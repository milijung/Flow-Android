package com.example.client.ui.navigation

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.client.APIObject
import com.example.client.R
import com.example.client.api.AnalysisResponseByList
import com.example.client.api.AnalysisResponseData
import com.example.client.api.DetailResponseByList
import com.example.client.api.api
import com.example.client.data.AppDatabase
import com.example.client.data.Category
import com.example.client.data.Detail
import com.example.client.databinding.FragmentHomeBinding
import com.example.client.ui.setting.SettingBudgetSettingActivity
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.navercorp.nid.NaverIdLoginSDK.applicationContext
import kotlinx.coroutines.InternalCoroutinesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

//import kotlinx.android.synthetic.main.fragment_home.*

@InternalCoroutinesApi @RequiresApi(Build.VERSION_CODES.O)
class HomeFragment : Fragment() {
    private lateinit var viewBinding: FragmentHomeBinding
    private lateinit var listItem: Detail
    private lateinit var selectedCategory: Category
    private val request: api = APIObject.getInstance().create(api::class.java)
    private lateinit var bottomNavigationActivity : BottomNavigationActivity
    private lateinit var roomDb : AppDatabase

    override fun onAttach(context: Context) {
        super.onAttach(context)
        bottomNavigationActivity = context as BottomNavigationActivity
        roomDb = AppDatabase.getInstance(bottomNavigationActivity)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentHomeBinding.inflate(layoutInflater)
        return viewBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var month  = LocalDate.now().monthValue
        val userId = roomDb.UserDao().getUserId()
        getAnalysis(userId,month)
        viewBinding.modifyText.setOnClickListener {
            val intent = Intent(bottomNavigationActivity,SettingBudgetSettingActivity::class.java)
            intent.putExtra("pageId",0)
            startActivity(intent)
        }
        viewBinding.modifyArrow.setOnClickListener {
            val intent = Intent(bottomNavigationActivity,SettingBudgetSettingActivity::class.java)
            intent.putExtra("pageId",0)
            startActivity(intent)
        }
        viewBinding.leftArrow.setOnClickListener {
            if(month-1>0){
                month -=1
                getAnalysis(userId,month)
            } else{
                Toast.makeText(bottomNavigationActivity,"이번 연도의 분석결과만 조회할 수 있습니다",Toast.LENGTH_SHORT).show()
            }
        }
        viewBinding.rightArrow.setOnClickListener {
            if(month+1<=LocalDate.now().monthValue){
                month +=1
                getAnalysis(userId,month)
            } else{
                Toast.makeText(bottomNavigationActivity,"이번달까지만 조회할 수 있습니다",Toast.LENGTH_SHORT).show()
            }
        }

        var graphStackedBar = viewBinding.graphStackedBar
        var barChart = viewBinding.graphBar
        //graphBar 값
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(1f, floatArrayOf(30f, 0f))) // 앞이 pink 뒤가 red
        entries.add(BarEntry(2f, floatArrayOf(40f, 0f)))
        entries.add(BarEntry(3f, floatArrayOf(30f, 0f)))
        entries.add(BarEntry(4f, floatArrayOf(40f, 0f)))
        entries.add(BarEntry(5f, floatArrayOf(30f, 0f)))
        entries.add(BarEntry(6f, floatArrayOf(50f, 10f)))
        entries.add(BarEntry(7f, floatArrayOf(50f, 20f)))

        //average line
        val line = LimitLine(50f, "")
        line.lineWidth = 2f
        line.enableDashedLine(10f, 10f, 0f)
        line.labelPosition = LimitLabelPosition.LEFT_TOP
        line.textSize = 10f
        val leftAxis: YAxis = barChart.getAxisLeft()
        leftAxis.removeAllLimitLines()
        leftAxis.addLimitLine(line)

        //graphBar 데이터셋 초기화
        var set = BarDataSet(entries,"DataSet")
        // graphBar 색 설정
        set.colors = mutableListOf(
            ContextCompat.getColor(applicationContext, R.color.pink),
            ContextCompat.getColor(applicationContext, R.color.red),
        )

        barChart.run {
            description.isEnabled = false // 차트 옆에 별도로 표기되는 description을 안보이게 설정 (false)
            setMaxVisibleValueCount(7) // 최대 보이는 그래프 개수를 7개로 지정
            setPinchZoom(false) // 핀치줌(두손가락으로 줌인 줌 아웃하는것) 설정
            setDrawBarShadow(false) //그래프의 그림자
            setDrawGridBackground(false)//격자구조 넣을건지
            //Y축
            axisLeft.run {
                axisMaximum = 90f
                axisMinimum = 0f
                granularity = 90f
                setDrawLabels(true) // 값 적는거 허용
                setDrawGridLines(true) //격자 라인 활용
                setDrawAxisLine(false) // 축 그리기 설정
                axisLineColor = ContextCompat.getColor(
                    context,
                    R.color.gray
                ) // 축 색깔 설정
                gridColor = ContextCompat.getColor(
                    context,
                    R.color.gray
                ) // 축 아닌 격자 색깔 설정
                textColor = ContextCompat.getColor(
                    context,
                    R.color.gray
                ) // 라벨 텍스트 컬러 설정
                textSize = 13f //라벨 텍스트 크기
                setDrawLabels(false) // 값 셋팅 설정
                addLimitLine(line) //average line
            }
            //X축
            xAxis.run {
                position = XAxis.XAxisPosition.BOTTOM //X축을 아래에
                granularity = 1f // 1 단위만큼 간격 두기
                setDrawAxisLine(true) // 축 그림
                setDrawGridLines(false) // 격자
                textColor = ContextCompat.getColor(context,
                    R.color.gray
                ) //라벨 색상
                textSize = 12f // 텍스트 크기
                valueFormatter = MyXAxisFormatter(month) // X축 라벨값 바꿔주기 위해 설정
            }
            axisRight.isEnabled = false // 오른쪽 Y축을 안보이게 해줌.
            setTouchEnabled(false) // 그래프 터치해도 아무 변화없게 막음
            animateY(1000) // 밑에서부터 올라오는 애니매이션 적용
            legend.isEnabled = false //차트 범례 설정
        }

        val dataSet :ArrayList<IBarDataSet> = ArrayList()
        dataSet.add(set)
        val data = BarData(dataSet)
        data.barWidth = 0.5f //막대 너비 설정
        barChart.run {
            this.data = data //차트의 데이터를 data로 설정
            setFitBars(true)
            invalidate()
        }

        //graphStackedBar
        val entries2 = ArrayList<BarEntry>()
        entries2.add(BarEntry(0f, floatArrayOf(50f, 30f, 20f))) //graph_stackedBar 값

        val set2 = BarDataSet(entries2, "")
        set2.colors = mutableListOf(
            ContextCompat.getColor(applicationContext,R.color.red),
            ContextCompat.getColor(applicationContext,R.color.orange),
            ContextCompat.getColor(applicationContext,R.color.yellow),
        ) //graph_stackedBar 차트 색

        val data2 = BarData(set2)
        val xAxis: XAxis = graphStackedBar.getXAxis()
        data2.setDrawValues(false)
        data2.setBarWidth(3f);
        data2.isHighlightEnabled = false
        graphStackedBar.data = data2
        graphStackedBar.axisLeft.setDrawGridLines(false)
        graphStackedBar.xAxis.setDrawGridLines(false)
        graphStackedBar.description.isEnabled = false
        graphStackedBar.axisLeft.setDrawLabels(false)
        graphStackedBar.axisRight.setDrawLabels(false)
        graphStackedBar.xAxis.setDrawLabels(false)
        graphStackedBar.legend.isEnabled = false
        graphStackedBar.invalidate()
        graphStackedBar.setPinchZoom(false)
        graphStackedBar.axisRight.isEnabled = false // Y축 안보이게
    }

    //graphBar X축 라벨값
    inner class MyXAxisFormatter(private val month : Int) : ValueFormatter() {
        private var days = ArrayList<String>()
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            if (month<8){
                days = arrayListOf("1월","2월","3월","4월","5월","6월","7월")
            }else{
                days = arrayListOf("6월","7월","8월","9월","10월","11월","12월")
            }
            return days.getOrNull(value.toInt()-1) ?: value.toString()
        }
    }
    private fun getAnalysis(userId:Int, month : Int,) {
        val call = request.getAnalysis(userId, month)
        viewBinding.month.text = "${month}월"
        viewBinding.progressBar.visibility = View.VISIBLE
        call.enqueue(object: Callback<AnalysisResponseByList> {
            override fun onResponse(call: Call<AnalysisResponseByList>, response: Response<AnalysisResponseByList>)  {
                if (response.body()!!.isSuccess){
                    val response : AnalysisResponseData= response.body()!!.result
                    viewBinding.month.text = "${month}월"
                    viewBinding.budgetBtn.text = "예산 ${response.budget}원"
                    viewBinding.spendBudget.text = "-${response.consumption}"
                    viewBinding.budgetPercent.text = "${response.percent}%"
                    if(month == LocalDate.now().monthValue)
                        viewBinding.date.text = "${month}.1 ~ ${month}.${LocalDate.now().dayOfMonth}"
                    else{
                        val date = LocalDate.of(LocalDate.now().year,month,1)
                        viewBinding.date.text = "${month}.1 ~ ${month}.${date.withDayOfMonth(date.lengthOfMonth()).dayOfMonth}"
                    }
                    if(response.consumption > response.latsConsumption){
                        viewBinding.compareWithPreviousBudget.text = "${response.consumption-response.latsConsumption}"
                        viewBinding.compareWithPreviousBudgetText.text = "원 더 쓰는 중이에요"
                    }
                    else{
                        viewBinding.compareWithPreviousBudget.text = "${response.latsConsumption-response.consumption}"
                        viewBinding.compareWithPreviousBudgetText.text = "원 적게 쓰는 중이에요"
                    }
                }
                else{
                    Toast.makeText(bottomNavigationActivity, "분석결과를 불러오지 못했습니다\n    나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                }
                println(response.body()?.message)
                viewBinding.progressBar.visibility = View.GONE
            }
            override fun onFailure(call: Call<AnalysisResponseByList>, t: Throwable) {
                viewBinding.progressBar.visibility = View.GONE
                Toast.makeText(bottomNavigationActivity, "분석결과를 불러오지 못했습니다\n    나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }
        })
    }
}