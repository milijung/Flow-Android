package com.example.client.ui.setting

import com.example.client.data.Result

data class Response(
    val isSuccess : Boolean,
    val code : Int,
    val message : String,
    val result : String
)
