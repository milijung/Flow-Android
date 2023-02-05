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
import kotlinx.android.synthetic.main.activity_home_list.view.*

class RecordAdapter(context: Context, private var datas:List<com.example.client.data.List>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    val context : Context = context
    val roomDb = AppDatabase.getCategoryInstance(context)
    var selectedItem : ArrayList<Int> = arrayListOf()
    private var longClickListener : OnListLongClickListener? = context as OnListLongClickListener

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
    fun updateRecordList(list: List<com.example.client.data.List>){
        datas = list
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
                when(selectedItem.size){
                    0 ->{
                        val intent = Intent(context, ListDetailActivity::class.java)
                        intent.putExtra("listId",data.listId)
                        context.startActivity(intent)
                    }
                    else ->{
                        when(binding.item.isSelected){
                            true ->{
                                binding.item.isSelected = false
                                selectedItem.remove(data.listId)
                                if(selectedItem.size==0)
                                    longClickListener?.onListLongClickFinish()
                            }
                            else ->{
                                binding.item.isSelected = true
                                selectedItem.add(data.listId)
                            }
                        }
                        println(selectedItem)
                    }
                }
            }
            // 길게 클릭
            binding.item.setOnLongClickListener {
                if(selectedItem.size==0){
                    binding.item.isSelected = !binding.item.isSelected
                    selectedItem.add(data.listId)
                    println(selectedItem)
                    longClickListener?.onListLongClickStart()
                }
                return@setOnLongClickListener true
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
    interface OnListLongClickListener{
        fun onListLongClickStart()
        fun onListLongClickFinish()
    }
    fun deleteButtonClickListener() {
        for(i: Int in selectedItem){
            roomDb?.ListDao()?.deleteById(i)
        }
        longClickListener?.onListLongClickFinish()
    }
}