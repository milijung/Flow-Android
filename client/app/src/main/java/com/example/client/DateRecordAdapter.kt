package com.example.client

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.client.databinding.ItemDateRecordBinding

class DateRecordAdapter(private val datas:List<RecordData>):RecyclerView.Adapter<DateRecordAdapter.DateRecordViewHolder>() {

    //private val ITEM_VIEW_TYPE_EXPENSE=1
    //private val ITEM_VIEW_TYPE_INCOME=2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateRecordViewHolder {
        return DateRecordViewHolder(ItemDateRecordBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,)
        )
    }

    override fun onBindViewHolder(holder: DateRecordViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    class DateRecordViewHolder(private val binding:ItemDateRecordBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data:RecordData){
            //icon 설정 추가하기
            binding.tvTime.text=data.time
            binding.tvMoney.text=data.price
            //binding.icon.setImageResource()
            binding.tvMemo.text=data.memo
            binding.tvName.text=data.name
        }
    }

    /*
    class IncomeViewHolder(private val binding:ItemDateRecordBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data:RecordData){
            //icon 설정 추가하기
            binding.tvTime.text=data.time
            binding.tvMoney.text=data.money
            //binding.icon.setImageResource()
            binding.tvMemo.text=data.memo
            binding.tvName.text=data.name
        }
    }

     */
}