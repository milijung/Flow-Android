package com.example.client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.client.databinding.ActivityChangeCategoryBinding
import kotlin.math.log

class ChangeCategoryActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityChangeCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityChangeCategoryBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        var categoryItems: ArrayList<Category> = arrayListOf()
        categoryItems.add(Category("하루세끼", R.drawable.ic_category_food, false))
        categoryItems.add(Category("주거·통신", R.drawable.ic_category_living, false))
        categoryItems.add(Category("교통관련", R.drawable.ic_category_traffic, false))
        categoryItems.add(Category("생필품", R.drawable.ic_category_market, false))
        categoryItems.add(Category("나를 위한", R.drawable.ic_category_formeexpense, false))
        categoryItems.add(Category("선물준비", R.drawable.ic_category_present, false))
        categoryItems.add(Category("자기계발", R.drawable.ic_category_selfimprovement, false))
        categoryItems.add(Category("카페·간식", R.drawable.ic_category_cafe, false))
        categoryItems.add(Category("저축", R.drawable.ic_category_saving, false))
        categoryItems.add(Category("술·외식", R.drawable.ic_category_alchol, false))
        categoryItems.add(Category("의료·건강", R.drawable.ic_category_medical, false))
        categoryItems.add(Category("오락·취미", R.drawable.ic_category_entertainment, false))
        categoryItems.add(Category("여행", R.drawable.ic_category_travel, false))
        categoryItems.add(Category("자산이동", R.drawable.ic_category_assetmovement, false))
        categoryItems.add(Category("기타지출", R.drawable.ic_category_others, false))
        categoryItems.add(Category("애견용품", R.drawable.ic_category_user, true))
        categoryItems.add(Category("회비", R.drawable.ic_category_user, true))
        categoryItems.add(Category("보험료", R.drawable.ic_category_user, true))

        viewBinding.changeCategoryButton.visibility = View.VISIBLE // View.GONE
        viewBinding.changeCategoryList.adapter = CategoryViewAdapter(applicationContext, categoryItems)
        viewBinding.changeCategoryButton.text = getText(R.string.finish_button)


    }
}
