package com.example.client

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CalendarService {
    @GET("/calendar/:year/:month")
    fun getExpense(
        //@Query("userId") userId:String,
        @Query("year") year: Int,
        @Query("month") month: Int,

    ): Call<CalendarServerData>
}