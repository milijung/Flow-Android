package com.example.client

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.client.databinding.ActivityChangeCategoryBinding
import com.example.client.databinding.FragmentCategoryIconBinding

class CategoryViewAdapter(context: Context, var CategoryItems:ArrayList<Category>) : BaseAdapter() {
    lateinit var binding: FragmentCategoryIconBinding
    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int = CategoryItems.size

    override fun getItem(position: Int): Any = CategoryItems[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        binding = FragmentCategoryIconBinding.inflate(inflater, parent, false)
        binding.categoryIconButton.text = CategoryItems[position].name
        binding.categoryIconButton.setCompoundDrawablesWithIntrinsicBounds(CategoryItems[position].image,0,0,0)

        return binding.root;
    }
}