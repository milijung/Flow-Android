package com.example.client

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.core.view.get
import androidx.fragment.app.FragmentContainerView
import com.example.client.databinding.ActivityChangeCategoryBinding
import com.example.client.databinding.FragmentCategoryIconBinding
import kotlinx.android.synthetic.main.fragment_category_icon.view.*
import kotlin.collections.List

class CategoryViewAdapter(context: Context, var CategoryItems:List<Category>,var checkedItemPosition: Int) : BaseAdapter() {
    private lateinit var binding: FragmentCategoryIconBinding
    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int = CategoryItems.size

    override fun getItem(position: Int): Category= CategoryItems[position]

    override fun getItemId(position: Int): Long = CategoryItems[position].categoryId.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        binding = FragmentCategoryIconBinding.inflate(inflater, parent, false)
        binding.categoryIconButton.text = getItem(position).name
        binding.categoryIconButton.setCompoundDrawablesWithIntrinsicBounds(getItem(position).image,0,0,0)
        binding.categoryIconButton.isFocusable = true
        binding.categoryIconButton.isFocusableInTouchMode = true
        if (position == checkedItemPosition)
            binding.categoryIconButton.requestFocus()

        binding.categoryIconButton.setOnClickListener {
            it.requestFocus()
        }
        return binding.root;
    }
}