package com.example.client.ui.category

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import com.example.client.data.AppDatabase
import com.example.client.data.Category
import com.example.client.data.CategoryViewAdapter
import com.example.client.databinding.ActivitySettingCategoryBinding

lateinit var settingCatevityViewBinding:ActivitySettingCategoryBinding
class SettingCategoryActivity : AppCompatActivity() {
    private lateinit var categoryList1 : List<Category>
    private lateinit var categoryList2 : List<Category>
    private lateinit var adapter1 : CategoryViewAdapter
    private lateinit var adapter2 : CategoryViewAdapter
    val roomDb = AppDatabase.getCategoryInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingCatevityViewBinding = ActivitySettingCategoryBinding.inflate(layoutInflater)
        setContentView(settingCatevityViewBinding.root)


        if (roomDb != null) {
            // gridview에 뿌릴 지출 카테고리 정보
            categoryList1 = roomDb.CategoryDao().selectByTypeId(1)
            // gridview에 뿌릴 수입 카테고리 정보
            categoryList2 = roomDb.CategoryDao().selectByTypeId(2)
            // gridview에 adapter 연결
            adapter1 = CategoryViewAdapter(this, categoryList1, -1)
            adapter2 = CategoryViewAdapter(this, categoryList2, -1)
            settingCatevityViewBinding.settingCategoryList.adapter = adapter1
            settingCatevityViewBinding.settingCategoryList2.adapter = adapter2

            // item 개수에 맞게 gridview height 설정
            val list1Params: ViewGroup.LayoutParams = settingCatevityViewBinding.settingCategoryList.layoutParams
            if ((categoryList1.size / 3 != 0) or (categoryList1.size < 3))
                list1Params.height = ConvertDPtoPX(this, 55) * (categoryList1.size / 3 + 1)
            else
                list1Params.height = ConvertDPtoPX(this, 55) * (categoryList1.size / 3)
            settingCatevityViewBinding.settingCategoryList.layoutParams = list1Params

            val list2Params: ViewGroup.LayoutParams = settingCatevityViewBinding.settingCategoryList2.layoutParams
            if ((categoryList2.size / 3 != 0) or (categoryList2.size < 3))
                list2Params.height = ConvertDPtoPX(this, 55) * (categoryList2.size / 3 + 1)
            else
                list2Params.height = ConvertDPtoPX(this, 55) * (categoryList2.size / 3)
            settingCatevityViewBinding.settingCategoryList2.layoutParams = list2Params
        }
        settingCatevityViewBinding.settingCategoryDeleteButton.visibility = View.GONE
        settingCatevityViewBinding.settingCategoryModifyButton.visibility = View.GONE
        // 뒤로가기
        settingCatevityViewBinding.settingCategoryBackButton.setOnClickListener() {
            super.onBackPressed()
        }
        // 카테고리 추가 화면으로 이동
        settingCatevityViewBinding.settingCategoryAddButton.setOnClickListener() {
            val intent = Intent(this, AddCategoryActivity::class.java)
            startActivity(intent)
        }
        settingCatevityViewBinding.settingCategoryDeleteButton.setOnClickListener(){
            // 정말로 삭제하시겠습니까? modal창
            val deleteCategoryId : Int = roomDb!!.CategoryDao().selectByName((currentFocus as AppCompatButton).text.toString())
            val deleteCategory : Category = roomDb!!.CategoryDao().selectById(deleteCategoryId)

            if(deleteCategoryId != 0) {
                roomDb!!.CategoryDao().deleteCategoryById(deleteCategoryId)
                roomDb!!.CategoryDao().updateCategoryOrder(deleteCategoryId, deleteCategory.typeId)
            }

            // 목록 새로고침
            this.onResume()
        }
        settingCatevityViewBinding.settingCategoryModifyButton.setOnClickListener(){
            val intent = Intent(this, UpdateCategoryActivity::class.java)
            println(roomDb!!.CategoryDao().selectByName((currentFocus as AppCompatButton).text.toString()))
            intent.putExtra("categoryId",roomDb!!.CategoryDao().selectByName((currentFocus as AppCompatButton).text.toString()))
            startActivity((intent))
        }



    }
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        // 화면 빈공간을 누르면 선택 해제


        return super.dispatchTouchEvent(ev)
    }

    override fun onResume() {
        super.onResume()
        when {
            // 지출 카테고리를 삭제한 경우 -> 지출 카테고리 gridview 새로고침
            settingCatevityViewBinding.settingCategoryList.hasFocus() -> {
                categoryList1 = roomDb!!.CategoryDao().selectByTypeId(1)
                adapter1 = CategoryViewAdapter(this, categoryList1, -1)
                settingCatevityViewBinding.settingCategoryList.adapter = adapter1
            }
            // 수입 카테고리를 삭제한 경우 -> 수입 카테고리 gridview 새로고침
            settingCatevityViewBinding.settingCategoryList2.hasFocus() -> {
                categoryList2 = roomDb!!.CategoryDao().selectByTypeId(2)
                adapter2 = CategoryViewAdapter(this, categoryList2, -1)
                settingCatevityViewBinding.settingCategoryList2.adapter = adapter2
            }
            else -> {

            }
        }

    }
    // dp를 픽셀 단위로 변환하는 함수, gridview height 설정에 사용
    fun ConvertDPtoPX(context: Context, dp: Int): Int {
        val density: Float = context.getResources().getDisplayMetrics().density
        return Math.round(dp.toFloat() * density)
    }
}

