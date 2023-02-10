package com.example.client.data

import com.example.client.data.model.DateRecordData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DateRecordService {
    @GET("/calendar/{year}/{month}/{date}")
    fun getTotalAmount(
        @Path("year") year: Int,
        @Path("month") month: Int,
        @Path("date") date:Int,
        @Query("userId") userId:Int,
        ): Call<DateRecordData>
}