package com.example.client.ui.setting

import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query

interface ApiService {
    @GET("users/selectForms")
    fun getBankAppChoice(
        @Query("bankName") bankName: String
    ):Call<Response>

    //예산금액과 시작일 수정

    @PATCH("users/modifyBudget")
    fun getBudget(
        @Query("budget") budget: Int
    ): Call<Response>

    fun getStartDay(
        @Query("startDay") startDay : Int
    ): Call<Response>

    //모든 데이터 삭제

    @DELETE("users/reset/:userId")
    fun allDataDelete(
        @Query("dataDelete") dataDelete : String
    ): Call<Response>

    //탈퇴하기
    @DELETE("users/deleteUser/:userId")
   fun getOut(
        @Query("getOut") getOut : String
    ): Call<Response>

}