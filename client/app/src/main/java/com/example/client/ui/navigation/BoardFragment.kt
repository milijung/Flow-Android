package com.example.client.ui.navigation


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.client.APIObject
import com.example.client.api.DetailResponseByList
import com.example.client.api.api
import com.example.client.data.AppDatabase
import com.example.client.data.Detail
import com.example.client.data.adapter.ItemDecoration
import com.example.client.data.adapter.RecordAdapter
import com.example.client.databinding.FragmentBoardBinding
import com.example.client.ui.board.AddListActivity
import com.example.client.ui.calendar.DateRecordActivity
import com.google.android.gms.common.util.CollectionUtils.listOf
import kotlinx.coroutines.InternalCoroutinesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.util.*
import kotlin.properties.Delegates

@InternalCoroutinesApi
class BoardFragment : androidx.fragment.app.Fragment(){
    private lateinit var viewBinding: FragmentBoardBinding
    private var linearLayoutManager: RecyclerView.LayoutManager? = null
    private lateinit var roomDb : AppDatabase
    private lateinit var bottomNavigationActivity : BottomNavigationActivity
    private var adapter: RecordAdapter? = null
    private lateinit var longClickListener : RecordAdapter.OnListLongClickListener
    private val request: api = APIObject.getInstance().create(api::class.java)
    private var page = 1
    private var userId by Delegates.notNull<Int>()

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
    ): FrameLayout {
        viewBinding = FragmentBoardBinding.inflate(inflater, container, false)
        linearLayoutManager=LinearLayoutManager(bottomNavigationActivity)
        viewBinding.boardList.layoutManager= LinearLayoutManager(bottomNavigationActivity)
        viewBinding.calendar.visibility = View.GONE
        userId = roomDb.UserDao().getUserId()

        getList(userId,"all","all",page)

        val decoration = ItemDecoration(25)
        viewBinding.boardList.addItemDecoration(decoration)

        viewBinding.boardMenu.setOnClickListener {
            if(viewBinding.boardMenuOption.isVisible)
                viewBinding.boardMenuOption.visibility = View.GONE
            else
                viewBinding.boardMenuOption.visibility = View.VISIBLE
        }
        viewBinding.boardMenuOption.setOnClickListener {
            val optionText = viewBinding.boardMenu.text
            viewBinding.boardMenu.text = viewBinding.boardMenuOption.text
            viewBinding.boardMenuOption.text = optionText
            viewBinding.boardMenuOption.visibility = View.GONE

            when(viewBinding.showCalendarButton.isEnabled){
                false -> {
                    page = 1
                    viewBinding.showCalendarButton.isEnabled = true
                    getList(userId,LocalDate.now().year.toString(),LocalDate.now().monthValue.toString(),page)
                }
                else -> {
                    page = 1
                    viewBinding.showCalendarButton.isEnabled = false
                    viewBinding.calendar.visibility = View.GONE
                    getList(userId,"all","all",page)
                }
            }
        }
        viewBinding.showCalendarButton.setOnClickListener {
            if(viewBinding.showCalendarButton.text == "달력보기"){
                viewBinding.calendar.visibility = View.VISIBLE
                viewBinding.showCalendarButton.text = "달력접기"
            }else{
                viewBinding.calendar.visibility = View.GONE
                viewBinding.showCalendarButton.text = "달력보기"
            }
        }
        viewBinding.boardAddButton.setOnClickListener {
            val intent = Intent(bottomNavigationActivity, AddListActivity::class.java)
            startActivity(intent)
        }
        viewBinding.calendar.setOnDateChangedListener { widget, date, selected ->
            //해당 날짜 내역 화면으로 이동
            val intent = Intent(bottomNavigationActivity, DateRecordActivity::class.java)
            intent.putExtra("year",date.year.toString())
            intent.putExtra("month",date.month.toString())
            intent.putExtra("day",date.day.toString())
            startActivity(intent)
        }
        viewBinding.calendar.setOnMonthChangedListener { widget, date ->
            page = 1
            getList(userId,date.year.toString(),date.month.toString(),page)
        }
        viewBinding.refresh.setOnRefreshListener {
            if(!viewBinding.showCalendarButton.isEnabled){
                page += 1
                getList(userId,"all","all",page)
                viewBinding.refresh.isRefreshing = false
            }
            else
                viewBinding.refresh.isRefreshing = false
        }
        return viewBinding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onMenuChangeListener(detail: List<Detail>, page: Int){
        var itemCount = 0
        when {
            adapter == null -> {
                adapter = RecordAdapter(bottomNavigationActivity,listOf())
            }
            page == 1 -> {
                adapter!!.selectedItem.clear()
            }
            else -> {
                itemCount = adapter!!.itemCount
            }
        }
        val sortedDetail : List<Detail> = detail.sortedWith(compareBy({-it.year.toInt()},{-it.month.toInt()},{-it.day.toInt()},{-it.detailId}))
        sortedDetail.filter{detail : Detail-> ((detail.integratedId == -1) or (detail.integratedId == detail.detailId) )}
        adapter!!.updateRecordList(sortedDetail, page)
        viewBinding.boardList.adapter= adapter
        when(page){
            1->(viewBinding.boardList.adapter as RecordAdapter).notifyDataSetChanged()
            else -> (viewBinding.boardList.adapter as RecordAdapter).notifyItemRangeInserted(itemCount, sortedDetail.size)
        }
    }
    private fun getList(userId:Int, year : String, month : String, page: Int) {
        val call = request.getDetailsOfRange(userId,year, month, page)
        viewBinding.progressBar.visibility = View.VISIBLE
        call.enqueue(object: Callback<DetailResponseByList> {
            override fun onResponse(call: Call<DetailResponseByList>, response: Response<DetailResponseByList>)  {
                if (response.body()!!.isSuccess){
                    onMenuChangeListener(response.body()!!.result, page)
                }
                else{
                    Toast.makeText(bottomNavigationActivity, "내역을 불러오지 못했습니다\n  나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                }
                println(response.body()?.message)
                viewBinding.progressBar.visibility = View.GONE
            }
            override fun onFailure(call: Call<DetailResponseByList>, t: Throwable) {
                viewBinding.progressBar.visibility = View.GONE
                Toast.makeText(bottomNavigationActivity, "내역을 불러오지 못했습니다\n  나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }
        })
    }
    fun getBoardList() : RecyclerView{
        return viewBinding.boardList
    }
    fun getSelectedDetail() : List<Detail>{
        val list = ArrayList<Detail>()
        for(d in adapter!!.datas){
            if(d.detailId in adapter!!.selectedItem)
                list.add(d)
        }
        return list
    }
}
