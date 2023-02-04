package com.example.client.ui.navigation


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.client.data.AppDatabase
import com.example.client.data.adapter.ItemDecoration
import com.example.client.data.adapter.RecordAdapter
import com.example.client.databinding.FragmentBoardBinding

class BoardFragment : androidx.fragment.app.Fragment() {
    private lateinit var viewBinding: FragmentBoardBinding
    private var linearLayoutManager: RecyclerView.LayoutManager? = null
    private lateinit var roomDb : AppDatabase
    private lateinit var bottomNavigationActivity : BottomNavigationActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        bottomNavigationActivity = context as BottomNavigationActivity
        roomDb = AppDatabase.getListInstance(bottomNavigationActivity)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentBoardBinding.inflate(inflater, container, false)
        linearLayoutManager=LinearLayoutManager(bottomNavigationActivity)
        viewBinding.boardList.layoutManager= LinearLayoutManager(bottomNavigationActivity)

        val decoration = ItemDecoration(20)
        viewBinding.boardList.addItemDecoration(decoration)
        viewBinding.boardMenuThisMonth.requestFocus()

        viewBinding.boardMenuThisMonth.setOnFocusChangeListener { v, hasFocus ->
            // 목록 새로고침
            viewBinding.boardList.adapter= RecordAdapter(bottomNavigationActivity,roomDb.ListDao().selectThisMonth())
            (viewBinding.boardList.adapter as RecordAdapter).notifyDataSetChanged()

        }
        viewBinding.boardMenuAll.setOnFocusChangeListener { v, hasFocus ->
            // 목록 새로고침
            viewBinding.boardList.adapter= RecordAdapter(bottomNavigationActivity,roomDb.ListDao().selectAll())
            (viewBinding.boardList.adapter as RecordAdapter).notifyDataSetChanged()

        }

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.boardList.adapter= RecordAdapter(bottomNavigationActivity,roomDb.ListDao().selectThisMonth())
    }


}