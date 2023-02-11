package com.example.client.ui.board

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.client.R
import com.example.client.api.HttpConnection
import com.example.client.data.AppDatabase
import com.example.client.data.Detail
import com.example.client.databinding.ActivityAddListBinding
import com.example.client.ui.category.ChangeCategoryActivity
import com.example.client.ui.navigation.BottomNavigationActivity
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
        val roomDb = AppDatabase.getInstance(this)
        val newListIntent = intent
        var categoryId : Int = newListIntent.getIntExtra("categoryId",1)
        var typeId : Int = newListIntent.getIntExtra("typeId",1)
        var selectedCategory = roomDb!!.CategoryDao().selectById(categoryId)
        val userId : Int = roomDb.UserDao().getUserId()

        viewBinding.addListBubble.text = "내역의 카테고리가 선택한 카테고리로 모두 바뀌게 돼요!"
        viewBinding.addListCategoryNameButton.text = selectedCategory.name
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
            viewBinding.addListTag.text = viewBinding.addListOption1.text
            viewBinding.addListTag.setBackgroundResource(R.drawable.expense_round)
            viewBinding.addListOption1.visibility = View.GONE
            viewBinding.addListOption2.visibility = View.GONE
        }
        viewBinding.addListOption2.setOnClickListener {
            typeId = 2
            viewBinding.addListTag.text = viewBinding.addListOption2.text
            viewBinding.addListTag.setBackgroundResource(R.drawable.income_round)
            viewBinding.addListOption1.visibility = View.GONE
            viewBinding.addListOption2.visibility = View.GONE
        }
        viewBinding.addListCategoryNameButton.setOnClickListener {
            val intent = Intent(this, ChangeCategoryActivity::class.java)
            intent.putExtra("typeId",typeId)
            intent.putExtra("order",selectedCategory.order)
            startActivity(intent)
        }
        viewBinding.addListButton.setOnClickListener {
            if((viewBinding.addListDate.text.toString()=="")
                or (viewBinding.addListTime.text.toString()== "")
                or (viewBinding.addListPrice.text.toString()=="")
                or (viewBinding.addListPlace.text.toString()=="")
            ){
                // 내용 작성해달라는 모달창
            }else{
                httpConnection.insertList(userId,Detail(
                    userId,
                    categoryId,
                    viewBinding.addListDatepicker.year.toString(),
                    (viewBinding.addListDatepicker.month+1).toString(),
                    viewBinding.addListDatepicker.dayOfMonth.toString(),
                    viewBinding.addListTime.text.toString(),
                    viewBinding.addListPrice.text.toString(),
                    viewBinding.addListPlace.text.toString(),
                    typeId,
                    viewBinding.addListSwitch1.isChecked,
                    viewBinding.addListSwitch2.isChecked,
                    viewBinding.addListMemoContent.text.toString()
                ))
                val intent = Intent(this, BottomNavigationActivity::class.java)
                intent.putExtra("pageId",1)
                startActivity(intent)
                finish()
            }
        }
    }
    private fun openDialog(option:String){
        when(option){
            "none" ->{
                if(viewBinding.addListDatepicker.visibility == View.VISIBLE){
                    viewBinding.addListDatepicker.visibility = View.GONE
                }else if(viewBinding.addListTimepicker.visibility == View.VISIBLE){
                    viewBinding.addListTimepicker.visibility = View.GONE
                }else if(viewBinding.addListOption1.visibility == View.VISIBLE){
                    viewBinding.addListOption1.visibility == View.GONE
                    viewBinding.addListOption2.visibility == View.GONE
                }
            }
            "datePicker" -> {
                viewBinding.addListDatepicker.visibility = View.VISIBLE
                if(viewBinding.addListTimepicker.visibility == View.VISIBLE){
                    viewBinding.addListTimepicker.visibility = View.GONE
                }else if(viewBinding.addListOption1.visibility == View.VISIBLE){
                    viewBinding.addListOption1.visibility == View.GONE
                    viewBinding.addListOption2.visibility == View.GONE
                }
            }
            "timePicker" -> {
                viewBinding.addListTimepicker.visibility = View.VISIBLE
                if(viewBinding.addListDatepicker.visibility == View.VISIBLE){
                    viewBinding.addListDatepicker.visibility = View.GONE
                }else if(viewBinding.addListOption1.visibility == View.VISIBLE){
                    viewBinding.addListOption1.visibility == View.GONE
                    viewBinding.addListOption2.visibility == View.GONE
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
