package com.example.client.data.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.client.data.Category
import com.example.client.data.Detail
import com.example.client.data.Keyword
import com.example.client.data.User

@Dao
interface CategoryDao {
    @Insert
    fun insert(category: Category)

    @Delete
    fun delete(category: Category)

    @Query("SELECT * FROM Category")
    fun selectAll() : List<Category>

    @Query("SELECT * FROM Category WHERE categoryId= :categoryId")
    fun selectById(categoryId: Int): Category

    @Query("SELECT categoryId FROM Category WHERE typeId = :typeId and `order`=:order")
    fun selectByOrder(typeId: Int, order: Int) : Int

    @Query("SELECT categoryId FROM Category WHERE name = :name")
    fun selectByName(name:String) : Int

    @Query("SELECT * FROM Category WHERE typeId = :typeId")
    fun selectByTypeId(typeId: Int) : List<Category>

    @Query("UPDATE Category SET `order`= `order`-1 WHERE typeId= :typeId and categoryId > :categoryId")
    fun updateCategoryOrder(categoryId: Int, typeId: Int) // 삭제한 카테고리의 id와 typeId를 param으로

    @Query("UPDATE Category SET name= :name, typeId= :typeId WHERE categoryId= :categoryId")
    fun updateCategoryInfo(categoryId: Int, name: String, typeId: Int)

    @Query("DELETE FROM Category WHERE categoryId = :categoryId")
    fun deleteCategoryById(categoryId : Int)

    @Query("DELETE FROM Category")
    fun deleteCategoryAll()
}

@Dao
interface ListDao {
    @Insert
    fun insert(detail: Detail)

    @Delete
    fun delete(detail: Detail)

    @Query("DELETE FROM Detail WHERE detailId = :detailId")
    fun deleteById(detailId: Int)

    @Query("SELECT * FROM Detail ORDER BY year DESC, month DESC, day DESC")
    fun selectAll() : List<Detail>

    @Query("SELECT * FROM Detail WHERE year = strftime('%Y','now') and month = strftime('%m','now') ORDER BY day DESC")
    fun selectThisMonth(): List<Detail>

    @Query("SELECT * FROM Detail WHERE detailId = :detailId")
    fun selectById(detailId: Int) : Detail

    @Query("SELECT * FROM Detail WHERE year = :year and month = :month and day = :day")
    fun selectByDate(year: String, month: String, day: String) : List<Detail>

    @Query("UPDATE Detail SET memo = :memo WHERE detailId= :detailId")
    fun updateMemo(detailId: Int, memo:String)

    @Query("UPDATE Detail SET categoryId= :categoryId WHERE detailId= :detailId")
    fun updateCategory(detailId: Int, categoryId:Int)

    @Query("UPDATE Detail SET isBudgetIncluded = :isBudgetIncluded WHERE detailId= :detailId")
    fun updateIsBudgetIncluded(detailId: Int, isBudgetIncluded:Boolean)

    @Query("UPDATE Detail SET categoryId = 15 WHERE categoryId = :categoryId")
    fun updateListOfDeletedCategory(categoryId: Int)

    @Query("UPDATE Detail SET isKeywordIncluded = :isKeywordIncluded WHERE detailId = :detailId")
    fun updateIsKeywordIncluded(detailId: Int, isKeywordIncluded : Boolean)

}
@Dao
interface KeywordDao {
    @Insert
    fun insert(keyword: Keyword)

    @Delete
    fun delete(keyword: Keyword)

    @Query("SELECT * FROM Keyword")
    fun selectAll() : List<Keyword>

    @Query("SELECT * FROM Keyword WHERE categoryId = :categoryId")
    fun selectByCategoryId(categoryId: Int) : List<Keyword>

    @Query("SELECT keyword FROM Keyword join Category using(categoryId) WHERE typeId = :typeId")
    fun selectByTypeId(typeId: Int) :List<String>

    @Query("SELECT * FROM Keyword WHERE keyword = :keyword")
    fun selectByKeyword(keyword: String) : Keyword

    @Query("DELETE FROM Keyword WHERE categoryId = :categoryId")
    fun deleteByCategoryId(categoryId: Int)

    @Query("DELETE FROM Keyword WHERE categoryId = :categoryId and keyword = :keyword")
    fun deleteKeyword(categoryId: Int, keyword: String)

    @Query("UPDATE Detail SET categoryId=:categoryId WHERE shop like '%'||:keyword||'%'")
    fun updateListCategoryByKeyword(keyword:String, categoryId: Int)
}
@Dao
interface UserDao {
    @Insert
    fun insert(user : User)

    @Query("DELETE FROM User WHERE userId = :userId")
    fun deleteById(userId: Int)

    @Query("SELECT userId FROM User")
    fun getUserId() : Int

    @Query("SELECT COUNT(*) FROM User")
    fun isLogin() : Int

    @Query("SELECT * FROM User")
    fun getUserInfo() : User

    @Query("UPDATE User SET budget = :budget,budgetStartDay = :budgetStartDay WHERE userId = :userId")
    fun updateBudgetInfo(userId: Int, budget: Int, budgetStartDay : Int)

    @Query("UPDATE User SET budgetAlarmPercent = :budgetAlarmPercent WHERE userId = :userId")
    fun updateBudgetAlarmPercent(userId: Int, budgetAlarmPercent: Int)
}
