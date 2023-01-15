package com.example.client.ui.category

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.core.view.get
import com.example.client.R
import com.example.client.data.AppDatabase
import com.example.client.data.Category
import com.example.client.data.CategoryViewAdapter
import com.example.client.databinding.ActivitySettingCategoryBinding
import com.example.client.databinding.FragmentCategoryIconBinding

class SettingCategoryActivity : AppCompatActivity() {
    private lateinit var viewBinding:ActivitySettingCategoryBinding
    private lateinit var categoryList1 : List<Category>
    private lateinit var categoryList2 : List<Category>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySettingCategoryBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val roomDb = AppDatabase.getCategoryInstance(this)
        if (roomDb != null) {
            categoryList1 = roomDb.CategoryDao().selectByTypeId(1)
            categoryList2 = roomDb.CategoryDao().selectByTypeId(2)
            var adapter1 = CategoryViewAdapter(this, categoryList1, -1)
            var adapter2 = CategoryViewAdapter(this, categoryList2, -1)
            viewBinding.settingCategoryList.adapter = adapter1
            viewBinding.settingCategoryList2.adapter = adapter2
            viewBinding.settingCategoryList.choiceMode = GridView.CHOICE_MODE_SINGLE
            viewBinding.settingCategoryList2.choiceMode = GridView.CHOICE_MODE_SINGLE
            val list1Params: ViewGroup.LayoutParams = viewBinding.settingCategoryList.layoutParams
            if (categoryList1.size / 3 != 0)
                list1Params.height = ConvertDPtoPX(this, 55) * (categoryList1.size / 3 + 1)
            else
                list1Params.height = ConvertDPtoPX(this, 55) * (categoryList1.size / 3)
            viewBinding.settingCategoryList.layoutParams = list1Params

            val list2Params: ViewGroup.LayoutParams = viewBinding.settingCategoryList2.layoutParams
            if (categoryList2.size / 3 != 0)
                list2Params.height = ConvertDPtoPX(this, 55) * (categoryList2.size / 3 + 1)
            else
                list2Params.height = ConvertDPtoPX(this, 55) * (categoryList2.size / 3)
            viewBinding.settingCategoryList2.layoutParams = list2Params
        }

        viewBinding.settingCategoryBackButton.setOnClickListener() {
            // 뒤로가기
            super.onBackPressed()
        }
        viewBinding.settingCategoryAddButton.setOnClickListener() {
            // 카테고리 추가 화면으로 이동
            val intent = Intent(this, AddCategoryActivity::class.java)
            startActivity(intent)
        }
        viewBinding.settingCategoryList.setOnFocusChangeListener { v, hasFocus ->
            if (viewBinding.settingCategoryList.isFocused or viewBinding.settingCategoryList2.isFocused) {
                viewBinding.settingCategoryDeleteButton.visibility = View.VISIBLE
                viewBinding.settingCategoryModifyButton.visibility = View.VISIBLE
            } else {
                viewBinding.settingCategoryDeleteButton.visibility = View.GONE
                viewBinding.settingCategoryModifyButton.visibility = View.GONE
            }
        }
    }
    fun ConvertDPtoPX(context: Context, dp: Int): Int {
        val density: Float = context.getResources().getDisplayMetrics().density
        return Math.round(dp.toFloat() * density)
    }
}
