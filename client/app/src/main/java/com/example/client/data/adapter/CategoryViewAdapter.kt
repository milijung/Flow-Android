package com.example.client.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.client.data.Category
import com.example.client.databinding.FragmentCategoryIconBinding
import com.example.client.ui.category.settingCategoryViewBinding
import kotlin.collections.List

class CategoryViewAdapter(context: Context, var CategoryItems:ArrayList<Category>, var checkedItemPosition: Int) : BaseAdapter() {
    private lateinit var binding: FragmentCategoryIconBinding
    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int = CategoryItems.size

    override fun getItem(position: Int): Category = CategoryItems[position]

    override fun getItemId(position: Int): Long = CategoryItems[position].categoryId.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        binding = FragmentCategoryIconBinding.inflate(inflater, parent, false)
        binding.categoryIconButton.text = getItem(position).name
        binding.categoryIconButton.setCompoundDrawablesWithIntrinsicBounds(getItem(position).image,0,0,0)
        binding.categoryIconButton.isFocusable = true
        binding.categoryIconButton.isFocusableInTouchMode = true // click하면 focus됨
        // 내역 카테고리 변경 화면 -> 내역의 원래 카테고리가 기본적으로 선택되어 있도록 focus
        if(checkedItemPosition != -1) {
            if (position == checkedItemPosition)
                binding.categoryIconButton.requestFocus()
        }
        // 카테고리 설정 화면 -> 아이콘을 눌러야만 삭제하기 수정하기 버튼이 보이도록 설정
        else {
            binding.categoryIconButton.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    if (CategoryItems[position].isUserCreated)
                        settingCategoryViewBinding.settingCategoryDeleteButton.visibility =
                            View.VISIBLE
                    else
                        settingCategoryViewBinding.settingCategoryDeleteButton.visibility =
                            View.GONE
                    settingCategoryViewBinding.settingCategoryModifyButton.visibility = View.VISIBLE
                }else{
                    settingCategoryViewBinding.settingCategoryDeleteButton.visibility = View.GONE
                    settingCategoryViewBinding.settingCategoryModifyButton.visibility = View.GONE
                }
            }
            // 선택된 카테고리를 한번 더 누르면 선택 해제됨
            binding.categoryIconButton.setOnClickListener(){
                when(it.isFocused){
                    true -> it.clearFocus()
                }

            }
        }
        return binding.root;
    }
    fun updateCategoryList(categoryList : ArrayList<Category>){
        CategoryItems = categoryList
    }
}
