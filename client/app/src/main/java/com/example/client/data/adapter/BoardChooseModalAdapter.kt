package com.example.client.data.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.client.data.Detail
import com.example.client.databinding.ItemRecordBinding


class BoardChooseModalAdapter(private val recordList:List<Detail>): RecyclerView.Adapter<BoardChooseModalAdapter.ItemViewHolder>() {
    private var selectedItemPosition = 0
    private var selectedLayout: View? = null

    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(v: View, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.mListener=listener
    }

    class ItemViewHolder(private val binding: ItemRecordBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: Detail){
            binding.tvTime.text=data.time
            binding.tvMoney.text=data.price.toString()
            binding.tvMemo.text=data.memo
            binding.tvName.text=data.shop
            binding.highlight.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemRecordBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(recordList[position])

        // Item Initialize
        holder.itemView.isSelected = position == selectedItemPosition

        //창이 처음 켜지면 첫번째 아이템이 클릭되도록 설정
        if(position==0){selectedLayout=holder.itemView}

        holder.itemView.setOnClickListener{
            //클릭한 아이템 다른 클래스로 넘겨주기
            mListener.onItemClick(it,position)

            //
            val currentPosition = holder.adapterPosition

            //선택되었던 아이템 클릭 해제
            selectedLayout?.isSelected=false
            selectedItemPosition = currentPosition
            //클릭한 아이템 클릭 설정
            selectedLayout = holder.itemView
            selectedLayout?.isSelected=true

        }

    }

    override fun getItemCount(): Int {
        return recordList.size
    }
}