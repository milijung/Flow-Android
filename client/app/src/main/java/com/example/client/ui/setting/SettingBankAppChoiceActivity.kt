package com.example.client.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.client.R
import com.example.client.databinding.ActivitySettingBankAppChoiceBinding
import com.example.client.databinding.ActivitySettingLetterAddInputBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SettingBankAppChoiceActivity : AppCompatActivity() {

    private lateinit var viewbinding: ActivitySettingBankAppChoiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewbinding = ActivitySettingBankAppChoiceBinding.inflate(layoutInflater)
        setContentView(viewbinding.root)

        viewbinding.applyBtn.text = getText(R.string.apply_button)

        // retrofit 객체 만들기 - 웹 브라우저 창 열기
        val retrofit = Retrofit.Builder()
            .baseUrl("jdbc:mysql://localhost:3306/flow")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // 인터페이스랑 연결 - 어떤 주소를 입력했다 정보
        val apiService = retrofit.create(ApiService::class.java)

        // 비동기적 작동위해서 큐에 넣음 - 입력한 주소 중에 하나로 연결 시도!
        apiService.getBankAppChoice("abc은행").enqueue(object :Callback<Response>{
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                if (response.isSuccessful){
                    val responseData = response.body()

                    if (responseData != null) {
                        Log.d("Retrofit", "Response\nCode: ${responseData.code} Message:${responseData.message}")

                        if (responseData.code == 200){
                            Toast.makeText(this@SettingBankAppChoiceActivity, "조회 성공", Toast.LENGTH_SHORT).show()
                        }
                        if (responseData.code == 400){
                            Toast.makeText(this@SettingBankAppChoiceActivity, "은행 선택 안함", Toast.LENGTH_SHORT).show()
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