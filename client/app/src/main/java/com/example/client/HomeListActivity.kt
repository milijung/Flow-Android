package com.example.client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.navercorp.nid.NaverIdLoginSDK

class HomeListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_list)

        //graph
        var barChart = findViewById<BarChart>(R.id.graph_twoBar)

        //graph 값
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(1f, floatArrayOf(60f, 0f)))
        entries.add(BarEntry(2f, floatArrayOf(0f, 50f)))

        //average line
        val line = LimitLine(50f, "")
        line.lineWidth = 2f
        line.enableDashedLine(10f, 10f, 0f)
        line.labelPosition = LimitLine.LimitLabelPosition.LEFT_TOP
        line.textSize = 10f
        val leftAxis: YAxis = barChart.getAxisLeft()
        leftAxis.removeAllLimitLines()
        leftAxis.addLimitLine(line)

        //graph 데이터셋 초기화
        var set = BarDataSet(entries,"DataSet")
        //graph 색 설정
        set.colors = mutableListOf(
            ContextCompat.getColor(NaverIdLoginSDK.applicationContext, R.color.pink),
            ContextCompat.getColor(NaverIdLoginSDK.applicationContext, R.color.red),
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
                setDrawAxisLine(false) // 축 그리기 설정
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
                valueFormatter = MyXAxisFormatter() // X축 라벨값 바꿔주기 위해 설정
            }
            axisRight.isEnabled = false // 오른쪽 Y축을 안보이게 해줌.
            setTouchEnabled(false) // 그래프 터치해도 아무 변화없게 막음
            animateY(1000) // 밑에서부터 올라오는 애니매이션 적용
            legend.isEnabled = false //차트 범례 설정
        }

        val dataSet :ArrayList<IBarDataSet> = ArrayList()
        dataSet.add(set)
        val data = BarData(dataSet)
        data.barWidth = 0.2f //막대 너비 설정
        barChart.run {
            this.data = data //차트의 데이터를 data로 설정
            setFitBars(true)
            invalidate()
        }
    }
    //graph_twoBar X축 라벨값
    inner class MyXAxisFormatter : ValueFormatter() {
        private val days = arrayOf("11월","12월")
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return days.getOrNull(value.toInt()-1) ?: value.toString()
        }
    }
}