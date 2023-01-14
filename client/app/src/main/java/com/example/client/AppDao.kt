package com.example.client

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CategoryDao {
    @Insert
    fun insert(category: Category)

    @Delete
    fun delete(category: Category)

    @Query("SELECT * FROM Category")
    fun selectAll() : kotlin.collections.List<Category>

    @Query("SELECT * FROM Category WHERE typeId= :typeId ORDER BY `order`")
    fun selectByTypeId(typeId: Int): kotlin.collections.List<Category>

    @Query("SELECT * FROM Category WHERE categoryId= :categoryId")
    fun selectById(categoryId: Int): Category

    @Query("SELECT categoryId FROM Category WHERE typeId = :typeId and `order`=:order")
    fun selectByOrder(typeId: Int, order: Int) : Int

    @Query("SELECT categoryId FROM Category WHERE name = :name")
    fun selectByName(name:String) : Int

    @Query("SELECT * FROM Category WHERE name like '%'||:searchKeyword||'%'")
    fun searchCategory(searchKeyword: String) : kotlin.collections.List<Category>

    @Query("UPDATE Category SET `order`= `order`-1 WHERE typeId= :typeId and categoryId > :categoryId")
    fun updateCategoryOrder(categoryId: Int, typeId: Int) // 삭제한 카테고리의 id와 typeId를 param으로

    @Query("UPDATE Category SET name= :name and typeId= :typeId WHERE categoryId= :categoryId")
    fun updateCategoryInfo(categoryId: Int, name: String, typeId: Int)
}

@Dao
interface ListDao {
    @Insert
    fun insert(list: List)

    @Delete
    fun delete(list:List)

    @Query("SELECT * FROM List")
    fun selectAll() : kotlin.collections.List<List>

    @Query("SELECT * FROM List WHERE year = strftime('%Y','now') and month = strftime('%m','now')")
    fun selectThisMonth(): kotlin.collections.List<List>

    @Query("SELECT * FROM List WHERE listId = :listId")
    fun selectById(listId: Int) : List

    @Query("UPDATE List SET memo = :memo WHERE listId= :listId")
    fun updateMemo(listId: Int, memo:String)

    @Query("UPDATE List SET categoryId= :categoryId WHERE listId= :listId")
    fun updateCategory(listId: Int, categoryId:Int)

    @Query("UPDATE List SET listId= :listId WHERE isBudgetIncluded = :isBudgetIncluded")
    fun updateIsBudgetIncluded(listId: Int, isBudgetIncluded:Boolean)

}