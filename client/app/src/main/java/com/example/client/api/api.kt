package com.example.client.api

import com.example.client.data.Detail
import retrofit2.Call
import retrofit2.http.*

interface api {
    // User api
    @GET("/users/{userId}")
    fun getUser(
        @Path("userId") userId: Int
    ): Call<UserResponseByList>
    // List api
    @POST("/details/{userId}")
    fun insertDetail(
        @Path("userId") userId:Int,
        @Body detailRequest : InsertDetailRequestData,
    ): Call<InsertDetailResponseData>

    @GET("/details/{userId}")
    fun getDetailsOfRange(
        @Path("userId") userId:Int,
        @Query("year") year : String,
        @Query("month") month : String,
        @Query("page") page : Int,
    ): Call<DetailResponseByList>

    @PATCH("/details/{userId}/{detailId}")
    fun updateDetail(
        @Path("userId") userId:Int,
        @Path("detailId") detailId:Int,
        @Body updateDetailRequest : UpdateDetailData,
    ): Call<ResponseData>

    @PATCH("/details/{userId}/join")
    fun joinDetail(
        @Path("userId") userId:Int,
        @Body joinDetailRequest:JoinDetailData
    ):Call<ResponseData>

    @HTTP(method = "DELETE", path = "/details/{userId}", hasBody = true)
    fun deleteDetail(
        @Path("userId") userId:Int,
        @Body deleteDetailRequest: DeleteDetailRequestData
    ):Call<ResponseData>

    // Category api
    @GET("/category/{userId}")
    fun getCategory(
        @Path("userId") userId: Int,
    ): Call<CategoryResponseByList>

    @POST("/category/add/{userId}")
    fun insertCategory(
        @Path("userId") userId:Int,
        @Body categoryRequest : CategoryRequestData,
    ):Call<ResponseData>

    @PATCH("/category/{userId}/{categoryId}")
    fun updateCategory(
        @Path("userId") userId:Int,
        @Path("categoryId") categoryId:Int,
        @Body categoryRequest : CategoryRequestData,
    ):Call<ResponseData>

    @DELETE("/category/{userId}/{categoryId}")
    fun deleteCategory(
        @Path("userId") userId:Int,
        @Path("categoryId") categoryId:Int,
    ):Call<ResponseData>

    // Calendar api
    @GET("/calendar/{year}/{month}")
    fun getCalendar(
        @Path("year") year: Int,
        @Path("month") month: Int,
        @Query("userId") userId:Int,
    ): Call<CalendarResponseByList>

    @GET("/calendar/{year}/{month}/{date}")
    fun getRecordsOfDate(
        @Path("year") year: Int,
        @Path("month") month: Int,
        @Path("date") date:Int,
        @Query("userId") userId:Int,
    ): Call<RecordsOfDate>

    // setting fragment api

    // 은행 앱 추가 선택
//    @GET("users/selectForms")
//    fun getBankAppChoice(
//        @Query("bankName") bankName: String
//    ):Call<Response>

    //예산금액과 시작일 수정
    @PATCH("/users/modifyBudget")
    fun updateBudget(
        @Query("userId") userId: Int,
        @Body budgetRequest: BudgetRequest
    ): Call<ResponseData>

    //모든 데이터 삭제

    @DELETE("/users/reset/{userId}")
    fun deleteData(
        @Query("userId") userId: Int
    ): Call<ResponseData>

    //탈퇴하기
    @DELETE("/users/deleteUser/{userId}")
    fun deleteAccount(
        @Query("userId") userId: Int
    ): Call<ResponseData>

    @GET("/home/{userId}/{month}")
    fun getAnalysis(
        @Path("userId") userId : Int,
        @Path("month") month : Int
    ): Call<AnalysisResponseByList>
}
