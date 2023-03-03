package com.example.client.data.adapter
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.data.model.AppDatabase
import com.example.client.data.Detail
import com.example.client.databinding.ItemRecordBinding
import com.example.client.ui.board.ListDetailActivity
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi // mode 0: 통합정보와 날짜정보를 묶어 bind, mode 1: 거래 정보만 bind
class RecordAdapter(val context: Context, var datas:List<Detail>, val mode:Int = 0) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val roomDb = AppDatabase.getInstance(context)
    private val recordLongClickListener = when(mode){0-> context as OnRecordLongClickListener else -> null}
    private lateinit var records : List<Detail>
    var selectedRecords = ArrayList<Int>()

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
        records = when(mode){
            0 -> datas.filter{ detail -> detail.integratedId == -1 || detail.integratedId == detail.detailId }
            else -> datas
        }
        (holder as ItemViewHolder).bind(records[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int = when(mode){
            0 -> datas.filter{detail -> detail.integratedId == -1 || detail.integratedId == detail.detailId }.size
            else -> datas.size
        }

    override fun getItemViewType(position: Int): Int = when(mode){
        0 -> datas.filter{detail -> detail.integratedId == -1 || detail.integratedId == detail.detailId }[position].typeId
        else -> datas[position].typeId
    }

    fun updateRecordList(newRecords: List<Detail>, page:Int){
        when(page){
            1 -> datas = newRecords
            else -> datas += newRecords
        }
        selectedRecords.clear()
        recordLongClickListener?.onRecordLongClickFinish()
    }

    inner class ItemViewHolder(private val binding:ItemRecordBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("ResourceAsColor")
        fun bind(data: Detail) {
            binding.tvTime.text = data.time
            binding.tvMemo.text = data.memo
            binding.tvName.text = data.shop
            binding.icon.setImageResource(roomDb!!.CategoryDao().selectById(data.categoryId).image)

            var price = 0
            when(mode){
                0 ->{
                    when (data.integratedId) {
                        data.detailId -> {
                            for (d in datas.filter{detail -> detail.integratedId == data.detailId }) {
                                when(d.typeId){
                                    1 -> price -= d.price
                                    else -> price += d.price
                                }
                            }
                            binding.highlight.visibility = View.VISIBLE // 통합내역 표시 highlight
                        }
                        else -> {
                            price = when(data.typeId){ 1-> -1*data.price else -> data.price}
                            binding.highlight.visibility = View.GONE
                        }
                    }
                    // 지출, 수입 tag 표시
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
                    binding.tag.visibility = View.VISIBLE

                    // 내역 선택 여부 표시
                    when(data.detailId){
                        in selectedRecords -> binding.item.isSelected = true
                        else -> binding.item.isSelected = false
                    }

                    // 날짜 표시
                    val prev = when(adapterPosition){0-> data else-> records[adapterPosition - 1]}

                    if (adapterPosition >0 && prev.year == data.year && prev.month == data.month) {
                        // 연, 월 표시 안하기
                        binding.yearAndMonth.visibility = View.GONE
                        // 일 표시
                        if (prev.day == data.day)
                            binding.day.visibility = View.GONE
                        else {
                            binding.day.text = "${data.day}일"
                            binding.day.visibility = View.VISIBLE
                        }
                    }else {
                        // 연, 월, 일 표시
                        binding.yearAndMonthText.text = "${data.year}년 ${data.month}월"
                        binding.yearAndMonth.visibility = View.VISIBLE
                        binding.day.text = "${data.day}일"
                        binding.day.visibility = View.VISIBLE
                    }
                    // 길게 클릭
                    binding.item.setOnLongClickListener {
                        if (selectedRecords.size == 0 && binding.subItemList.childCount == 0) {
                            binding.item.isSelected = !binding.item.isSelected
                            selectedRecords.add(data.detailId)
                            recordLongClickListener?.onRecordLongClickStart()
                        }
                        return@setOnLongClickListener true
                    }
                }
                1 -> {
                    price = data.price
                    binding.highlight.visibility = View.GONE
                }
            }

            binding.tvMoney.text = kotlin.math.abs(price).toString()

            binding.item.setOnClickListener {
                when (selectedRecords.size) {
                    // longClick중이지 않은 경우
                    0 -> {
                        when(binding.highlight.isVisible){
                            false -> {
                                // list 상세 페이지 연결
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
                            }
                            // 통합 대표 내역인 경우
                            else -> {
                                when(binding.subItemList.childCount){
                                    // toggle down
                                    0->{
                                        val subList = datas.filter{detail -> detail.integratedId == data.detailId }
                                        val decoration = ItemVerticalDecoration(1)

                                        binding.subItemList.adapter = RecordAdapter(context, subList, 1)
                                        binding.subItemList.addItemDecoration(decoration)
                                        binding.subItemList.layoutManager = LinearLayoutManager(context)
                                        binding.item.setBackgroundResource(R.drawable.item_record_integrated)
                                        binding.subItemBackground.visibility = View.VISIBLE
                                    }
                                    // toggle up
                                    else ->{
                                        binding.item.setBackgroundResource(R.drawable.item_record_style)
                                        binding.subItemList.adapter = null
                                        binding.subItemBackground.visibility = View.GONE
                                    }
                                }

                            }
                        }
                    }
                    // longClick중인 경우
                    else -> {
                        when (binding.item.isSelected) {
                            true -> {
                                binding.item.isSelected = false
                                selectedRecords.remove(data.detailId)
                                binding.subItemList.adapter = null
                                binding.subItemBackground.visibility = View.GONE
                                if (selectedRecords.size == 0)
                                    recordLongClickListener?.onRecordLongClickFinish()
                            }
                            else -> {
                                binding.item.isSelected = true
                                selectedRecords.add(data.detailId)
                            }
                        }
                    }
                }
            }
        }
    }
    interface OnRecordLongClickListener{
        fun onRecordLongClickStart()
        fun onRecordLongClickFinish()
    }
    fun deleteRecordList(deleteItemList : ArrayList<Int>) {
        datas = datas.filter { detail -> detail.detailId !in deleteItemList }
        selectedRecords.clear()
        recordLongClickListener?.onRecordLongClickFinish()
    }
}