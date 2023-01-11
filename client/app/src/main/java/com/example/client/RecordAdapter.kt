package com.example.client


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.client.databinding.ItemDateBinding
import com.example.client.databinding.ItemIncomeBinding
import com.example.client.databinding.ItemRecordBinding

class RecordAdapter(private val datas:List<RecordData>) :RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val ITEM_VIEW_TYPE_HEADER=0
    private val ITEM_VIEW_TYPE_EXPENSE=1
    private val ITEM_VIEW_TYPE_INCOME=2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View?
        return when(viewType){
            ITEM_VIEW_TYPE_HEADER ->DateViewHolder(
                ItemDateBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
            )
            ITEM_VIEW_TYPE_EXPENSE-> RecordViewHolder(
                ItemRecordBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
            )
            ITEM_VIEW_TYPE_INCOME-> IncomeViewHolder(
                ItemIncomeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
            )
            else -> throw ClassCastException("Unknown viewType $viewType")
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int){
        when(datas[position].viewType){
            ITEM_VIEW_TYPE_HEADER-> {
                (holder as DateViewHolder).bind(datas[position])
                holder.setIsRecyclable(false)
            }
            ITEM_VIEW_TYPE_EXPENSE-> {
                (holder as RecordViewHolder).bind(datas[position])
                holder.setIsRecyclable(false)
            }
            ITEM_VIEW_TYPE_INCOME-> {
                (holder as IncomeViewHolder).bind(datas[position])
                holder.setIsRecyclable(false)
            }
            else -> throw RuntimeException("알 수 없는 뷰 타입")

        }
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun getItemViewType(position: Int): Int {
        return datas[position].viewType
    }

    class RecordViewHolder(private val binding:ItemRecordBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data:RecordData){
            //icon 설정 추가하기
            binding.tvTime.text=data.time
            binding.tvMoney.text=data.money
            //binding.icon.setImageResource()
            binding.tvMemo.text=data.memo
            binding.tvName.text=data.name

        }
    }

    class IncomeViewHolder(private val binding: ItemIncomeBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data:RecordData){
            //icon 설정 추가하기
            binding.tvTime.text=data.time
            binding.tvMoney.text=data.money
            //binding.icon.setImageResource()
            binding.tvMemo.text=data.memo
            binding.tvName.text=data.name

        }
    }


    class DateViewHolder(private val binding:ItemDateBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data:RecordData){
            //더 작성하기
            binding.tvDate.text=data.date
        }
    }




}