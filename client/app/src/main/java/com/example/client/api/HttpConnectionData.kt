package com.example.client.api

import androidx.room.ColumnInfo
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
data class InsertDetailRequestData(
    @SerializedName("userId") val userId:Int,
    @SerializedName("categoryId") val categoryId: Int,
    @SerializedName( "year") val year: String,
    @SerializedName( "month") val month: String,
    @SerializedName("day") val day: String,
    @SerializedName("time") val time: String,
    @SerializedName( "price") val price : Int,
    @SerializedName("shop") val shop: String,
    @SerializedName("typeId") val typeId:Int,  // 지출: 1, 수입: 2
    @SerializedName("isBudgetIncluded") val isBudgetIncluded : Boolean,
    @SerializedName("isKeywordIncluded") val isKeywordIncluded : Boolean = false,
    @SerializedName( "memo") val memo : String,
    @SerializedName("integratedId") val integratedId : Int = -1,
)
data class InsertDetailResponseData(
    @SerializedName("isSuccess")
    val isSuccess:Boolean,
    @SerializedName("code")
    val code:Int,
    @SerializedName("message")
    val message:String,
    @SerializedName("result")
    val result:DetailId
)
data class DetailId(
    @SerializedName("detailId")
    val detailId:Int
)
data class DeleteDetailRequestData(
    @SerializedName("detailId")
    val detailList:List<Int>
)
data class RecordsInfoOfDate(
    @SerializedName("totalAmount")
    val totalAmount:ArrayList<TotalAmount>,
    @SerializedName("detail")
    val detail:List<Detail>,
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
data class AnalysisResponseByList(
    @SerializedName("isSuccess")
    val isSuccess:Boolean,
    @SerializedName("code")
    val code:Int,
    @SerializedName("message")
    val message:String,
    @SerializedName("result")
    val result: AnalysisResponseData,
)
data class AnalysisResponseData(
    @SerializedName("budget")
    val budget: Int,
    @SerializedName("consumption")
    val consumption:Int,
    @SerializedName("lastConsumption")
    val lastConsumption:Int,
    @SerializedName("percent")
    val percent:Int,
    @SerializedName("categoryData")
    val categoryData: List<CategoryAnalysisData>,
    @SerializedName("expenditureData")
    val cexpenditureData: List<ExpenditureAnalysisData>
)
data class CategoryAnalysisData(
    @SerializedName("categoryId")
    val categoryId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("consumption")
    val consumption:Int,
    @SerializedName("percent")
    val percent:Int,
)
data class ExpenditureAnalysisData(
    @SerializedName("month")
    val month: Int,
    @SerializedName("expenditure")
    val expenditure: Int,
)

