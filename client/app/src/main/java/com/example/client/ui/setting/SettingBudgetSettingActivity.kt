package com.example.client.ui.setting

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.client.R
import com.example.client.databinding.ActivitySettingBudgetSettingBinding
import kotlinx.android.synthetic.main.activity_setting_budget_setting.*
import kotlinx.android.synthetic.main.fragment_bottom_sheet.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SettingBudgetSettingActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivitySettingBudgetSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySettingBudgetSettingBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.completBtn.text = getText(R.string.finish_button)

        viewBinding.backButton.setOnClickListener(){
            super.onBackPressed()
        }

        // 예산시작일 고르는 창
        viewBinding.budgetStartBtn.setOnClickListener {
            val bottomSheet = BottomSheet()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        viewBinding.edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val edit : String = Integer.parseInt(p0.toString()).div(10000).toString()+"만 원"
                viewBinding.result.text = edit
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        viewBinding.oneBtn.setOnClickListener(View.OnClickListener {
            result.setText("1만원")
        })

        viewBinding.fiveBtn.setOnClickListener(View.OnClickListener {
            result.setText("5만원")
        })

        viewBinding.tenBtn.setOnClickListener(View.OnClickListener {
            result.setText("10만원")
        })

        viewBinding.twentyBtn.setOnClickListener(View.OnClickListener {
            result.setText("20만원")
        })

        viewBinding.thirtyBtn.setOnClickListener(View.OnClickListener {
            result.setText("30만원")
        })

        //예산 금액과 시작일 수정 api

        val retrofit = Retrofit.Builder()
            .baseUrl("jdbc:mysql://localhost:3306/flow")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // 인터페이스랑 연결 - 어떤 주소를 입력했다 정보
        val apiService = retrofit.create(ApiService::class.java)

        // 비동기적 작동위해서 큐에 넣음 - 입력한 주소 중에 하나로 연결 시도!
        apiService.getBudget(100000).enqueue(object : Callback<Response> {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                if (response.isSuccessful){
                    val responseData = response.body()

                    if (responseData != null) {
                        Log.d("Retrofit", "Response\nCode: ${responseData.code} Message:${responseData.message}")

                        if (responseData.code == 200){
                            Toast.makeText(this@SettingBudgetSettingActivity, "조회 성공", Toast.LENGTH_SHORT).show()
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

        apiService.getStartDay(15).enqueue(object : Callback<Response> {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                if (response.isSuccessful){
                    val responseData = response.body()

                    if (responseData != null) {
                        Log.d("Retrofit", "Response\nCode: ${responseData.code} Message:${responseData.message}")

                        if (responseData.code == 400){
                            Toast.makeText(this@SettingBudgetSettingActivity, "예산 설정 실패", Toast.LENGTH_SHORT).show()
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

