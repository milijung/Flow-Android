package com.example.client.data.model

import com.google.gson.annotations.SerializedName

data class DateRecordData(
    @SerializedName("result")
    val result:DateRecordDataResult
)

data class DateRecordDataResult(
    @SerializedName("totalAmount")
    val totalAmount:ArrayList<DateRecordDataTotalAmount>,
    @SerializedName("transaction")
    val transaction:ArrayList<DateRecordDataTran>,
)

data class DateRecordDataTotalAmount(
    @SerializedName("total")
    val total:Int,
    @SerializedName("isExp")
    val isExp:Int,
)

data class DateRecordDataTran(
    @SerializedName("isExp")
    val isExp:Int,
    @SerializedName("info")
    val info:String,
    @SerializedName("amount")
    val amount:Int,
    @SerializedName("time")
    val time:Int,
    @SerializedName("memo")
    val memo:String,
    @SerializedName("detailId")
    val detailId:Int,
    @SerializedName("categoryId")
    val categoryId:Int,
    @SerializedName("integratedId")
    val integratedId:Int,
)
