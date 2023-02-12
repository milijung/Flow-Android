package com.example.client.ui.category

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.appcompat.widget.AppCompatButton
import com.example.client.api.HttpConnection
import com.example.client.data.AppDatabase
import com.example.client.data.Category
import com.example.client.data.adapter.CategoryViewAdapter
import com.example.client.databinding.ActivitySettingCategoryBinding
import com.example.client.ui.navigation.BottomNavigationActivity
import kotlinx.coroutines.InternalCoroutinesApi

lateinit var settingCategoryViewBinding:ActivitySettingCategoryBinding
@InternalCoroutinesApi
class SettingCategoryActivity : AppCompatActivity() {
    private lateinit var categoryList : ArrayList<Category>
    private lateinit var adapter1 : CategoryViewAdapter
    private lateinit var adapter2 : CategoryViewAdapter
    val roomDb = AppDatabase.getInstance(this)
    private val httpConnection : HttpConnection = HttpConnection()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingCategoryViewBinding = ActivitySettingCategoryBinding.inflate(layoutInflater)
        setContentView(settingCategoryViewBinding.root)

        // 전체 카테고리 목록
        categoryList = roomDb!!.CategoryDao().selectAll() as ArrayList<Category>

        // gridview에 adapter 연결
        adapter1 = CategoryViewAdapter(this, categoryList.filter { category -> category.typeId ==1 } as ArrayList<Category>, -1)
        settingCategoryViewBinding.settingCategoryList.adapter = adapter1
        adapter2 = CategoryViewAdapter(this, categoryList.filter { category -> category.typeId ==2 } as ArrayList<Category>, -1)
        settingCategoryViewBinding.settingCategoryList2.adapter = adapter2

        // gridview height 설정
        val list1Params: ViewGroup.LayoutParams = settingCategoryViewBinding.settingCategoryList.layoutParams
        list1Params.height = getGridviewHeight(settingCategoryViewBinding.settingCategoryList.adapter.count)
        settingCategoryViewBinding.settingCategoryList.layoutParams = list1Params

        val list2Params: ViewGroup.LayoutParams = settingCategoryViewBinding.settingCategoryList2.layoutParams
        list2Params.height = getGridviewHeight(settingCategoryViewBinding.settingCategoryList2.adapter.count)
        settingCategoryViewBinding.settingCategoryList2.layoutParams = list2Params

        settingCategoryViewBinding.settingCategoryDeleteButton.visibility = View.GONE
        settingCategoryViewBinding.settingCategoryModifyButton.visibility = View.GONE

        // 뒤로가기
        settingCategoryViewBinding.settingCategoryBackButton.setOnClickListener() {
            val intent = Intent(this, BottomNavigationActivity::class.java)
            intent.putExtra("pageId",3)
            startActivity(intent)
            finish()
        }
        // 카테고리 추가 화면으로 이동
        settingCategoryViewBinding.settingCategoryAddButton.setOnClickListener() {
            val intent = Intent(this, AddCategoryActivity::class.java)
            startActivity(intent)
        }
        // 카테고리 삭제
        settingCategoryViewBinding.settingCategoryDeleteButton.setOnClickListener(){
            // 정말로 삭제하시겠습니까? modal창
            val deleteCategoryId : Int = roomDb!!.CategoryDao().selectByName((currentFocus as AppCompatButton).text.toString())
            val deleteCategory : Category = roomDb!!.CategoryDao().selectById(deleteCategoryId)

            if(deleteCategoryId != 0) {
                // 삭제하려는 카테고리에 소속된 list들 -> 카테고리를 "기타지출"로 변경
                roomDb!!.ListDao().updateListOfDeletedCategory(deleteCategoryId)
                // 카테고리 삭제
                roomDb!!.CategoryDao().deleteCategoryById(deleteCategoryId)

                //서버 카테고리 삭제
                httpConnection.deleteCategory(roomDb,1, deleteCategoryId)

               // 해당 카테고리에 연결된 키워드 삭제
                roomDb!!.KeywordDao().deleteByCategoryId(deleteCategoryId)
                // 카테고리 position 재정렬
                roomDb!!.CategoryDao().updateCategoryOrder(deleteCategoryId, deleteCategory.typeId)
            }
            // 목록 새로고침
            refreshCategoryList(deleteCategory)
        }
        // 카테고리 수정
        settingCategoryViewBinding.settingCategoryModifyButton.setOnClickListener(){
            val intent = Intent(this, UpdateCategoryActivity::class.java)
            intent.putExtra("categoryId",roomDb!!.CategoryDao().selectByName((currentFocus as AppCompatButton).text.toString()))
            startActivity((intent))
        }
    }
    // 카테고리 삭제 후 목록 refresh
    private fun refreshCategoryList(deleteCategory: Category) {
        var categoryListView : GridView
        var typeId : Int
        var adapter : CategoryViewAdapter
        when {
            settingCategoryViewBinding.settingCategoryList.hasFocus() -> {
                typeId = 1
                adapter = adapter1
                categoryListView = settingCategoryViewBinding.settingCategoryList
            }
            else -> {
                typeId = 2
                adapter = adapter2
                categoryListView = settingCategoryViewBinding.settingCategoryList2
            }
        }
        categoryList.remove(deleteCategory)
        adapter.updateCategoryList(categoryList.filter { category -> category.typeId==typeId } as ArrayList<Category>)
        categoryListView.adapter = adapter
        (categoryListView.adapter as CategoryViewAdapter).notifyDataSetChanged()
    }
    private fun getGridviewHeight(size : Int) : Int{
        return if ((size / 3 != 0) or (size < 3))
            ConvertDPtoPX(this, 55) * (size / 3 + 1)
        else
            ConvertDPtoPX(this, 55) * (size / 3)
    }

    // dp를 픽셀 단위로 변환
    private fun ConvertDPtoPX(context: Context, dp: Int): Int {
        val density: Float = context.getResources().getDisplayMetrics().density
        return Math.round(dp.toFloat() * density)
    }
}

