package com.example.client.ui.navigation



import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.client.*
import com.example.client.data.adapter.CalendarAdapter
import com.example.client.data.CalendarService
import com.example.client.data.model.CalendarData
import com.example.client.databinding.FragmentCalendarBinding
import com.example.client.ui.calendar.DateRecordActivity
import com.example.client.ui.calendar.OnCalendarItemListener

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.YearMonth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CalendarFragment : Fragment(), OnCalendarItemListener {
    private lateinit var viewBinding: FragmentCalendarBinding
    lateinit var selectedDate: LocalDate
    private lateinit var bottomNavigationActivity : BottomNavigationActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        bottomNavigationActivity = context as BottomNavigationActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentCalendarBinding.inflate(inflater, container, false)
        //현재 날짜
        selectedDate=LocalDate.now()
        //화면 설정
        setMonthView()
        //저번달 버튼 이벤트
        viewBinding.calendarPreBtn.setOnClickListener {
            selectedDate=selectedDate.minusMonths(1)
            //새롭게 화면 설정
            setMonthView()
        }
        //다음달 버튼 이벤트
        viewBinding.calendarNextBtn.setOnClickListener {
            selectedDate=selectedDate.plusMonths(1)
            //새롭게 화면 설정
            setMonthView()
        }
        return viewBinding.root
    }

    //날짜 화면에 보여주기
    private fun setMonthView() {
        //월 텍스트뷰 셋팅
        viewBinding.calendarMonth.text=yearMonthFromDate(selectedDate)
        //날짜 생성해서 리스트에 담기
        val dayList=dayInMonthArray(selectedDate)
        //어댑터 초기화
        val adapter = CalendarAdapter(dayList,this,selectedDate)
        //레이아웃 설정(열 7개)
        var manager: RecyclerView.LayoutManager=GridLayoutManager(activity, 7)
        //레이아웃 적용
        viewBinding.calendarRv.layoutManager=manager
        //어뎁터 적용
        viewBinding.calendarRv.adapter=adapter
    }

    //날짜 타입 설정(월)
    private fun yearMonthFromDate(date:LocalDate):String{
        var formatter= DateTimeFormatter.ofPattern("MM월")
        //받아온 날짜를 해당 포맷으로 변경
        return date.format(formatter)
    }

    //날짜 설정 / 어뎁터에 날짜, 지출, 수입 리스트 넘겨주기
    private fun dayInMonthArray(date:LocalDate):ArrayList<CalendarData>{

        var serverList: ArrayList<CalendarServerDataResult>?= arrayListOf()
        //serverList=requestAPI(selectedDate.year,selectedDate.monthValue)
        serverList?.add(CalendarServerDataResult(24,1,1000))
        serverList?.add(CalendarServerDataResult(24,0,20000))
        serverList?.add(CalendarServerDataResult(5,0,5000))

        var dayList=ArrayList<CalendarData>()

        var yearMonth= YearMonth.from(date)

        //해당 월 마지막 날짜 가져오기(예 28, 30, 31)
        var lastDay = yearMonth.lengthOfMonth()

        //해당 월의 첫 번째 날 가져오기(예: 4월 1일)
        var firstDay=selectedDate.withDayOfMonth(1)

        //첫 번째날 요일 가져오기(월:1, 일: 7)
        var dayOfweek=firstDay.dayOfWeek.value

        for(i in 1..41){
            //첫 번째날 요일이 일요일일 때
            if(dayOfweek==7){
                if(i>(lastDay)){
                    dayList.add(CalendarData("", "",""))
                }
                else {
                    var expense = ""
                    var income = ""

                    if(serverList?.find(){it.date.toInt()==i && it.isExp.toInt()==1}!=null){
                        expense="-"+serverList.find(){it.date.toInt()==i && it.isExp.toInt()==1}!!.amount
                    }
                    if(serverList?.find(){it.date.toInt()==i && it.isExp.toInt()==0}!=null){
                        income="+"+serverList.find(){it.date.toInt()==i && it.isExp.toInt()==0}!!.amount
                    }

                    dayList.add(CalendarData((i).toString(), expense, income))
                }
            }else{
                if(i <= dayOfweek || i>(lastDay+dayOfweek)){
                    dayList.add(CalendarData("", "",""))
                }else{
                    var expense=""
                    var income=""
                    if(serverList?.find(){it.date==(i-dayOfweek) && it.isExp==1}!=null){
                        expense="-"+(serverList.find(){it.date==(i-dayOfweek) && it.isExp==1}!!.amount).toString()
                    }
                    if(serverList?.find(){it.date==(i-dayOfweek) && it.isExp==0}!=null){
                        income="+"+(serverList.find(){it.date==(i-dayOfweek) && it.isExp==0}!!.amount).toString()
                    }
                    dayList.add(CalendarData((i-dayOfweek).toString(), expense,income))
            }
            }
        }
        return dayList
    }

    //api 요청 시 필요한 객체
    object CalendarObject{
        private const val BASE_URL = ""
        private var instance: Retrofit? = null

        open fun getInstance() : Retrofit {
            if (instance == null) {
                instance = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return instance!!
        }
    }

    //api 요청
    private fun requestAPI(year:Int, month:Int): ArrayList<CalendarServerDataResult>? {

        val service: CalendarService =CalendarObject.getInstance().create(CalendarService::class.java)
        val call = service.getAmount(year,month)
        var list :ArrayList<CalendarServerDataResult>?=null

        call.enqueue(object: Callback<CalendarServerData>{
            override fun onResponse(call: Call<CalendarServerData>, response: Response<CalendarServerData>){
                if (response.isSuccessful){
                    list = response.body()?.result
                }
                else{
                    Log.w("Retrofit", "Response Not Successful ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CalendarServerData>, t: Throwable) {
                Log.w("Retrofit", "Error!", t)
            }
        })
        return list

    }

    //아이템 클릭 이벤트 --> 클릭 시 해당 날짜 내역 화면으로 이동
    override fun onItemClick(data: CalendarData) {

        //해당 연,월,일 값을 fragment로 넘겨주기
        var year=selectedDate.year.toString()
        var month=selectedDate.monthValue.toString()
        var day=data.day

        //해당 날짜 내역 화면으로 이동
        val intent = Intent(bottomNavigationActivity, DateRecordActivity::class.java)
        intent.putExtra("year",year)
        intent.putExtra("month",month)
        intent.putExtra("day",day)
        startActivity(intent)
    }
}