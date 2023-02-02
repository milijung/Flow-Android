package com.example.client.ui.setting

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query

interface ApiService {
    @GET("users/selectForms")
    fun getBankAppChoice(
        @Query("bankName") bankName: String
    ):Call<Response>

    @PATCH("users/modifyBudget")
    fun getBudget(
        @Query("budget") budget: Int
    ): Call<Response>

    fun getStartDay(
        @Query("startDay") startDay : Int
    ): Call<Response>

}