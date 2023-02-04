package com.example.client.data.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.data.AppDatabase
import com.example.client.databinding.ItemRecordBinding
import com.example.client.ui.board.ListDetailActivity

class RecordAdapter(context: Context, private val datas:List<com.example.client.data.List>) :RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    val context : Context = context
    val roomDb = AppDatabase.getCategoryInstance(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
            ItemRecordBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )

    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int){
        (holder as ItemViewHolder).bind(datas[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun getItemViewType(position: Int): Int {
        return datas[position].typeId
    }

    inner class ItemViewHolder(private val binding:ItemRecordBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: com.example.client.data.List){
            binding.tvTime.text=data.time
            binding.tvMoney.text=data.price
            binding.tvMemo.text=data.memo
            binding.tvName.text=data.shop
            // 날짜 visibility
            if(adapterPosition != 0){
                when(datas[adapterPosition-1].day){
                    datas[adapterPosition].day -> {
                        binding.day.visibility = View.GONE
                    }
                    else -> {
                        binding.day.text = data.day+"일"
                        binding.day.visibility = View.VISIBLE
                    }
                }
            } else{
                binding.day.text = data.day+"일"
                binding.day.visibility = View.VISIBLE
            }
            // 카테고리 icon
            if (roomDb != null) {
                binding.icon.setImageResource(roomDb.CategoryDao().selectById(data.categoryId).image)
            } else{
                when(data.typeId){
                    1 -> binding.icon.setImageResource(R.drawable.ic_category_user)
                    else -> binding.icon.setImageResource(R.drawable.ic_category_income_user)
                }
            }
            // list 상세 페이지 연결
            binding.item.setOnClickListener {
                val intent = Intent(context, ListDetailActivity::class.java)
                intent.putExtra("listId",data.listId)
                context.startActivity(intent)
            }
            // 지출, 수입 tag
            binding.tag.visibility = View.VISIBLE
            when(data.typeId){
                1 ->{
                    binding.tag.text = "지출"
                    binding.tag.setBackgroundResource(R.drawable.expense_round)
                }
                else ->{
                    binding.tag.text="수입"
                    binding.tag.setBackgroundResource(R.drawable.income_round)
                }
            }
            // 통합내역 표시 highlight
            when(data.integratedId){
                -1 -> binding.highlight.visibility = View.GONE
                data.listId -> binding.highlight.visibility = View.VISIBLE
                else ->{
                    binding.item.visibility = View.GONE
                    binding.day.visibility = View.GONE
                }
            }
        }
    }

}