package com.example.client.api

import com.example.client.data.Category
import com.example.client.data.Detail
import com.example.client.data.User
import com.google.gson.annotations.SerializedName

data class ResponseData(
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
    @SerializedName("categoryId")
    val categoryId: Int,
    @SerializedName("memo")
    val memo: String,
    @SerializedName("isBudgetIncluded")
    val isBudgetIncluded : Boolean,
    @SerializedName("isKeywordIncluded")
    val isKeywordIncluded : Boolean = false,
)

data class JoinDetailData(
    @SerializedName("integratedId")
    val integratedId: Int,
    @SerializedName("detailId")
    val detailId: List<Int>,
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
    @SerializedName("isSuccess")
    val isSuccess:Boolean,
    @SerializedName("code")
    val code:Int,
    @SerializedName("message")
    val message:String,
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
    val result:ArrayList<CategoryResponse>
)
data class CategoryResponse(
    @SerializedName("categoryId")
    val categoryId: Int,
    @SerializedName("name")
    val name:String,
    @SerializedName("typeId")
    val typeId: Int,
    @SerializedName("isUserCreated")
    val isUserCreated: Boolean
)
data class CategoryRequestData(
    @SerializedName("name")
    val name:String,
    @SerializedName("typeId")
    val typeId: Int,
)

data class BudgetRequest(
    @SerializedName("budget")
    val budget:Int,
    @SerializedName("startDate")
    val startDate:Int,
)

