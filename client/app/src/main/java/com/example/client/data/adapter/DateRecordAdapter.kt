package com.example.client.data.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.data.AppDatabase
import com.example.client.databinding.ItemRecordBinding
import com.example.client.ui.board.ListDetailActivity

class DateRecordAdapter(context : Context,private val datas:List<com.example.client.data.List>):RecyclerView.Adapter<DateRecordAdapter.ViewHolder>() {
    val context : Context = context
    private lateinit var binding : ItemRecordBinding
    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemRecordBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size

    inner class ViewHolder(private val binding:ItemRecordBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data:com.example.client.data.List){
            val roomDb = AppDatabase.getCategoryInstance(context)
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
            when(data.integratedId){
                -1 -> binding.highlight.visibility = View.GONE
                data.listId -> binding.highlight.visibility = View.VISIBLE
                else ->{
                    binding.item.visibility = View.GONE
                    binding.day.visibility = View.GONE
                }
            }
            // list 상세 페이지 연결
            binding.item.setOnClickListener {
                val intent = Intent(context, ListDetailActivity::class.java)
                intent.putExtra("listId",data.listId)
                context.startActivity(intent)
            }
        }
    }
}