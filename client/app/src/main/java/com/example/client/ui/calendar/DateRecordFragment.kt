package com.example.client.ui.calendar

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.client.data.adapter.ItemDecoration
import com.example.client.data.AppDatabase
import com.example.client.data.adapter.DateRecordAdapter
import com.example.client.databinding.FragmentDateRecordBinding
import com.example.client.ui.navigation.BottomNavigationActivity


class DateRecordFragment : Fragment() {

    private lateinit var viewBinding:FragmentDateRecordBinding
    private lateinit var dateRecordActivity : DateRecordActivity
    private val roomDb = AppDatabase.getListInstance(dateRecordActivity)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dateRecordActivity = context as DateRecordActivity
    }

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
        if (roomDb != null) {
            viewBinding.lvExpense.adapter= DateRecordAdapter(dateRecordActivity,roomDb.ListDao().selectThisMonth())
        }

        viewBinding.lvIncome.layoutManager= LinearLayoutManager(activity)
        if (roomDb != null) {
            viewBinding.lvIncome.adapter= DateRecordAdapter(dateRecordActivity,roomDb.ListDao().selectThisMonth())
        }

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