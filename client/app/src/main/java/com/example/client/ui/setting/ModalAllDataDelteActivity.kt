package com.example.client.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.client.databinding.ActivityModalAllDataDelteBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ModalAllDataDelteActivity : AppCompatActivity(){

    private lateinit var binding : ActivityModalAllDataDelteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityModalAllDataDelteBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //모든 데이터 삭제 api

        val retrofit = Retrofit.Builder()
            .baseUrl("jdbc:mysql://localhost:3306/flow")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // 인터페이스랑 연결 - 어떤 주소를 입력했다 정보
        val apiService = retrofit.create(ApiService::class.java)

        // 비동기적 작동위해서 큐에 넣음 - 입력한 주소 중에 하나로 연결 시도!
        apiService.allDataDelete("네").enqueue(object : Callback<Response> {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                if (response.isSuccessful){
                    val responseData = response.body()

                    if (responseData != null) {
                        Log.d("Retrofit", "Response\nCode: ${responseData.code} Message:${responseData.message}")

                        if (responseData.code == 200){
                            Toast.makeText(this@ModalAllDataDelteActivity, "데이터 삭제 성공", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else{
                    Log.w("Retrofit", "Response Not Successful ${response.code()}")
                }
            }
            override fun onFailure(call: Call<Response>, t: Throwable) {
                Log.e("Retrofit", "Error!",t)
            }
        })
    }
}