package com.example.client

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.client.databinding.ActivityRecordBinding

class RecordActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityRecordBinding

    private val data=listOf(
        RecordData(0,"17일"),
        RecordData(1,"","배달의 민족", "11:00","10000원",
            "철수랑 배달","@drawable/ic_category_food",),
        RecordData(2, "","배달의 민족", "11:00",
            "10000원","철수랑 배달","@drawable/ic_category_food",),
        RecordData(0,"15일"),
        RecordData(1,"","배달의 민족", "11:00","10000원",
            "철수랑 배달","@drawable/ic_category_food",),
        RecordData(2, "","배달의 민족", "11:00",
            "10000원","철수랑 배달","@drawable/ic_category_food",),

        )

    override fun onCreate(savedInstanceState: Bundle?) {

        viewBinding=ActivityRecordBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

//        viewBinding.lvRecord.layoutManager= LinearLayoutManager(this)
//        viewBinding.lvRecord.adapter=RecordAdapter(data)
//
//        val decoration = ItemDecoration(20)
//        viewBinding.lvRecord.addItemDecoration(decoration)
    }


}