package com.example.client.ui.calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.client.APIObject
import com.example.client.api.RecordsOfDate
import com.example.client.api.api
import com.example.client.data.AppDatabase
import com.example.client.data.Detail
import com.example.client.data.adapter.ItemDecoration
import com.example.client.data.adapter.RecordAdapter
import com.example.client.databinding.ActivityDateRecordBinding
import kotlinx.coroutines.InternalCoroutinesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@InternalCoroutinesApi
class DateRecordActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityDateRecordBinding
    private val request: api = APIObject.getInstance().create(api::class.java)
    var expenseList = ArrayList<Detail>()
    var incomeList = ArrayList<Detail>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=ActivityDateRecordBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        val roomDb = AppDatabase.getInstance(this)
        val calendarIntent = intent
        val year = calendarIntent.getStringExtra("year")!!.toInt()
        val month = calendarIntent.getStringExtra("month")!!.toInt()
        val day = calendarIntent.getStringExtra("day")!!.toInt()
        val userId = roomDb!!.UserDao().getUserId()

        val decoration = ItemDecoration(20)
        viewBinding.lvExpense.addItemDecoration(decoration)
        viewBinding.lvIncome.addItemDecoration(decoration)
        viewBinding.lvIncome.layoutManager=LinearLayoutManager(this)
        viewBinding.date.text = "${month}월 ${day}일"

        // 뒤로가기
        viewBinding.backButton.setOnClickListener {
            super.onBackPressed()
        }

        getRecordsOfDate(userId, year,month,day)
    }
    private fun getRecordsOfDate(userId: Int, year: Int, month: Int, day : Int) {
        val call = request.getRecordsOfDate(year,month,day,userId)
        viewBinding.progressBar.visibility = View.VISIBLE
        expenseList.clear()
        incomeList.clear()
        call.enqueue(object: Callback<RecordsOfDate> {
            override fun onResponse(call: Call<RecordsOfDate>, response: Response<RecordsOfDate>){
                if(response.body()!!.isSuccess){
                    val recordsInfo = response.body()?.result!!
                    println(recordsInfo)
                    // 총 지출금액과 수입금액 가져오기
                    if (recordsInfo != null) {
                        for(price in recordsInfo.totalAmount){
                            if(price.isExp == 1)
                                viewBinding.priceExpense.text = "-${price.total}"
                            else
                                viewBinding.priceIncome.text = "+${price.total}"
                        }
                        // 내역 가져오기
                        val records= recordsInfo.detail
                        if(records != null){
                            for(r in records){
                                when(r.typeId){
                                    1 -> expenseList.add(r)
                                    else -> incomeList.add(r)
                                }
                            }
                        }
                    }

                    when(expenseList?.size){
                        0 -> {
                            viewBinding.tvExpense.visibility = View.GONE
                            viewBinding.priceExpense.visibility = View.GONE
                        }
                        else -> {
                            viewBinding.tvExpense.visibility = View.VISIBLE
                            viewBinding.priceExpense.visibility = View.VISIBLE
                            viewBinding.lvExpense.layoutManager=LinearLayoutManager(applicationContext)
                            viewBinding.lvExpense.adapter= RecordAdapter(applicationContext,expenseList!!)
                        }
                    }
                    when(incomeList?.size){
                        0 -> {
                            viewBinding.tvIncome.visibility = View.GONE
                            viewBinding.priceIncome.visibility = View.GONE
                        }
                        else -> {
                            viewBinding.tvIncome.visibility = View.VISIBLE
                            viewBinding.priceIncome.visibility = View.VISIBLE
                            viewBinding.lvIncome.adapter= RecordAdapter(applicationContext,incomeList!!)
                        }
                    }
                    viewBinding.progressBar.visibility = View.GONE
                }
                else{
                    viewBinding.progressBar.visibility = View.GONE
                    Toast.makeText(this@DateRecordActivity, "내역을 불러오지 못했습니다\n  나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<RecordsOfDate>, t: Throwable) {
                viewBinding.progressBar.visibility = View.GONE
                Toast.makeText(this@DateRecordActivity, "내역을 불러오지 못했습니다\n  나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }
        })
    }
}