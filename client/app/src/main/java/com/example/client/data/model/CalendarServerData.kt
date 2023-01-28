package com.example.client

import com.google.gson.annotations.SerializedName


data class CalendarServerData(
    @SerializedName("result")
    val result:ArrayList<CalendarServerDataResult>
)

data class CalendarServerDataResult(
    @SerializedName("date")
    val date:Int,
    @SerializedName("is_exp")
    val isExp:Int,
    @SerializedName("amount")
    val amount:Int,
)
