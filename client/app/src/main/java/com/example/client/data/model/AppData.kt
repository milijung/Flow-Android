package com.example.client.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Category")
data class Category(
    @ColumnInfo(name="name") val name:String,
    @ColumnInfo(name="image") val image: Int,
    @ColumnInfo(name="typeId") val typeId: Int,  // 지출: 1, 수입: 2
    @ColumnInfo(name="order") val order: Int,
    @ColumnInfo(name="isUserCreated") val isUserCreated: Boolean,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name="categoryId") val categoryId:Int =0,
)
@Entity(tableName = "Detail")
data class Detail(
    @ColumnInfo(name="userId") val userId:Int,
    @ColumnInfo(name="categoryId") val categoryId: Int,
    @ColumnInfo(name = "year") val year: String,
    @ColumnInfo(name = "month") val month: String,
    @ColumnInfo(name = "day") val day: String,
    @ColumnInfo(name = "time") val time: String,
    @ColumnInfo(name = "price") var price : Int,
    @ColumnInfo(name = "shop") val shop: String,
    @ColumnInfo(name="typeId") var typeId:Int,  // 지출: 1, 수입: 2
    @ColumnInfo(name="isBudgetIncluded") val isBudgetIncluded : Boolean,
    @ColumnInfo(name="isKeywordIncluded") val isKeywordIncluded : Boolean = false,
    @ColumnInfo(name = "memo") val memo : String,
    @ColumnInfo(name="integratedId") var integratedId : Int = -1,
    @PrimaryKey @ColumnInfo(name="detailId") val detailId : Int = 0,
)
@Entity(tableName = "Keyword")
data class Keyword(
    @ColumnInfo(name="categoryId") val categoryId: Int,
    @ColumnInfo(name= "keyword") val keyword: String,
    @ColumnInfo(name= "isUserCreated") val isUserCreated: Boolean,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name="keywordId") val keywordId: Int = 0,
)
@Entity(tableName = "User")
data class User(
    @PrimaryKey @ColumnInfo(name="userId") val userId: Int,
    @ColumnInfo(name="budget") val budget: Int,
    @ColumnInfo(name= "budgetStartDay") val budgetStartDay: Int,
    @ColumnInfo(name= "budgetAlarmPercent") val budgetAlarmPercent: Int,
    @ColumnInfo(name= "isFingerprintOn") val isFingerprintOn: Boolean = false,
)



