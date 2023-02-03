package com.example.client

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.client.databinding.FragmentDateRecordBinding
import com.example.client.ui.navigation.BottomNavigationActivity


class DateRecordFragment : Fragment() {


    private val data=listOf(
        RecordData(1, "17일","배달의 민족", "11:00","10000원","철수랑 배달","@drawable/ic_category_food",
            ),
        RecordData(1, "17일","배달의 민족", "11:00","10000원","철수랑 배달","@drawable/ic_category_food",
        ),RecordData(1, "17일","배달의 민족", "11:00","10000원","철수랑 배달","@drawable/ic_category_food",
        ),RecordData(1, "17일","배달의 민족", "11:00","10000원","철수랑 배달","@drawable/ic_category_food",
        ),RecordData(1, "17일","배달의 민족", "11:00","10000원","철수랑 배달","@drawable/ic_category_food",
        ),RecordData(1, "17일","배달의 민족", "11:00","10000원","철수랑 배달","@drawable/ic_category_food",
        ),RecordData(1, "17일","배달의 민족", "11:00","10000원","철수랑 배달","@drawable/ic_category_food",
        ),

        )

    private val data2=listOf(

        RecordData(2, "17일","배달의 민족", "11:00","10000원","철수랑 배달","@drawable/ic_category_food",
        ),
        RecordData(2, "17일","배달의 민족", "11:00","10000원","철수랑 배달","@drawable/ic_category_food",
        ),RecordData(2, "17일","배달의 민족", "11:00","10000원","철수랑 배달","@drawable/ic_category_food",
        ),RecordData(2, "17일","배달의 민족", "11:00","10000원","철수랑 배달","@drawable/ic_category_food",
        ),RecordData(2, "17일","배달의 민족", "11:00","10000원","철수랑 배달","@drawable/ic_category_food",
        ),RecordData(2, "17일","배달의 민족", "11:00","10000원","철수랑 배달","@drawable/ic_category_food",
        ),
    )

    private lateinit var viewBinding:FragmentDateRecordBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding=FragmentDateRecordBinding.inflate(layoutInflater)


        //CalendarFragment에서 값 가져오기
        setFragmentResultListener("calendar") { requestKey, bundle ->

            bundle.getString("monthDay")?.let {
                viewBinding.tvMonthDay.text = it

            }

            var year=bundle.getString("year")
            var month=bundle.getString("month")
            var day=bundle.getString("day")



        }




        viewBinding.lvExpense.layoutManager= LinearLayoutManager(activity)
        viewBinding.lvExpense.adapter=DateRecordAdapter(data)

        viewBinding.lvIncome.layoutManager= LinearLayoutManager(activity)
        viewBinding.lvIncome.adapter=DateRecordAdapter(data2)

        val decoration = ItemDecoration(20)
        viewBinding.lvExpense.addItemDecoration(decoration)
        viewBinding.lvIncome.addItemDecoration(decoration)



        //뒤로가기 버튼 누를 시 캘린더 화면으로 이동
        val calendarActivity=activity as BottomNavigationActivity

        viewBinding.btnBack.setOnClickListener {
            calendarActivity.changeFragment(1)
            //calendarActivity.supportFragmentManager.beginTransaction().remove(this).commit()
            //calendarActivity.supportFragmentManager.popBackStack()
        }



        return viewBinding.root
    }


}