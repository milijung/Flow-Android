package com.example.client.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.SET_DEFAULT
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
@Entity(tableName = "List")
data class List(
    @ColumnInfo(name="typeId") val typeId:Int,  // 지출: 1, 수입: 2
    @ColumnInfo(name = "year") val year: String,
    @ColumnInfo(name = "month") val month: String,
    @ColumnInfo(name = "day") val day: String,
    @ColumnInfo(name = "time") val time: String,
    @ColumnInfo(name = "shop") val shop: String,
    @ColumnInfo(name = "price") val price : Int,
    @ColumnInfo(name = "memo") val memo : String,
    @ColumnInfo(name="categoryId") val categoryId: Int,
    @ColumnInfo(name="isBudgetIncluded") val isBudgetIncluded : Boolean,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name="listId") val listId : Int = 0,
)


