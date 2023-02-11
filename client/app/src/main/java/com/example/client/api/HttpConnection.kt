package com.example.client.api
import android.content.Context
import android.util.Log
import com.example.client.APIObject
import com.example.client.data.*
import com.example.client.data.model.CategoryRequestData
import com.example.client.data.model.ResponseData
import kotlinx.coroutines.InternalCoroutinesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
open class HttpConnection {
    private val request: api = APIObject.getInstance().create(api::class.java)

    fun getUserInfo(roomDb: AppDatabase, userId: Int) {
        val call = request.getUser(userId)
        call.enqueue(object : Callback<UserResponseByList>{
            override fun onResponse(
                call: Call<UserResponseByList>,
                response: Response<UserResponseByList>
            ) {
                if(response.isSuccessful)
                    roomDb.UserDao().insert(response.body()?.result!!)
                println(response.body()?.message)
            }

            override fun onFailure(call: Call<UserResponseByList>, t: Throwable) {
                Log.w("Retrofit", "Error!", t)
            }
        })
    }
    fun insertList(userId:Int, requestData: Detail) {
        val call = request.insertDetail(userId,requestData)
        call.enqueue(object: Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if (response.isSuccessful){
                    println("${response.body()?.message}")
                    println("${response.body()?.result}")
                }
                else{
                    println("${response.body()?.message}")
                }
            }
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.w("Retrofit", "Error!", t)
            }
        })
    }
    fun updateList(userId:Int, detailId : Int, body : UpdateDetailData) {
        val call = request.updateDetail(userId,detailId, body)
        call.enqueue(object: Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>)  {
                println(response.body()?.message)
            }
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.w("Retrofit", "Error!", t)
            }
        })
    }

    fun getCategory(roomDb: AppDatabase,userId:Int) {
        val call = request.getCategory(userId)

        call.enqueue(object: Callback<CategoryResponseByList> {
            override fun onResponse(call: Call<CategoryResponseByList>, response: Response<CategoryResponseByList>) {
                if (response.isSuccessful){
                    for(category in response.body()?.result!!)
                        roomDb.CategoryDao().insert(category)
                }
                else{
                    Log.w("Retrofit", "Response Not Successful ${response.code()}")
                }
            }
            override fun onFailure(call: Call<CategoryResponseByList>, t: Throwable) {
                Log.w("Retrofit", "Error!", t)
            }
        })
    }
    fun insertCategory(roomDb: AppDatabase, userId:Int, category: CategoryRequestData) {
        val call = request.insertCategory(userId, category)

        call.enqueue(object: Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                // roomDb에 카테고리 추가하는 코드 작성하기
                println(response.body()!!.message)
            }
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.w("Retrofit", "Error!", t)
            }
        })
    }
    fun deleteCategory(roomDb: AppDatabase, userId:Int, categoryId:Int) {
        val call = request.deleteCategory(userId,categoryId)

        call.enqueue(object: Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                roomDb.CategoryDao().deleteCategoryById(categoryId)
                println(response.body()!!.message)
            }
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.w("Retrofit", "Error!", t)
            }
        })
    }
    fun updateCategory(userId:Int, categoryId:Int, requestData: CategoryRequestData) {
        val call = request.updateCategory(userId,categoryId,requestData)

        call.enqueue(object: Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                println(response.body()?.message)
            }
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.w("Retrofit", "Error!", t)
            }
        })
    }
}
