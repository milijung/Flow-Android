package com.example.client.ui.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.client.RecordData
import com.example.client.data.AppDatabase
import com.example.client.databinding.FragmentBoardBinding

class BoardFragment : Fragment() {

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
//        val roomDb = AppDatabase.getListInstance(this)
//
//        recyclerAdapter = RecordAdapter(data)
//        //linerLayoutManager=LinearLayoutManager()
//
//        viewBinding.lvRecord.layoutManager= LinearLayoutManager(this)
//        viewBinding.lvRecord.adapter=RecordAdapter(data)
//
//        val decoration = ItemDecoration(20)
//        viewBinding.lvRecord.addItemDecoration(decoration)


    }
}