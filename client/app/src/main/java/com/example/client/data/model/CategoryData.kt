package com.example.client.data.model


import com.example.client.data.Detail
import com.google.gson.annotations.SerializedName

data class CategoryData(
    @SerializedName("isSuccess")
    val isSuccess:Boolean,
    @SerializedName("code")
    val code:Int,
    @SerializedName("message")
    val typeId:String,
    @SerializedName("result")
    val result:ArrayList<CategoryResult>
)

data class CategoryResult(
    @SerializedName("categoryId")
    val categoryId:Int,
    @SerializedName("name")
    val name:String,
    @SerializedName("typeId")
    val typeId:Int,
    @SerializedName("isUserCreated")
    val isUserCreated:Boolean,

)

data class CategoryRequestData(
    @SerializedName("name")
    val name:String,
    @SerializedName("typeId")
    val typeId:Int,
)

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
data class ResponseListData(
    @SerializedName("isSuccess")
    val isSuccess:Boolean,
    @SerializedName("code")
    val code:Int,
    @SerializedName("message")
    val message:String,
    @SerializedName("result")
    val result: List<Detail>,
)


