package com.example.client.api
import android.content.Context
import android.util.Log
import com.example.client.APIObject
import com.example.client.R
import com.example.client.data.*
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
                    roomDb.CategoryDao()?.insert(Category("하루세끼", R.drawable.ic_category_food,1,0,false,1))
                    roomDb.CategoryDao()?.insert(Category("주거·통신", R.drawable.ic_category_living,1,1,false,2))
                    roomDb.CategoryDao()?.insert(Category("교통관련", R.drawable.ic_category_traffic,1,2,false,3))
                    roomDb.CategoryDao()?.insert(Category("생필품", R.drawable.ic_category_market,1,3,false,4))
                    roomDb.CategoryDao()?.insert(Category("나를 위한", R.drawable.ic_category_formeexpense,1,4,false,5))
                    roomDb.CategoryDao()?.insert(Category("선물준비", R.drawable.ic_category_present,1,5,false,6))
                    roomDb.CategoryDao()?.insert(
                            Category("자기계발",
                                R.drawable.ic_category_selfimprovement,1,6,false,7)
                        )
                    roomDb.CategoryDao()?.insert(Category("카페·간식", R.drawable.ic_category_cafe,1,7,false,8))
                    roomDb.CategoryDao()?.insert(Category("저축", R.drawable.ic_category_saving,1,8,false,9))
                    roomDb.CategoryDao()?.insert(Category("술·외식", R.drawable.ic_category_alchol,1,9,false,10))
                    roomDb.CategoryDao()?.insert(Category("의료·건강", R.drawable.ic_category_medical,1,10,false,11))
                    roomDb.CategoryDao()?.insert(Category("오락·취미", R.drawable.ic_category_entertainment,1,11,false,12))
                    roomDb.CategoryDao()?.insert(Category("여행", R.drawable.ic_category_travel,1,12,false,13))
                    roomDb.CategoryDao()?.insert(Category("자산이동", R.drawable.ic_category_assetmovement,1,13,false,14))
                    roomDb.CategoryDao()?.insert(Category("기타지출", R.drawable.ic_category_others,1,14,false,15))
                    roomDb.CategoryDao()?.insert(Category("수입", R.drawable.ic_category_income,2,0,false,16))
                    var image : Int = 0
                    var order : Int = 0
                    for(category in response.body()?.result!!){
                        when(category.typeId){
                            1 -> {
                                image = R.drawable.ic_category_user
                                order = roomDb.CategoryDao().selectByTypeId(1).size
                            }
                            else -> {
                                image = R.drawable.ic_category_income_user
                                order = roomDb.CategoryDao().selectByTypeId(2).size
                            }
                        }
                        if(roomDb.CategoryDao().selectById(category.categoryId) == null)
                            roomDb.CategoryDao().insert(Category(category.name, image, category.typeId,order,category.isUserCreated,category.categoryId))
                    }
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
