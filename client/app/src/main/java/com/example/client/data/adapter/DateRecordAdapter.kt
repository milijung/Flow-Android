package com.example.client.data.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.data.AppDatabase
import com.example.client.data.model.DateRecordDataTotalAmount
import com.example.client.data.model.DateRecordDataTran
import com.example.client.databinding.ItemRecordBinding
import com.example.client.ui.board.ListDetailActivity

class DateRecordAdapter(private val context : Context,private val datas:List<DateRecordDataTran>):RecyclerView.Adapter<DateRecordAdapter.ViewHolder>() {
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
        fun bind(data:DateRecordDataTran){
            val roomDb = AppDatabase.getCategoryInstance(context)
            val roomDbList=AppDatabase.getListInstance(context)
            binding.tvTime.text=data.time.toString()
            binding.tvMoney.text=data.amount.toString()
            binding.tvMemo.text=data.memo
            binding.tvName.text=data.info

            if (roomDb != null) {
                binding.icon.setImageResource(roomDb.CategoryDao().selectById(data.categoryId).image)
            } else{
                when(data.isExp){
                    1 -> binding.icon.setImageResource(R.drawable.ic_category_user)
                    else -> binding.icon.setImageResource(R.drawable.ic_category_income_user)
                }
            }

            if (roomDbList != null) {
                when(data.integratedId){
                    -1 -> binding.highlight.visibility = View.GONE
                    data.categoryId -> binding.highlight.visibility = View.VISIBLE
                    else ->{
                        binding.item.visibility = View.GONE
                        binding.day.visibility = View.GONE
                    }
                }
            }
            // list 상세 페이지 연결
            binding.item.setOnClickListener {
                val intent = Intent(context, ListDetailActivity::class.java)
                intent.putExtra("listId",data.detailId)
                context.startActivity(intent)
            }
        }
    }
}