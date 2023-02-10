package com.example.client.ui.calendar

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.client.APIObject
import com.example.client.data.adapter.ItemDecoration
import com.example.client.data.DateRecordService
import com.example.client.data.adapter.DateRecordAdapter
import com.example.client.data.model.DateRecordData
import com.example.client.databinding.ActivityDateRecordBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DateRecordActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityDateRecordBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=ActivityDateRecordBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val calendarIntent = intent
        val year = calendarIntent.getStringExtra("year") as String
        val month = calendarIntent.getStringExtra("month") as String
        val day = calendarIntent.getStringExtra("day") as String


        val decoration = ItemDecoration(20)
        viewBinding.lvExpense.addItemDecoration(decoration)
        viewBinding.lvIncome.addItemDecoration(decoration)
        viewBinding.lvIncome.layoutManager=LinearLayoutManager(this)

        viewBinding.date.text = "$month"+"월 $day"+"일"

        //api 요청 - 총지출,수입 가져오기기
        val service: DateRecordService = APIObject.getInstance().create(DateRecordService::class.java)
        val call = service.getTotalAmount(year.toInt(),month.toInt(),day.toInt(),1)

        call.enqueue(object: Callback<DateRecordData>{
            override fun onResponse(call: Call<DateRecordData>, response: Response<DateRecordData>){
                if(response.isSuccessful){
                    val serverList=response.body()?.result

                    if (serverList != null) {
                        val totalExpense=serverList.totalAmount.find {it.isExp==1}
                        val totalIncome=serverList.totalAmount.find {it.isExp==2}

                        if (totalExpense != null) {
                            val expenseSum= totalExpense.total
                            viewBinding.priceExpense.text = "-${expenseSum}"
                        }
                        if (totalIncome != null) {
                            val incomeSum=totalIncome.total
                            viewBinding.priceIncome.text = "+${incomeSum}"
                        }
                    }

                    //내역 리스트 가져오기
                    val list= serverList?.transaction
                    val expenseList= list?.filter { it.isExp==1 }
                    val incomeList=list?.filter{it.isExp==2}

                    when(expenseList?.size){
                        0 -> {
                            viewBinding.tvExpense.visibility = View.GONE
                            viewBinding.priceExpense.visibility = View.GONE
                        }
                        else -> {
                            viewBinding.tvExpense.visibility = View.VISIBLE
                            viewBinding.priceExpense.visibility = View.VISIBLE
                            viewBinding.lvExpense.layoutManager=LinearLayoutManager(applicationContext)
                            viewBinding.lvExpense.adapter= DateRecordAdapter(applicationContext,expenseList!!)
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
                            viewBinding.lvIncome.adapter= DateRecordAdapter(applicationContext,incomeList!!)
                        }
                    }
                }
                else{ Log.w("Retrofit", "Response Not Successful ${response.code()}") }
            }
            override fun onFailure(call: Call<DateRecordData>, t: Throwable) {
                Log.w("Retrofit", "Error!", t)
            }
        })

        // 뒤로가기
        viewBinding.backButton.setOnClickListener {
            super.onBackPressed()
        }
    }
}