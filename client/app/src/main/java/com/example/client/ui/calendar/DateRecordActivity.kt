package com.example.client.ui.calendar

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.client.data.adapter.ItemDecoration
import com.example.client.data.AppDatabase
import com.example.client.data.List
import com.example.client.data.adapter.DateRecordAdapter
import com.example.client.databinding.ActivityDateRecordBinding

class DateRecordActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityDateRecordBinding
    private val roomDb = AppDatabase.getListInstance(this)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=ActivityDateRecordBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val calendarIntent = intent
        val year = calendarIntent.getStringExtra("year") as String
        val month = calendarIntent.getStringExtra("month") as String
        val day = calendarIntent.getStringExtra("day") as String

        val expenseList : kotlin.collections.List<List> = roomDb!!.ListDao().selectByDate(year, month, day).filter { list: List -> list.typeId==1 }
        val incomeList : kotlin.collections.List<List> = roomDb!!.ListDao().selectByDate(year, month, day).filter { list: List -> list.typeId==2 }
        var expenseSum = expenseList.sumOf { list -> list.price.replace(",","").toInt() }
        var incomeSum = incomeList.sumOf { list -> list.price.replace(",","").toInt() }

        val decoration = ItemDecoration(20)
        viewBinding.lvExpense.addItemDecoration(decoration)
        viewBinding.lvIncome.addItemDecoration(decoration)
        viewBinding.lvIncome.layoutManager=LinearLayoutManager(this)

        viewBinding.date.text = "$month"+"월 $day"+"일"
        viewBinding.priceExpense.text = "-$expenseSum"
        when(expenseList.size){
            0 -> {
                viewBinding.tvExpense.visibility = View.GONE
                viewBinding.priceExpense.visibility = View.GONE
            }
            else -> {
                viewBinding.tvExpense.visibility = View.VISIBLE
                viewBinding.priceExpense.visibility = View.VISIBLE
                viewBinding.lvExpense.layoutManager=LinearLayoutManager(this)
                viewBinding.lvExpense.adapter= DateRecordAdapter(this,expenseList)
            }
        }
        when(incomeList.size){
            0 -> {
                viewBinding.tvIncome.visibility = View.GONE
                viewBinding.priceIncome.visibility = View.GONE
            }
            else -> {
                viewBinding.tvIncome.visibility = View.VISIBLE
                viewBinding.priceIncome.visibility = View.VISIBLE
                viewBinding.priceIncome.text = "+$incomeSum"
                viewBinding.lvIncome.adapter= DateRecordAdapter(this,incomeList)
            }
        }
        // 뒤로가기
        viewBinding.backButton.setOnClickListener {
            super.onBackPressed()
        }
    }
}