package com.example.client.data

import com.example.client.CalendarServerData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CalendarService {
    @GET("/calendar/{year}/{month}")
    fun getAmount(
        @Path("year") year: Int,
        @Path("month") month: Int,
        @Query("userId") userId:Int,

        ): Call<CalendarServerData>
}