package com.example.client.ui.navigation


import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.client.APIObject
import com.example.client.api.DetailResponseByList
import com.example.client.api.HttpConnection
import com.example.client.api.api
import com.example.client.data.AppDatabase
import com.example.client.data.Detail
import com.example.client.data.adapter.ItemDecoration
import com.example.client.data.adapter.RecordAdapter
import com.example.client.databinding.FragmentBoardBinding
import com.example.client.ui.board.AddListActivity
import com.google.android.gms.common.util.CollectionUtils.listOf
import kotlinx.coroutines.InternalCoroutinesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.util.*

@InternalCoroutinesApi
class BoardFragment : androidx.fragment.app.Fragment(){
    private lateinit var viewBinding: FragmentBoardBinding
    private var linearLayoutManager: RecyclerView.LayoutManager? = null
    private lateinit var roomDb : AppDatabase
    private lateinit var bottomNavigationActivity : BottomNavigationActivity
    private lateinit var adapter: RecordAdapter
    private lateinit var longClickListener : RecordAdapter.OnListLongClickListener
    private val request: api = APIObject.getInstance().create(api::class.java)
    override fun onAttach(context: Context) {
        super.onAttach(context)
        bottomNavigationActivity = context as BottomNavigationActivity
        roomDb = AppDatabase.getInstance(bottomNavigationActivity)!!
        longClickListener = context
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentBoardBinding.inflate(inflater, container, false)
        linearLayoutManager=LinearLayoutManager(bottomNavigationActivity)
        viewBinding.boardList.layoutManager= LinearLayoutManager(bottomNavigationActivity)
        val userId = roomDb.UserDao().getUserId()
        var page = 1
        adapter = RecordAdapter(bottomNavigationActivity,listOf())
        viewBinding.boardList.adapter = adapter
        getList(userId,"all","all",page )

        val decoration = ItemDecoration(20)
        viewBinding.boardList.addItemDecoration(decoration)
        viewBinding.boardMenu.requestFocus()

        viewBinding.boardMenu.setOnClickListener {
            viewBinding.boardMenuOption.visibility = View.VISIBLE
        }
        viewBinding.boardMenuOption.setOnClickListener {
            val optionText = viewBinding.boardMenu.text
            viewBinding.boardMenu.text = viewBinding.boardMenuOption.text
            viewBinding.boardMenuOption.text = optionText
            viewBinding.boardMenuOption.visibility = View.GONE

            when(optionText){
                "전체" -> {
                    page = 1
                    getList(userId,LocalDate.now().year.toString(),LocalDate.now().monthValue.toString(),page)
                }
                else -> {
                    page = 1
                    getList(userId,"all","all",page)
                }
            }
        }

        viewBinding.boardAddButton.setOnClickListener {
            val intent = Intent(bottomNavigationActivity, AddListActivity::class.java)
            startActivity(intent)
        }
        return viewBinding.root
    }

    private fun onMenuChangeListener(detail: List<Detail>){
        longClickListener?.onListLongClickFinish()
        adapter.selectedItem = arrayListOf()
        val sortedDetail = detail.sortedWith(compareBy({-it.year.toInt()},{-it.month.toInt()},{-it.day.toInt()},{-it.detailId}))
        sortedDetail.filter{detail -> ((detail.integratedId == -1) or (detail.integratedId == detail.detailId) )}
        adapter.updateRecordList(sortedDetail)
        viewBinding.boardList.adapter= adapter
        (viewBinding.boardList.adapter as RecordAdapter).notifyDataSetChanged()
    }
    private fun getList(userId:Int, year : String, month : String, page: Int) {
        val call = request.getDetailsOfRange(userId,year, month, page)
        call.enqueue(object: Callback<DetailResponseByList> {
            override fun onResponse(call: Call<DetailResponseByList>, response: Response<DetailResponseByList>)  {
                if (response.body()!!.isSuccess){
                    println(response.body()!!.result)
                    onMenuChangeListener(response.body()!!.result)
                }
                else{
                    Toast.makeText(bottomNavigationActivity, "내역을 불러오지 못했습니다\n  나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                }
                println(response.body()?.message)
            }
            override fun onFailure(call: Call<DetailResponseByList>, t: Throwable) {
                Toast.makeText(bottomNavigationActivity, "내역을 불러오지 못했습니다\n  나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
