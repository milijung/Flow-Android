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
import com.example.client.api.*
import com.example.client.data.AppDatabase
import com.example.client.data.adapter.CalendarAdapter
import com.example.client.databinding.FragmentCalendarBinding
import com.example.client.ui.calendar.DateRecordActivity
import kotlinx.coroutines.InternalCoroutinesApi

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.YearMonth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@InternalCoroutinesApi
class CalendarFragment : Fragment() {
    private lateinit var viewBinding: FragmentCalendarBinding
    lateinit var TodayDate: LocalDate
    private lateinit var bottomNavigationActivity : BottomNavigationActivity
    var monthFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MM")
    private val request: api = APIObject.getInstance().create(api::class.java)
    var dayList=ArrayList<CalendarData>()
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
        val roomDb = AppDatabase.getInstance(bottomNavigationActivity)
        val userId = roomDb!!.UserDao().getUserId()
        //현재 날짜
        TodayDate=LocalDate.now()
        //화면 설정
        setMonthView(userId)
        //저번달 버튼 이벤트
        viewBinding.calendarPreBtn.setOnClickListener {
            TodayDate=TodayDate.minusMonths(1)
            //새롭게 화면 설정
            setMonthView(userId)
        }
        //다음달 버튼 이벤트
        viewBinding.calendarNextBtn.setOnClickListener {
            TodayDate=TodayDate.plusMonths(1)
            //새롭게 화면 설정
            setMonthView(userId)
        }
        return viewBinding.root
    }

    //날짜, 지출, 수익 화면에 보여주기
    private fun setMonthView(userId : Int) {
        //월 텍스트뷰 셋팅
        viewBinding.calendarMonth.text="${TodayDate.monthValue}월"

        //레이아웃 설정(열 7개)
        val manager: RecyclerView.LayoutManager=GridLayoutManager(activity, 7)
        viewBinding.calendarRv.layoutManager=manager

        //해당 월 가져오기
        val yearMonth= YearMonth.from(TodayDate)

        //해당 월 마지막 날짜 가져오기(예 28, 30, 31)
        val lastDay = yearMonth.lengthOfMonth()

        //해당 월의 첫 번째 날 가져오기(예: 2023-01-01)
        val firstDay=TodayDate.withDayOfMonth(1)

        //첫 번째날 요일 가져오기(월:1, 일: 7)
        var dayOfWeek=firstDay.dayOfWeek.value
        if(dayOfWeek==7){dayOfWeek=0} //일:7 ->일:0

        dayList = arrayListOf()

        getCalendarInfo(TodayDate.year, TodayDate.monthValue, lastDay, dayOfWeek,userId)
    }
    private fun getCalendarInfo(year: Int, month: Int, lastDay: Int, dayOfWeek:Int, userId: Int) {
        val call = request.getCalendar(year, month, userId)
        call.enqueue(object: Callback<CalendarResponseByList> {
            override fun onResponse(
                call: Call<CalendarResponseByList>,
                response: Response<CalendarResponseByList>
            ) {
                if (response.isSuccessful){
                    //서버에서 해당 월의 지출, 수입 가져오기
                    val calendarInfo = response.body()?.result!!
                    //어뎁터에 넘겨줄 리스트 만들기
                    for(i in 1..41){
                        //빈칸
                        if(i <= dayOfWeek || i>(lastDay+dayOfWeek)){
                            dayList.add(CalendarData("", "",""))
                        }else{ //숫자,지출,수입 넣기
                            var expense=""
                            var income=""
                            //지출
                            if(calendarInfo?.find{it.date==(i-dayOfWeek) && it.isExp==1}!=null){
                                expense="-"+(calendarInfo.find {it.date==(i-dayOfWeek) && it.isExp==1}!!.amount).toString()
                            }
                            //수입
                            if(calendarInfo?.find{it.date==(i-dayOfWeek) && it.isExp==2}!=null){
                                income="+"+(calendarInfo.find {it.date==(i-dayOfWeek) && it.isExp==2}!!.amount).toString()
                            }

                            dayList.add(CalendarData((i-dayOfWeek).toString(), expense,income))
                        }
                    }
                    //어댑터 초기화
                    val adapter = CalendarAdapter(dayList,TodayDate)

                    //날짜 클릭 이벤트 --> 클릭 시 해당 날짜 내역 화면으로 이동
                    adapter.setOnItemClickListener(object : CalendarAdapter.OnItemClickListener{
                        override fun onItemClick(v: View, position: Int) {
                            //클릭한 날짜의 CalendarData
                            val data = dayList[position]

                            val year=TodayDate.year.toString()
                            val month=TodayDate.format(monthFormatter)
                            val day="0"+data.day


                            //해당 날짜 내역 화면으로 이동
                            val intent = Intent(bottomNavigationActivity, DateRecordActivity::class.java)
                            intent.putExtra("year",year)
                            intent.putExtra("month",month)
                            intent.putExtra("day",day.substring(day.length -2,day.length))
                            startActivity(intent)
                        }

                    }
                    )
                    //어뎁터 적용
                    viewBinding.calendarRv.adapter=adapter
                }
                else{
                    Log.w("Retrofit", "Response Not Successful ${response.code()}")
                }
                println(response.body()?.message)
            }
            override fun onFailure(call: Call<CalendarResponseByList>, t: Throwable) {
                Log.w("Retrofit", "Error!", t)
            }
        })
    }
}