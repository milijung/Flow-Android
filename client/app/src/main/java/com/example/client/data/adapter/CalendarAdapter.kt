package com.example.client.data.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.client.R
import com.example.client.data.model.CalendarData
import com.example.client.databinding.ItemCalendarBinding
import org.threeten.bp.LocalDate


class CalendarAdapter(private val dayList:ArrayList<CalendarData>,
                      private val selectedDate: LocalDate): RecyclerView.Adapter<CalendarAdapter.ItemViewHolder>(){

    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(v: View, position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.mListener=listener
    }

    class ItemViewHolder(private val binding:ItemCalendarBinding):RecyclerView.ViewHolder(binding.root){

        val dayText:TextView=binding.calendarDay
        val expenseText:TextView=binding.calendarExpense
        val incomeText:TextView=binding.calendarIncome


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemCalendarBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        //날짜 보여주기
        val data=dayList[holder.adapterPosition]
        holder.dayText.text= data.day

        //지출 없으면 맨 위에 수입 보여주기
        if (data.expense==""){
            holder.expenseText.text=data.income
        }
        else{ //지출 있으면 지출, 수입 순으로 보여주기
            holder.expenseText.text=data.expense
            holder.incomeText.text=data.income
        }



        //오늘 날짜에 동그라미 그리기
        val today = LocalDate.now() //오늘 날짜

        if(data.day==today.dayOfMonth.toString() && today.month==selectedDate.month){
            holder.dayText.setBackgroundResource(R.drawable.calendar_circle)
        }

        //텍스트 색상 지정(일-빨강)
        if( position==0||position%7==0){
            holder.dayText.setTextColor(Color.parseColor("#F10000"))
        }

        //총 지출, 수입 색상 지정
        if(data.expense!="" && data.income!=""){
            holder.expenseText.setTextColor(Color.parseColor("#E73A40"))
            holder.incomeText.setTextColor(Color.parseColor("#28A9DC"))
        }



        //날짜 클릭 리스너
        holder.itemView.setOnClickListener{
            mListener.onItemClick(it,position)
        }
    }

    override fun getItemCount(): Int {
        return dayList.size
    }
}

