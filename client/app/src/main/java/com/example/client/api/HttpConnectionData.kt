package com.example.client.api

import androidx.room.ColumnInfo
import com.example.client.data.Category
import com.example.client.data.Detail
import com.example.client.data.User
import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("isSuccess")
    val isSuccess:Boolean,
    @SerializedName("code")
    val code:Int,
    @SerializedName("message")
    val message:String,
    @SerializedName("result")
    val result:String,
)
data class DetailResponseByList(
    @SerializedName("isSuccess")
    val isSuccess:Boolean,
    @SerializedName("code")
    val code:Int,
    @SerializedName("message")
    val message:String,
    @SerializedName("result")
    val result: List<Detail>,
)
data class UpdateDetailData(
    @ColumnInfo(name="categoryId") val categoryId: Int,
    @ColumnInfo(name= "memo") val memo: String,
    @ColumnInfo(name="isBudgetIncluded") val isBudgetIncluded : Boolean,
    @ColumnInfo(name="isKeywordIncluded") val isKeywordIncluded : Boolean = false,
)

data class UserResponseByList(
    @SerializedName("isSuccess")
    val isSuccess:Boolean,
    @SerializedName("code")
    val code:Int,
    @SerializedName("message")
    val message:String,
    @SerializedName("result")
    val result: User,
)

data class CalendarResponseByList(
    @SerializedName("isSuccess")
    val isSuccess:Boolean,
    @SerializedName("code")
    val code:Int,
    @SerializedName("message")
    val message:String,
    @SerializedName("result")
    val result:ArrayList<CalendarResponse>
)

data class CalendarResponse(
    @SerializedName("date")
    val date:Int,
    @SerializedName("isExp")
    val isExp:Int,
    @SerializedName("amount")
    val amount:Int,
)
data class CalendarData(
    val day:String,
    val expense:String="",
    val income:String=""
)
data class RecordsOfDate(
    @SerializedName("result")
    val result:RecordsInfoOfDate
)

data class RecordsInfoOfDate(
    @SerializedName("totalAmount")
    val totalAmount:ArrayList<TotalAmount>,
    @SerializedName("transaction")
    val transaction:ArrayList<Detail>,
)

data class TotalAmount(
    @SerializedName("total")
    val total:Int,
    @SerializedName("isExp")
    val isExp:Int,
)

data class CategoryResponseByList(
    @SerializedName("isSuccess")
    val isSuccess:Boolean,
    @SerializedName("code")
    val code:Int,
    @SerializedName("message")
    val typeId:String,
    @SerializedName("result")
    val result:ArrayList<Category>
)


