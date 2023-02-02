package com.example.client.data.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.client.data.model.CalendarData
import com.example.client.ui.calendar.OnCalendarItemListener
import com.example.client.R
import com.example.client.databinding.ItemCalendarBinding
import org.threeten.bp.LocalDate


class CalendarAdapter(private val dayList:ArrayList<CalendarData>,
                      private val onCalendarItemListener: OnCalendarItemListener, val selectedDate: LocalDate): RecyclerView.Adapter<CalendarAdapter.ItemViewHolder>(){

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

        val data=dayList[holder.adapterPosition]
        holder.dayText.text= data.day

        if (data.expense==""){
            holder.expenseText.text=data.income
        }
        else{
            holder.expenseText.text=data.expense
            holder.incomeText.text=data.income
        }



        //오늘 날짜에 동그라미 그리기
        var today = LocalDate.now()

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

        //인터페이스를 통해 클릭한 날짜를 넘겨준다
        holder.itemView.setOnClickListener{
            onCalendarItemListener.onItemClick(data)
        }

    }

    override fun getItemCount(): Int {
        return dayList.size
    }
}

