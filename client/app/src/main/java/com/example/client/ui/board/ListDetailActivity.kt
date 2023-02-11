package com.example.client.ui.board

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.client.ui.category.ChangeCategoryActivity
import com.example.client.R
import com.example.client.api.HttpConnection
import com.example.client.api.UpdateDetailData
import com.example.client.data.*
import com.example.client.databinding.ActivityListDetailBinding
import com.example.client.ui.navigation.BottomNavigationActivity
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class ListDetailActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityListDetailBinding
    private lateinit var selectedCategory : Category
    private val httpConnection : HttpConnection = HttpConnection()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityListDetailBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val listDetailIntent = intent
        val userId = listDetailIntent.getIntExtra("userId",-1)
        val detailId = listDetailIntent.getIntExtra("detailId",1) // 넘겨받은 내역 id
        val typeId = listDetailIntent.getIntExtra("typeId",1)
        val categoryId = listDetailIntent.getIntExtra("categoryId",1)
        val price = listDetailIntent.getStringExtra("price")
        var memo = listDetailIntent.getStringExtra("memo")
        val shop = listDetailIntent.getStringExtra("shop")
        val year = listDetailIntent.getStringExtra("year")
        val month = listDetailIntent.getStringExtra("month")
        val day = listDetailIntent.getStringExtra("day")
        val time = listDetailIntent.getStringExtra("time")
        var isBudgetIncluded = listDetailIntent.getBooleanExtra("isBudgetIncluded",true)
        var isKeywordIncluded = listDetailIntent.getBooleanExtra("isKeywordIncluded",false)

        val roomDb = AppDatabase.getInstance(this) // 내역 DB
        val bankStatementRepository = BankStatementRepository(this)

        if (roomDb != null) {
            selectedCategory = roomDb.CategoryDao().selectById(categoryId)
        }// 넘겨받은 내역의 카테고리 Data

        // 넘겨받은 내역의 Data를 화면에 표시
        viewBinding.listDetailButton.text = getText(R.string.finish_button)
        viewBinding.listDetailMemoContent.setText(memo)
        viewBinding.listDetailTime.text = "${year}년 ${month}월 ${day}일 ${time}"
        viewBinding.listDetailPlace.text = shop
        viewBinding.listDetailPrice.text = "${price}원"
        viewBinding.listDetailCategoryNameButton.text = selectedCategory.name
        viewBinding.listDetailBubble.text = "${shop} 내역의 카테고리가 선택한 카테고리로 모두 바뀌게 돼요!"
        viewBinding.listDetailSwitch1.isChecked = isBudgetIncluded
        viewBinding.listDetailSwitch2.isChecked = isKeywordIncluded

        viewBinding.listDetailBackButton.setOnClickListener(){
            // 내용이 변경되지 않는다는 modal창 추가
            //뒤로가기
            super.onBackPressed()
        }

        viewBinding.listDetailCategoryNameButton.setOnClickListener(){
            // 카테고리 수정 화면으로 이동
            val intent = Intent(this, ChangeCategoryActivity::class.java)
            intent.putExtra("detailId",detailId)
            intent.putExtra("typeId",typeId)
            intent.putExtra("order",selectedCategory.order)
            intent.putExtra("userId",userId)
            intent.putExtra("price",price)
            intent.putExtra("memo",viewBinding.listDetailMemoContent.text.toString())
            intent.putExtra("shop",shop)
            intent.putExtra("year",year)
            intent.putExtra("month",month)
            intent.putExtra("day",day)
            intent.putExtra("time",time)
            intent.putExtra("isBudgetIncluded",viewBinding.listDetailSwitch1.isChecked)
            intent.putExtra("isKeywordIncluded",viewBinding.listDetailSwitch2.isChecked)
            startActivity(intent)
        }
        viewBinding.listDetailButton.setOnClickListener(){
            // Memo 내용, 예산 저장 여부 DB에 update
            httpConnection.updateList(userId, detailId, UpdateDetailData(categoryId,viewBinding.listDetailMemoContent.text.toString(),viewBinding.listDetailSwitch1.isChecked, viewBinding.listDetailSwitch2.isChecked))
            // Board 화면으로 이동
            val intent = Intent(this, BottomNavigationActivity::class.java)
            intent.putExtra("pageId",1)
            startActivity(intent)
            finish()
        }
    }
}