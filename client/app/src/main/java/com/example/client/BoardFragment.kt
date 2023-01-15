package com.example.client

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.client.databinding.FragmentBoardBinding

class BoardFragment : Fragment() {

    private val data=listOf(
        RecordData("","0","0","0","0","17일",0),
        RecordData("배달의 민족", "11:00","10000원","철수랑 배달","@drawable/ic_category_food",
            "17일",1),
        RecordData("요기요","11:00","10000원","철수랑 배달","@drawable/ic_category_food",
            "17일",2),
        RecordData("","0","0","0","0","16일",0),
        RecordData("배달의 민족", "11:00","10000원","철수랑 배달","@drawable/ic_category_food",
            "17일",1),
        RecordData("요기요","11:00","10000원","철수랑 배달","@drawable/ic_category_food",
            "17일",2),
        RecordData("","0","0","0","0","15일",0),
        RecordData("배달의 민족", "11:00","10000원","철수랑 배달","@drawable/ic_category_food",
            "17일",1),
        RecordData("요기요","11:00","10000원","철수랑 배달","@drawable/ic_category_food",
            "17일",2),
        RecordData("","0","0","0","0","14일",0),
        RecordData("배달의 민족", "11:00","10000원","철수랑 배달","@drawable/ic_category_food",
            "17일",1),
        RecordData("요기요","11:00","10000원","철수랑 배달","@drawable/ic_category_food",
            "17일",2),

        )

    private lateinit var viewBinding: FragmentBoardBinding
    private var linearLayoutManager: RecyclerView.LayoutManager? = null
    private var recyclerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentBoardBinding.inflate(layoutInflater)
        return viewBinding.root
/*
        recyclerAdapter = RecordAdapter(data)
        //linerLayoutManager=LinearLayoutManager()

        viewBinding.lvRecord.layoutManager= LinearLayoutManager(this)
        viewBinding.lvRecord.adapter=RecordAdapter(data)

        val decoration = ItemDecoration(20)
        viewBinding.lvRecord.addItemDecoration(decoration)

 */
    }
}