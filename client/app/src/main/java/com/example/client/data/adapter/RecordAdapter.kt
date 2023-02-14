package com.example.client.data.adapter
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.data.AppDatabase
import com.example.client.data.Detail
import com.example.client.databinding.ItemRecordBinding
import com.example.client.ui.board.ListDetailActivity
import kotlinx.coroutines.InternalCoroutinesApi
import java.lang.Math.abs

@InternalCoroutinesApi
class RecordAdapter(val context: Context, var datas:List<Detail>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    val roomDb = AppDatabase.getInstance(context)
    var selectedItem = ArrayList<Int>()
    private var longClickListener : OnListLongClickListener? = context as OnListLongClickListener
    private var adapterData = ArrayList<Detail>()

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
        adapterData = ArrayList()
        for(d in datas){
            if(d.detailId == d.integratedId || d.integratedId == -1)
                adapterData.add(d)
        }
        (holder as ItemViewHolder).bind(adapterData[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        adapterData = ArrayList()
        for(d in datas){
            if(d.detailId == d.integratedId || d.integratedId == -1)
                adapterData.add(d)
        }
        return adapterData.size
    }

    override fun getItemViewType(position: Int): Int {
        adapterData = ArrayList()
        for(d in datas){
            if(d.detailId == d.integratedId || d.integratedId == -1)
                adapterData.add(d)
        }
        return adapterData[position].typeId
    }
    fun updateRecordList(detail: List<Detail>, page:Int){
        datas = if(page==1){
            detail
        } else{
            val temp= ArrayList<Detail>()
            for (d in datas){
                temp.add(d)
            }
            for(d in detail){
                temp.add(d)
            }
            temp
        }
    }

    inner class ItemViewHolder(private val binding:ItemRecordBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: Detail){
            binding.tvTime.text=data.time
            binding.tvMemo.text=data.memo
            binding.tvName.text=data.shop
            if(data.detailId in selectedItem)
                binding.item.isSelected = true
            val year = this@RecordAdapter.datas[adapterPosition].year
            val month = this@RecordAdapter.datas[adapterPosition].month
            val day = this@RecordAdapter.datas[adapterPosition].day

            var price = 0
            if(data.integratedId == data.detailId){
                for(d in datas){
                    if(d.integratedId == data.detailId)
                        if(d.typeId==1)
                            price -= d.price
                        else
                            price += d.price
                }
                // 지출, 수입 tag
                binding.tag.visibility = View.VISIBLE
                when(price > 0){
                    false ->{
                        binding.tag.text = "지출"
                        binding.tag.setBackgroundResource(R.drawable.expense_round)
                    }
                    else ->{
                        binding.tag.text="수입"
                        binding.tag.setBackgroundResource(R.drawable.income_round)
                    }
                }
            }

            else{
                price = data.price
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
            }

            binding.tvMoney.text= abs(price).toString()
            // 날짜 visibility
            if(adapterPosition != 0){
                val prev = this@RecordAdapter.datas[adapterPosition-1]
                if(prev.year == year && prev.month == month){
                    binding.yearAndMonth.visibility = View.GONE
                    if(prev.day == day)
                        binding.day.visibility = View.GONE
                    else
                        binding.day.visibility = View.GONE
                }else{
                    binding.yearAndMonthText.text = "${year}년 ${month}월"
                    binding.yearAndMonth.visibility = View.VISIBLE
                    binding.day.text = "${data.day}일"
                    binding.day.visibility = View.VISIBLE
                }
            } else{
                binding.yearAndMonthText.text = "${year}년 ${month}월"
                binding.yearAndMonth.visibility = View.VISIBLE
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
                        intent.putExtra("userId",data.userId)
                        intent.putExtra("detailId",data.detailId)
                        intent.putExtra("typeId",data.typeId)
                        intent.putExtra("categoryId",data.categoryId)
                        intent.putExtra("price",data.price)
                        intent.putExtra("memo",data.memo)
                        intent.putExtra("shop",data.shop)
                        intent.putExtra("year",data.year)
                        intent.putExtra("month",data.month)
                        intent.putExtra("day",data.day)
                        intent.putExtra("time",data.time)
                        intent.putExtra("isBudgetIncluded",data.isBudgetIncluded)
                        intent.putExtra("isKeywordIncluded",data.isKeywordIncluded)
                        context.startActivity(intent)
                    }
                    else ->{
                        when(binding.item.isSelected){
                            true ->{
                                binding.item.isSelected = false
                                selectedItem.remove(data.detailId)
                                if(selectedItem.size ==0)
                                    longClickListener?.onListLongClickFinish()
                            }
                            else ->{
                                binding.item.isSelected = true
                                selectedItem.add(data.detailId)
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
                    selectedItem.add(data.detailId)
                    longClickListener?.onListLongClickStart()
                }
                return@setOnLongClickListener true
            }
            // 통합내역 표시 highlight
            when(data.integratedId){
                data.detailId -> binding.highlight.visibility = View.VISIBLE
                -1 -> binding.highlight.visibility = View.GONE
            }
        }
    }
    interface OnListLongClickListener{
        fun onListLongClickStart()
        fun onListLongClickFinish()
    }
    fun deleteRecordList(deleteItemList : ArrayList<Int>) {
        var temp = ArrayList<Detail>()
        for(d in datas){
            if(d.detailId !in deleteItemList)
                temp.add(d)
        }
        datas = temp
        selectedItem.clear()
        longClickListener?.onListLongClickFinish()
    }
}