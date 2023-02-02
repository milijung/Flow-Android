package com.example.client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.contentcapture.DataRemovalRequest
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.client.databinding.ActivityDateRecordBinding
import com.example.client.databinding.ActivityRecordBinding

class DateRecordActivity : AppCompatActivity() {

    //스크롤 오류 --> 수정하기

    private val data=listOf(
        RecordData(2,"17일","배달의 민족", "11:00","10000원","철수랑 배달","@drawable/ic_category_food",),
        RecordData(2,"17일","배달의 민족", "11:00","10000원","철수랑 배달","@drawable/ic_category_food",),
        RecordData(2,"17일","배달의 민족", "11:00","10000원","철수랑 배달","@drawable/ic_category_food",),
        RecordData(2,"17일","배달의 민족", "11:00","10000원","철수랑 배달","@drawable/ic_category_food",),
        RecordData(2,"17일","배달의 민족", "11:00","10000원","철수랑 배달","@drawable/ic_category_food",),RecordData(2,"17일","배달의 민족", "11:00","10000원","철수랑 배달","@drawable/ic_category_food",),
    )

    private val data2=listOf(

        RecordData(1,"","배달의 민족", "11:00","10000원","철수랑 배달","@drawable/ic_category_food",),
        RecordData(1,"17일","배달의 민족", "11:00","10000원","철수랑 배달","@drawable/ic_category_food",),
        RecordData(1,"17일","배달의 민족", "11:00","10000원","철수랑 배달","@drawable/ic_category_food",),
        RecordData(1,"17일","배달의 민족", "11:00","10000원","철수랑 배달","@drawable/ic_category_food",),
        RecordData(1,"17일","배달의 민족", "11:00","10000원","철수랑 배달","@drawable/ic_category_food",),
    )



    private lateinit var viewBinding: ActivityDateRecordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=ActivityDateRecordBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.lvExpense.layoutManager=LinearLayoutManager(this)
        viewBinding.lvExpense.adapter=DateRecordAdapter(data)

        viewBinding.lvIncome.layoutManager=LinearLayoutManager(this)
        viewBinding.lvIncome.adapter=DateRecordAdapter(data2)

        val decoration = ItemDecoration(20)
        viewBinding.lvExpense.addItemDecoration(decoration)
        viewBinding.lvIncome.addItemDecoration(decoration)
    }
}