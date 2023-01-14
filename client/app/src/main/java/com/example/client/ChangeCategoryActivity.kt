package com.example.client

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.GridView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.SearchView
import com.example.client.databinding.ActivityChangeCategoryBinding
import kotlin.collections.List

class ChangeCategoryActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityChangeCategoryBinding
    private lateinit var categoryList : List<Category>
    private lateinit var adapter: CategoryViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityChangeCategoryBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

        val listDetailIntent = intent
        val listId:Int = listDetailIntent.getIntExtra("listId",1)
        val categoryType : Int = listDetailIntent.getIntExtra("typeId",1)
        val selectedCategoryPosition: Int = listDetailIntent.getIntExtra("order",0)
        val roomDb = AppDatabase.getCategoryInstance(this)

        if(roomDb != null){
            categoryList = roomDb.CategoryDao().selectByTypeId(categoryType)
            val categoryLength = categoryList.size
            if(categoryLength == 0){
                roomDb.CategoryDao().insert(Category("하루세끼",R.drawable.ic_category_food,1,0,false))
                roomDb.CategoryDao().insert(Category("주거·통신",R.drawable.ic_category_living,1,1,false))
                roomDb.CategoryDao().insert(Category("교통관련",R.drawable.ic_category_traffic,1,2,false))
                roomDb.CategoryDao().insert(Category("생필품",R.drawable.ic_category_market,1,3,false))
                roomDb.CategoryDao().insert(Category("나를 위한",R.drawable.ic_category_formeexpense,1,4,false))
                roomDb.CategoryDao().insert(Category("선물준비",R.drawable.ic_category_present,1,5,false))
                roomDb.CategoryDao().insert(Category("자기계발",R.drawable.ic_category_selfimprovement,1,6,false))
                roomDb.CategoryDao().insert(Category("카페·간식",R.drawable.ic_category_cafe,1,7,false))
                roomDb.CategoryDao().insert(Category("저축",R.drawable.ic_category_saving,1,8,false))
                roomDb.CategoryDao().insert(Category("술·외식",R.drawable.ic_category_alchol,1,9,false))
                roomDb.CategoryDao().insert(Category("의료·건강",R.drawable.ic_category_medical,1,10,false))
                roomDb.CategoryDao().insert(Category("오락·취미",R.drawable.ic_category_entertainment,1,11,false))
                roomDb.CategoryDao().insert(Category("여행",R.drawable.ic_category_travel,1,12,false))
                roomDb.CategoryDao().insert(Category("자산이동",R.drawable.ic_category_assetmovement,1,13,false))
                roomDb.CategoryDao().insert(Category("기타지출",R.drawable.ic_category_others,1,14,false))
                roomDb.CategoryDao().insert(Category("애견용품",R.drawable.ic_category_user,1,15,true))
                roomDb.CategoryDao().insert(Category("회비",R.drawable.ic_category_user,1,16,true))
                roomDb.CategoryDao().insert(Category("수입",R.drawable.ic_category_income,2,0,false))
                roomDb.CategoryDao().insert(Category("장학금",R.drawable.ic_category_income_user,2,1,true))
                roomDb.CategoryDao().insert(Category("이자",R.drawable.ic_category_income_user,2,2,true))
            }

            adapter = CategoryViewAdapter(this, categoryList, selectedCategoryPosition)
            viewBinding.changeCategoryList.adapter = adapter
            viewBinding.changeCategoryList.choiceMode = GridView.CHOICE_MODE_SINGLE
            viewBinding.changeCategoryButton.setOnClickListener(){
                // 내역의 카테고리 변경
                val listDb = AppDatabase.getListInstance(this)
                val selectedCategoryId = roomDb.CategoryDao().selectByName((viewBinding.changeCategoryList.focusedChild as AppCompatButton).text.toString())
                listDb!!.ListDao().updateCategory(listId,selectedCategoryId)

                // 내역 상세 화면으로 이동. 내역 id를 담아서 전송
                val intent = Intent(this, ListDetailActivity::class.java)
                intent.putExtra("listId",listId)
                startActivity(intent)
            }
            viewBinding.changeCategoryButton.text = getText(R.string.finish_button)

        }

        viewBinding.changeCategoryBackButton.setOnClickListener(){
            // 뒤로가기
            super.onBackPressed()
        }
        viewBinding.changeCategoryAddButton.setOnClickListener(){
            // 카테고리 추가 화면으로 이동
            val intent = Intent(this, AddCategoryActivity::class.java)
            intent.putExtra("listId",listId)
            intent.putExtra("typeId",categoryType)
            intent.putExtra("order",selectedCategoryPosition)
            startActivity(intent)
        }
        viewBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (roomDb != null) {
                    categoryList = roomDb.CategoryDao().searchCategory(newText.toString())
                    adapter = CategoryViewAdapter(this@ChangeCategoryActivity, categoryList, selectedCategoryPosition)
                    viewBinding.changeCategoryList.adapter = adapter
                }
                return true
            }
        })

    }
}

