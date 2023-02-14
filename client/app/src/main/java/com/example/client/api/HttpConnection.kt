package com.example.client.api
import android.content.Context
import android.content.Intent

import android.util.Log
import android.widget.Toast
import com.example.client.APIObject
import com.example.client.R
import com.example.client.data.*
import com.example.client.ui.category.ChangeCategoryActivity
import com.example.client.ui.category.SettingCategoryActivity
import com.example.client.ui.navigation.BottomNavigationActivity
import kotlinx.coroutines.InternalCoroutinesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
open class HttpConnection {
    private val request: api = APIObject.getInstance().create(api::class.java)

    fun getUserInfo(context: Context, roomDb: AppDatabase, userId: Int) {
        val call = request.getUser(userId)
        call.enqueue(object : Callback<UserResponseByList>{
            override fun onResponse(
                call: Call<UserResponseByList>,
                response: Response<UserResponseByList>
            ) {
                if(response.body()!!.isSuccess)
                    roomDb.UserDao().insert(response.body()?.result!!)
                else{
                    Toast.makeText(context, "사용자 정보를 받아오지 못했습니다\n      나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                }
                println(response.body()?.message)
            }

            override fun onFailure(call: Call<UserResponseByList>, t: Throwable) {
                Toast.makeText(context, "사용자 정보를 받아오지 못했습니다\n      나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }
        })
    }
    @InternalCoroutinesApi
    fun insertList(context:Context, userId:Int, requestData: InsertDetailRequestData) {
        val call = request.insertDetail(userId,requestData)
        call.enqueue(object: Callback<InsertDetailResponseData> {
            override fun onResponse(call: Call<InsertDetailResponseData>, response: Response<InsertDetailResponseData>) {
                if (response.body()!!.isSuccess){
                    // Board 화면으로 이동
                    val intent = Intent(context, BottomNavigationActivity::class.java)
                    intent.putExtra("pageId",1)
                    context.startActivity(intent)
                    Toast.makeText(context, "내역이 성공적으로 추가되었습니다", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(context, "내역이 추가되지 않았습니다\n  나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()

                }
                println("${response.body()?.message}")
            }
            override fun onFailure(call: Call<InsertDetailResponseData>, t: Throwable) {
                Toast.makeText(context, "요청을 성공적으로 전송하지 못했습니다.\n        나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }
        })
    }
    @InternalCoroutinesApi
    fun updateList(context: Context, userId:Int, detailId : Int, body : UpdateDetailData) {
        val call = request.updateDetail(userId,detailId, body)
        call.enqueue(object: Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>)  {
                if(response.body()!!.isSuccess){
                    // Board 화면으로 이동
                    val intent = Intent(context, BottomNavigationActivity::class.java)
                    intent.putExtra("pageId",1)
                    context.startActivity(intent)
                    Toast.makeText(context, "내역이 성공적으로 수정되었습니다", Toast.LENGTH_SHORT).show()
                } else{
                    Toast.makeText(context, "내역이 수정되지 않았습니다\n  나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                }
                println(response.body()?.message)
            }
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Toast.makeText(context, "요청을 성공적으로 전송하지 못했습니다\n        나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getCategory(context: Context, roomDb: AppDatabase,userId:Int) {
        val call = request.getCategory(userId)

        call.enqueue(object: Callback<CategoryResponseByList> {
            override fun onResponse(call: Call<CategoryResponseByList>, response: Response<CategoryResponseByList>) {
                if (response.body()!!.isSuccess){
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
                    Toast.makeText(context, "사용자 정보를 받아오지 못했습니다\n      나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<CategoryResponseByList>, t: Throwable) {
                Toast.makeText(context, "사용자 정보를 받아오지 못했습니다\n      나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }
        })
    }
    @InternalCoroutinesApi
    fun insertCategory(context: Context, listId: Int, selectedCategoryPosition:Int, roomDb: AppDatabase, userId:Int, category: CategoryRequestData, order: Int) {
        val call = request.insertCategory(userId, category)

        call.enqueue(object: Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if(response.body()!!.isSuccess){
                    // roomDb에 카테고리 추가하는 코드 작성하기
                    val image = when(category.typeId){
                        1-> R.drawable.ic_category_user
                        else -> R.drawable.ic_category_income_user
                    }
                    roomDb.CategoryDao().insert(Category(category.name,image, category.typeId, order, true))
                    Toast.makeText(context, "카테고리가 성공적으로 추가되었습니다", Toast.LENGTH_SHORT).show()
                    // 설정 카테고리 화면에서 넘어왔던 경우
                    if(listId == -1){
                        val intent = Intent(context, SettingCategoryActivity::class.java)
                        context.startActivity(intent)
                    }
                    // 내역의 카테고리 변경 화면에서 넘어왔던 경우
                    else{
                        val intent = Intent(context, ChangeCategoryActivity::class.java)
                        intent.putExtra("listId",listId)
                        intent.putExtra("typeId",category.typeId)
                        intent.putExtra("order",selectedCategoryPosition)
                        context.startActivity(intent)
                    }
                } else{
                    Toast.makeText(context, "카테고리가 추가되지 않았습니다\n    나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                }
                println(response.body()!!.message)
            }
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Toast.makeText(context, "요청을 성공적으로 전송하지 못했습니다\n        나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }
        })
    }
    fun updateCategory(context: Context,roomDb: AppDatabase,userId:Int, categoryId:Int, requestData: CategoryRequestData) {
        val call = request.updateCategory(userId,categoryId,requestData)

        call.enqueue(object: Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if(response.body()!!.isSuccess){
                    roomDb.CategoryDao().updateCategoryInfo(
                        categoryId,
                        requestData.name,
                        requestData.typeId
                    )
                    Toast.makeText(context, "카테고리가 성공적으로 수정되었습니다", Toast.LENGTH_SHORT).show()
                } else{
                    Toast.makeText(context, "카테고리가 수정되지 않았습니다\n    나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                }
                println(response.body()?.message)
            }
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Toast.makeText(context, "요청을 성공적으로 전송하지 못했습니다\n        나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }
        })
    }
   @InternalCoroutinesApi
   fun updateBudget(context:Context, roomDb: AppDatabase,pageId:Int,userId: Int, budgetRequest: BudgetRequest) {
       val call = request.updateBudget(userId, budgetRequest)

       call.enqueue(object : Callback<ResponseData> {
           override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
               if (response.body()!!.isSuccess) {
                   roomDb.UserDao().updateBudgetInfo(userId,budgetRequest.budget, budgetRequest.startDate)
                   val intent = Intent(context, BottomNavigationActivity::class.java)
                   intent.putExtra("pageId",pageId)
                   context.startActivity(intent)
                   Toast.makeText(context, "예산이 성공적으로 수정되었습니다", Toast.LENGTH_SHORT).show()
               } else {
                   Toast.makeText(context, "예산이 수정되지 않았습니다\n  나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
               }
               println(response.body()?.message)
           }

           override fun onFailure(call: Call<ResponseData>, t: Throwable) {
               Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
           }
       })
   }
    fun allDataDelete(context:Context, userId: Int) {
        val call = request.deleteData(userId)

        call.enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if (response.body()!!.isSuccess) {
                    Toast.makeText(context, "요청 성공", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "요청 실패", Toast.LENGTH_SHORT).show()
                }
                println(response.body()?.message)
            }
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
