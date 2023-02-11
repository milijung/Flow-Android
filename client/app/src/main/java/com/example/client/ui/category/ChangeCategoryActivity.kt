package com.example.client.ui.category

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.SearchView
import com.example.client.data.adapter.CategoryViewAdapter
import com.example.client.R
import com.example.client.data.*
import com.example.client.databinding.ActivityChangeCategoryBinding
import com.example.client.ui.board.AddListActivity
import com.example.client.ui.board.ListDetailActivity
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class ChangeCategoryActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityChangeCategoryBinding
    private lateinit var categoryList : ArrayList<Category>
    private lateinit var searchCategoryList : ArrayList<Category>
    private lateinit var adapter: CategoryViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityChangeCategoryBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

        val listDetailIntent = intent
        val listId:Int = listDetailIntent.getIntExtra("detailId",-2)
        val categoryType : Int = listDetailIntent.getIntExtra("typeId",1)
        val selectedCategoryPosition: Int = listDetailIntent.getIntExtra("order",0)
        val userId = listDetailIntent.getIntExtra("userId",-1)
        val price = listDetailIntent.getStringExtra("price")
        var memo = listDetailIntent.getStringExtra("memo")
        val shop = listDetailIntent.getStringExtra("shop")
        val year = listDetailIntent.getStringExtra("year")
        val month = listDetailIntent.getStringExtra("month")
        val day = listDetailIntent.getStringExtra("day")
        val time = listDetailIntent.getStringExtra("time")
        var isBudgetIncluded = listDetailIntent.getBooleanExtra("isBudgetIncluded",true)
        var isKeywordIncluded = listDetailIntent.getBooleanExtra("isKeywordIncluded",false)
        val roomDb = AppDatabase.getInstance(this)

        categoryList = roomDb!!.CategoryDao().selectAll().filter { category -> category.typeId==categoryType } as ArrayList<Category>
        adapter = CategoryViewAdapter(this, categoryList, selectedCategoryPosition)
        val prevCategoryId = roomDb!!.CategoryDao().selectByOrder(categoryType,selectedCategoryPosition)
        viewBinding.changeCategoryList.adapter = adapter
        viewBinding.changeCategoryButton.text = getText(R.string.finish_button)

        // 완료하기 버튼 클릭
        viewBinding.changeCategoryButton.setOnClickListener(){
            val selectedCategoryId = roomDb.CategoryDao().selectByName((viewBinding.changeCategoryList.focusedChild as AppCompatButton).text.toString())
            when(listId){
                -2->{
                    // 내역 추가 화면으로 이동. category id를 담아서 전송
                    val intent = Intent(this, AddListActivity::class.java)
                    intent.putExtra("categoryId",selectedCategoryId)
                    intent.putExtra("typeId",categoryType)
                    startActivity(intent)
                    finish()
                }
                else->{
                    val isKeyword : Keyword = roomDb!!.KeywordDao().selectByKeyword(shop!!)
                    if((isKeyword != null)  ){
                        // 키워드 삭제
                        if(isKeyword.isUserCreated){
                            roomDb.KeywordDao().deleteKeyword(prevCategoryId,shop)

                        }
                    }

                    // 내역 상세 화면으로 이동. 내역 id를 담아서 전송
                    val intent = Intent(this, ListDetailActivity::class.java)
                    intent.putExtra("userId",userId)
                    intent.putExtra("detailId",listId)
                    intent.putExtra("typeId",categoryType)
                    intent.putExtra("categoryId",selectedCategoryId)
                    intent.putExtra("price",price)
                    intent.putExtra("memo",memo)
                    intent.putExtra("shop",shop)
                    intent.putExtra("year",year)
                    intent.putExtra("month",month)
                    intent.putExtra("day",day)
                    intent.putExtra("time",time)
                    intent.putExtra("isBudgetIncluded",isBudgetIncluded)
                    intent.putExtra("isKeywordIncluded",isKeywordIncluded)
                    startActivity(intent)
                    finish()
                }
            }
        }

        // 뒤로 가기
        viewBinding.changeCategoryBackButton.setOnClickListener(){
            super.onBackPressed()
        }
        // 카테고리 추가 화면으로 이동
        viewBinding.changeCategoryAddButton.setOnClickListener(){
            val intent = Intent(this, AddCategoryActivity::class.java)
            intent.putExtra("listId",listId)
            intent.putExtra("typeId",categoryType)
            intent.putExtra("order",selectedCategoryPosition)
            startActivity(intent)
        }
        // 카테고리 검색
        viewBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (roomDb != null) {
                    searchCategoryList =
                        categoryList.filter { category -> category.name.contains(newText.toString().trim()) } as ArrayList<Category>
                    adapter.updateCategoryList(searchCategoryList)
                    viewBinding.changeCategoryList.adapter = adapter
                }
                return true
            }
        })

    }


}

