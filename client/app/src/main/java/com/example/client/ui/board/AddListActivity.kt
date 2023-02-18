package com.example.client.ui.board

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.client.R
import com.example.client.api.HttpConnection
import com.example.client.api.InsertDetailRequestData
import com.example.client.data.AppDatabase
import com.example.client.databinding.ActivityAddListBinding
import com.example.client.ui.category.ChangeCategoryActivity
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class AddListActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityAddListBinding
    val httpConnection : HttpConnection = HttpConnection()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityAddListBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val newListIntent = intent
        var categoryId : Int = newListIntent.getIntExtra("categoryId",1)
        var typeId : Int = newListIntent.getIntExtra("typeId",1)

        val roomDb = AppDatabase.getInstance(this)
        val type1DefaultCategory = roomDb!!.CategoryDao().selectById(1)
        val type2DefaultCategory = roomDb.CategoryDao().selectById(16)
        val userId = roomDb.UserDao().getUserId()

        viewBinding.addListBubble.text = "내역의 카테고리가 선택한 카테고리로 모두 바뀌게 돼요!"
        viewBinding.addListCategoryNameButton.text = roomDb.CategoryDao().selectById(categoryId).name
        viewBinding.addListTag.text = when(typeId){1->viewBinding.addListOption1.text else->viewBinding.addListOption2.text}
        viewBinding.addListTag.setBackgroundResource( when(typeId){1->R.drawable.expense_round else->R.drawable.income_round})

        viewBinding.addListButton.setText(R.string.finish_button)

        viewBinding.addListDate.setOnClickListener {
            openDialog("datePicker")
        }
        viewBinding.addListTime.setOnClickListener {
            openDialog("timePicker")
        }
        viewBinding.addListTagButton.setOnClickListener {
            openDialog("typePicker")
        }
        viewBinding.addListPlace.setOnClickListener {
            openDialog("none")
        }
        viewBinding.addListBackButton.setOnClickListener {
            super.onBackPressed()
        }
        viewBinding.addListDatepicker.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            viewBinding.addListDate.setText("${year}년 ${monthOfYear+1}월 ${dayOfMonth}일")
            view.visibility = View.GONE
        }
        viewBinding.addListTimepickerButton1.setOnClickListener {
            viewBinding.addListTime.setText(viewBinding.addListTimepickerButton1.text)
            viewBinding.addListTimepicker.visibility = View.GONE
        }
        viewBinding.addListTimepickerButton2.setOnClickListener {
            viewBinding.addListTime.setText("${viewBinding.addListTimepicker.hour}:${viewBinding.addListTimepicker.minute}")
            viewBinding.addListTimepicker.visibility = View.GONE
        }
        viewBinding.addListOption1.setOnClickListener {
            typeId = 1
            categoryId = 1
            viewBinding.addListTag.text = viewBinding.addListOption1.text
            viewBinding.addListTag.setBackgroundResource(R.drawable.expense_round)
            viewBinding.addListOption1.visibility = View.GONE
            viewBinding.addListOption2.visibility = View.GONE
            viewBinding.addListCategoryNameButton.text = type1DefaultCategory.name
        }
        viewBinding.addListOption2.setOnClickListener {
            typeId = 2
            categoryId = 16
            viewBinding.addListTag.text = viewBinding.addListOption2.text
            viewBinding.addListTag.setBackgroundResource(R.drawable.income_round)
            viewBinding.addListOption1.visibility = View.GONE
            viewBinding.addListOption2.visibility = View.GONE
            viewBinding.addListCategoryNameButton.text =type2DefaultCategory.name
        }
        viewBinding.addListCategoryNameButton.setOnClickListener {
            val intent = Intent(this, ChangeCategoryActivity::class.java)
            intent.putExtra("typeId",typeId)
            intent.putExtra("order",type1DefaultCategory.order)
            startActivity(intent)
        }
        viewBinding.addListButton.setOnClickListener {
            when {
                viewBinding.addListDate.text.toString()=="" -> {
                    Toast.makeText(this, "날짜를 선택해주세요", Toast.LENGTH_SHORT).show()
                }
                viewBinding.addListTime.text.toString()== "" -> {
                    Toast.makeText(this, "시간을 선택해주세요", Toast.LENGTH_SHORT).show()
                }
                viewBinding.addListPrice.text.toString()=="" -> {
                    Toast.makeText(this, "가격을 입력해주세요", Toast.LENGTH_SHORT).show()
                }
                viewBinding.addListPlace.text.toString()=="" -> {
                    Toast.makeText(this, "거래처를 입력해주세요", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    httpConnection.insertList(this, userId,InsertDetailRequestData(
                        userId,
                        categoryId,
                        viewBinding.addListDatepicker.year.toString(),
                        (viewBinding.addListDatepicker.month+1).toString(),
                        viewBinding.addListDatepicker.dayOfMonth.toString(),
                        viewBinding.addListTime.text.toString(),
                        Integer.parseInt(viewBinding.addListPrice.text.toString()),
                        viewBinding.addListPlace.text.toString(),
                        typeId,
                        viewBinding.addListSwitch1.isChecked,
                        viewBinding.addListSwitch2.isChecked,
                        viewBinding.addListMemoContent.text.toString()
                    ))
                }
            }
        }
    }
    private fun openDialog(option:String){
        when(option){
            "none" ->{
                when {
                    viewBinding.addListDatepicker.visibility == View.VISIBLE -> {
                        viewBinding.addListDatepicker.visibility = View.GONE
                    }
                    viewBinding.addListTimepicker.visibility == View.VISIBLE -> {
                        viewBinding.addListTimepicker.visibility = View.GONE
                    }
                    viewBinding.addListOption1.visibility == View.VISIBLE -> {
                        viewBinding.addListOption1.visibility = View.GONE
                        viewBinding.addListOption2.visibility = View.GONE
                    }
                }
            }
            "datePicker" -> {
                viewBinding.addListDatepicker.visibility = View.VISIBLE
                if(viewBinding.addListTimepicker.visibility == View.VISIBLE){
                    viewBinding.addListTimepicker.visibility = View.GONE
                }else if(viewBinding.addListOption1.visibility == View.VISIBLE){
                    viewBinding.addListOption1.visibility = View.GONE
                    viewBinding.addListOption2.visibility = View.GONE
                }
            }
            "timePicker" -> {
                viewBinding.addListTimepicker.visibility = View.VISIBLE
                if(viewBinding.addListDatepicker.visibility == View.VISIBLE){
                    viewBinding.addListDatepicker.visibility = View.GONE
                }else if(viewBinding.addListOption1.visibility == View.VISIBLE){
                    viewBinding.addListOption1.visibility = View.GONE
                    viewBinding.addListOption2.visibility = View.GONE
                }
            }
            else ->{
                viewBinding.addListOption1.visibility = View.VISIBLE
                viewBinding.addListOption2.visibility = View.VISIBLE
                if(viewBinding.addListDatepicker.visibility == View.VISIBLE){
                    viewBinding.addListDatepicker.visibility = View.GONE
                }else if(viewBinding.addListTimepicker.visibility == View.VISIBLE){
                    viewBinding.addListTimepicker.visibility = View.GONE
                }
            }
        }
    }


}
