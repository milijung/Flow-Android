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
import com.example.client.data.CalendarService
import com.example.client.data.adapter.CalendarAdapter
import com.example.client.data.model.CalendarData
import com.example.client.databinding.FragmentCalendarBinding
import com.example.client.ui.calendar.DateRecordActivity

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.YearMonth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CalendarFragment : Fragment() {
    private lateinit var viewBinding: FragmentCalendarBinding
    lateinit var selectedDate: LocalDate
    private lateinit var bottomNavigationActivity : BottomNavigationActivity


    var monthFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MM")

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

    //날짜, 지출, 수익 화면에 보여주기
    private fun setMonthView() {
        //월 텍스트뷰 셋팅
        viewBinding.calendarMonth.text=selectedDate.format(monthFormatter)+"월"


        //레이아웃 설정(열 7개)
        val manager: RecyclerView.LayoutManager=GridLayoutManager(activity, 7)
        //레이아웃 적용
        viewBinding.calendarRv.layoutManager=manager


        //어뎁터에 넘겨줄 리스트
        val dayList=ArrayList<CalendarData>()

        //해당 월 가져오기
        val yearMonth= YearMonth.from(selectedDate)

        //해당 월 마지막 날짜 가져오기(예 28, 30, 31)
        val lastDay = yearMonth.lengthOfMonth()

        //해당 월의 첫 번째 날 가져오기(예: 2023-01-01)
        val firstDay=selectedDate.withDayOfMonth(1)

        //첫 번째날 요일 가져오기(월:1, 일: 7)
        var dayOfWeek=firstDay.dayOfWeek.value
        if(dayOfWeek==7){dayOfWeek=0} //일:7 ->일:0

        //api 요청
        val service: CalendarService =APIObject.getInstance().create(CalendarService::class.java)
        val call = service.getAmount(selectedDate.year,selectedDate.monthValue,1)

        call.enqueue(object: Callback<CalendarServerData>{
            override fun onResponse(call: Call<CalendarServerData>, response: Response<CalendarServerData>){
                if (response.isSuccessful){
                    //서버에서 해당 월의 지출, 수입 가져오기
                    val serverList = response.body()?.result

                    //어뎁터에 넘겨줄 리스트 만들기
                    for(i in 1..41){
                        //빈칸
                        if(i <= dayOfWeek || i>(lastDay+dayOfWeek)){
                            dayList.add(CalendarData("", "",""))
                        }else{ //숫자,지출,수입 넣기
                            var expense=""
                            var income=""
                            //지출
                            if(serverList?.find {it.date==(i-dayOfWeek) && it.isExp==1}!=null){
                                expense="-"+(serverList.find {it.date==(i-dayOfWeek) && it.isExp==1}!!.amount).toString()
                            }
                            //수입
                            if(serverList?.find {it.date==(i-dayOfWeek) && it.isExp==2}!=null){
                                income="+"+(serverList.find {it.date==(i-dayOfWeek) && it.isExp==2}!!.amount).toString()
                            }

                            dayList.add(CalendarData((i-dayOfWeek).toString(), expense,income))
                        }
                    }

                    //어댑터 초기화
                    val adapter = CalendarAdapter(dayList,selectedDate)

                    //날짜 클릭 이벤트 --> 클릭 시 해당 날짜 내역 화면으로 이동
                    adapter.setOnItemClickListener(object : CalendarAdapter.OnItemClickListener{
                        override fun onItemClick(v: View, position: Int) {
                            //클릭한 날짜의 CalendarData
                            val data = dayList[position]

                            val year=selectedDate.year.toString()
                            val month=selectedDate.format(monthFormatter)
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
            }

            override fun onFailure(call: Call<CalendarServerData>, t: Throwable) {
                Log.w("Retrofit", "Error!", t)
            }
        })
    }
}