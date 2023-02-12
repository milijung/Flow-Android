package com.example.client

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIObject {
    private const val BASE_URL = "http://52.79.79.123:8080"
    private var instance: Retrofit? = null

    fun getInstance() : Retrofit {
        if (instance == null) {
            instance = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return instance!!
    }
}