package com.example.client.ui.navigation


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.client.data.AppDatabase
import com.example.client.data.adapter.ItemDecoration
import com.example.client.data.adapter.RecordAdapter
import com.example.client.databinding.FragmentBoardBinding

class BoardFragment : androidx.fragment.app.Fragment(){
    private lateinit var viewBinding: FragmentBoardBinding
    private var linearLayoutManager: RecyclerView.LayoutManager? = null
    private lateinit var roomDb : AppDatabase
    private lateinit var bottomNavigationActivity : BottomNavigationActivity
    private lateinit var adapter: RecordAdapter
    private lateinit var longClickListener : RecordAdapter.OnListLongClickListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        bottomNavigationActivity = context as BottomNavigationActivity
        roomDb = AppDatabase.getListInstance(bottomNavigationActivity)!!
        longClickListener = context as RecordAdapter.OnListLongClickListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentBoardBinding.inflate(inflater, container, false)
        linearLayoutManager=LinearLayoutManager(bottomNavigationActivity)
        viewBinding.boardList.layoutManager= LinearLayoutManager(bottomNavigationActivity)
        adapter = RecordAdapter(bottomNavigationActivity,roomDb.ListDao().selectThisMonth())

        var recordListOfAll : List<com.example.client.data.List> = roomDb.ListDao().selectAll()
        var recordListOfThisMonth : List<com.example.client.data.List> = roomDb.ListDao().selectThisMonth()

        val decoration = ItemDecoration(20)
        viewBinding.boardList.addItemDecoration(decoration)
        viewBinding.boardMenuThisMonth.requestFocus()

        viewBinding.boardMenuThisMonth.setOnFocusChangeListener { v, hasFocus ->
            onMenuChangeListener(recordListOfThisMonth)
        }
        viewBinding.boardMenuAll.setOnFocusChangeListener { v, hasFocus ->
            onMenuChangeListener(recordListOfAll)
        }
        viewBinding.boardAddButton.setOnClickListener {
            adapter.deleteButtonClickListener()
            recordListOfAll = roomDb.ListDao().selectAll()
            recordListOfThisMonth = roomDb.ListDao().selectThisMonth()
            when(viewBinding.boardMenuThisMonth.hasFocus()){
                true -> onMenuChangeListener(recordListOfThisMonth)
                else -> onMenuChangeListener(recordListOfAll)
            }
        }
        return viewBinding.root
    }

    private fun onMenuChangeListener(list: List<com.example.client.data.List>){
        longClickListener?.onListLongClickFinish()
        adapter.selectedItem = arrayListOf()
        adapter.updateRecordList(list)
        viewBinding.boardList.adapter= adapter
        (viewBinding.boardList.adapter as RecordAdapter).notifyDataSetChanged()
    }

}