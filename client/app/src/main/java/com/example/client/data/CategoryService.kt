package com.example.client.data


import com.example.client.data.model.CategoryData
import com.example.client.data.model.CategoryRequestData
import com.example.client.data.model.CategoryResponseData
import retrofit2.Call
import retrofit2.http.*

interface CategoryService {
    @GET("/category/{userId}")
    fun getCategory(
        @Path("userId") userId: Int,
    ): Call<CategoryData>

    @POST("/category/add/{userId}")
    fun postCategory(
        @Path("userId") userId:Int,
        @Body categoryRequest : CategoryRequestData,
    ):Call<CategoryResponseData>


    @PATCH("/category/{userId}/{categoryId}")
    fun patchCategory(
        @Path("userId") userId:Int,
        @Path("categoryId") categoryId:Int,
        @Body categoryRequest : CategoryRequestData,
    ):Call<CategoryResponseData>

    @DELETE("/category/{userId}/{categoryId}")
    fun deleteCategory(
        @Path("userId") userId:Int,
        @Path("categoryId") categoryId:Int,
    ):Call<CategoryResponseData>

}