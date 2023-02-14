package com.example.client.data.adapter
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat.getColor
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.data.AppDatabase
import com.example.client.data.Detail
import com.example.client.databinding.ItemRecordBinding
import com.example.client.ui.board.ListDetailActivity
import kotlinx.coroutines.InternalCoroutinesApi
import java.lang.Math.abs

@InternalCoroutinesApi
class RecordAdapter(val context: Context, var datas:List<Detail>,val option:Int = 0,val integratedId:Int = -1) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    val roomDb = AppDatabase.getInstance(context)
    var selectedItem = ArrayList<Int>()
    private var longClickListener : OnListLongClickListener? =null
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
        if(integratedId==-1){
            for (d in datas) {
                if (d.detailId == d.integratedId || d.integratedId == -1)
                    adapterData.add(d)
            }
        }else{
            if(option==0)
                for (d in datas) {
                    if (d.integratedId == integratedId)
                        adapterData.add(d)
                }
        }
        if(option==0){
            (holder as ItemViewHolder).bind(adapterData[position])
            holder.setIsRecyclable(false)
            longClickListener= context as OnListLongClickListener
        }else{
            (holder as ItemViewHolder).bind(datas[position])
            holder.setIsRecyclable(false)
        }

    }

    override fun getItemCount(): Int {
        adapterData = ArrayList()
        if(integratedId == -1){
            for (d in datas) {
                if (d.detailId == d.integratedId || d.integratedId == -1)
                    adapterData.add(d)
            }
        }else{
            for (d in datas) {
                if (d.integratedId == integratedId)
                    adapterData.add(d)
            }
        }
        return if(option==0)
            adapterData.size
        else
            datas.size
    }

    override fun getItemViewType(position: Int): Int {
        adapterData = ArrayList()
        if(integratedId == -1){
            for (d in datas) {
                if (d.detailId == d.integratedId || d.integratedId == -1)
                    adapterData.add(d)
            }
        }else{
            for (d in datas) {
                if (d.integratedId == integratedId)
                    adapterData.add(d)
            }
        }
        return if(option==0)
            adapterData[position].typeId
        else
            datas[position].typeId
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
        selectedItem.clear()
        longClickListener?.onListLongClickFinish()
    }

    inner class ItemViewHolder(private val binding:ItemRecordBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("ResourceAsColor")
        fun bind(data: Detail) {
            binding.tvTime.text = data.time
            binding.tvMemo.text = data.memo
            binding.tvName.text = data.shop
            // 통합내역 표시 highlight
            if (option == 0) {
                when (data.integratedId) {
                    data.detailId -> {
                        if (integratedId == -1)
                            binding.highlight.visibility = View.VISIBLE
                        else
                            binding.highlight.visibility = View.GONE
                    }
                    else -> binding.highlight.visibility = View.GONE
                }
            } else {
                binding.highlight.visibility = View.GONE
            }
            if (data.detailId in selectedItem)
                binding.item.isSelected = true
  
            var price = 0
            if (data.integratedId == data.detailId && integratedId != data.detailId) {
                for (d in datas) {
                    if (d.integratedId == data.detailId)
                        if (d.typeId == 1)
                            price -= d.price
                        else
                            price += d.price
                }
                // 지출, 수입 tag
                binding.tag.visibility = View.VISIBLE
                when (price > 0) {
                    false -> {
                        binding.tag.text = "지출"
                        binding.tag.setBackgroundResource(R.drawable.expense_round)
                    }
                    else -> {
                        binding.tag.text = "수입"
                        binding.tag.setBackgroundResource(R.drawable.income_round)
                    }
                }
            } else {
                price = data.price
                // 지출, 수입 tag
                binding.tag.visibility = View.VISIBLE
                when (data.typeId) {
                    1 -> {
                        binding.tag.text = "지출"
                        binding.tag.setBackgroundResource(R.drawable.expense_round)
                    }
                    else -> {
                        binding.tag.text = "수입"
                        binding.tag.setBackgroundResource(R.drawable.income_round)
                    }
                }
            }
            binding.tvMoney.text = abs(price).toString()
            // 날짜 visibility
            if (integratedId == -1 && option == 0) {
                if (adapterPosition != 0) {
                    val prev = adapterData[adapterPosition - 1]
                    if (prev.year == data.year && prev.month == data.month) {
                        binding.yearAndMonth.visibility = View.GONE
                        if (prev.day == data.day)
                            binding.day.visibility = View.GONE
                        else {
                            binding.day.text = "${data.day}일"
                            binding.day.visibility = View.VISIBLE
                        }
                    } else {
                        binding.yearAndMonthText.text = "${data.year}년 ${data.month}월"
                        binding.yearAndMonth.visibility = View.VISIBLE
                        binding.day.text = "${data.day}일"
                        binding.day.visibility = View.VISIBLE
                    }
                } else {
                    binding.yearAndMonthText.text = "${data.year}년 ${data.month}월"
                    binding.yearAndMonth.visibility = View.VISIBLE
                    binding.day.text = data.day + "일"
                    binding.day.visibility = View.VISIBLE
                }
            } else {

            }
            // 카테고리 icon
            if (roomDb != null) {
                binding.icon.setImageResource(
                    roomDb.CategoryDao().selectById(data.categoryId).image
                )
            } else {
                when (data.typeId) {
                    1 -> binding.icon.setImageResource(R.drawable.ic_category_user)
                    else -> binding.icon.setImageResource(R.drawable.ic_category_income_user)
                }
            }
            // list 상세 페이지 연결
            binding.item.setOnClickListener {
                when (selectedItem.size) {
                    0 -> {
                        if (!binding.highlight.isVisible || option != 0) {
                            val intent = Intent(context, ListDetailActivity::class.java)
                            intent.putExtra("userId", data.userId)
                            intent.putExtra("detailId", data.detailId)
                            intent.putExtra("typeId", data.typeId)
                            intent.putExtra("categoryId", data.categoryId)
                            intent.putExtra("price", data.price)
                            intent.putExtra("memo", data.memo)
                            intent.putExtra("shop", data.shop)
                            intent.putExtra("year", data.year)
                            intent.putExtra("month", data.month)
                            intent.putExtra("day", data.day)
                            intent.putExtra("time", data.time)
                            intent.putExtra("isBudgetIncluded", data.isBudgetIncluded)
                            intent.putExtra("isKeywordIncluded", data.isKeywordIncluded)
                            context.startActivity(intent)
                        } else {
                            if (binding.subItemList.childCount == 0) {
                                binding.item.setBackgroundResource(R.drawable.item_record_integrated)
                                var subList = ArrayList<Detail>()
                                for (d in datas) {
                                    if (d.integratedId == data.detailId)
                                        subList.add(d)
                                }
                                binding.subItemList.adapter =
                                    RecordAdapter(context, subList, 0, data.detailId)
                                val decoration = ItemDecoration(1)
                                binding.subItemList.addItemDecoration(decoration)
                                binding.subItemList.layoutManager = LinearLayoutManager(context)
                                binding.subItemBackground.visibility = View.VISIBLE
                            } else {
                                binding.item.setBackgroundResource(R.drawable.item_record_style)
                                binding.subItemList.adapter = null
                                binding.subItemBackground.visibility = View.GONE
                            }
                        }
                    }
                    else -> {
                        when (binding.item.isSelected) {
                            true -> {
                                binding.item.isSelected = false
                                selectedItem.remove(data.detailId)
                                binding.subItemList.adapter = null
                                binding.subItemBackground.visibility = View.GONE
                                if (selectedItem.size == 0)
                                    longClickListener?.onListLongClickFinish()
                            }
                            else -> {
                                binding.item.isSelected = true
                                selectedItem.add(data.detailId)
                            }
                        }
                        println(selectedItem)
                    }
                }
            }

            if(option ==0){
                // 길게 클릭
                binding.item.setOnLongClickListener {
                    if(integratedId == -1) {
                        if (selectedItem.size == 0) {
                            binding.item.isSelected = !binding.item.isSelected
                            selectedItem.add(data.detailId)
                            longClickListener?.onListLongClickStart()
                        }
                    }
                    return@setOnLongClickListener true
                }
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