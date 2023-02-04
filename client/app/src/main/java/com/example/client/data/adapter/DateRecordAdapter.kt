package com.example.client.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.data.AppDatabase
import com.example.client.databinding.ItemDateRecordBinding

class DateRecordAdapter(context : Context,private val datas:List<com.example.client.data.List>):RecyclerView.Adapter<DateRecordAdapter.ViewHolder>() {
    private val parent : Context = context
    private lateinit var binding : ItemDateRecordBinding
    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemDateRecordBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size

    inner class ViewHolder(private val binding:ItemDateRecordBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data:com.example.client.data.List){
            val roomDb = AppDatabase.getCategoryInstance(parent)
            binding.tvTime.text=data.time
            binding.tvMoney.text=data.price
            if (roomDb != null) {
                binding.icon.setImageResource(roomDb.CategoryDao().selectById(data.categoryId).image)
            } else{
                when(data.typeId){
                    1 -> binding.icon.setImageResource(R.drawable.ic_category_user)
                    else -> binding.icon.setImageResource(R.drawable.ic_category_income_user)
                }
            }
            binding.tvMemo.text=data.memo
            binding.tvName.text=data.shop
        }
    }
}