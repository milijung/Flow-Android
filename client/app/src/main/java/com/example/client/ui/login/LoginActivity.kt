package com.example.client.ui.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import com.example.client.APIObject
import com.example.client.databinding.ActivityLoginBinding

import com.example.client.R
import com.example.client.data.AppDatabase
import com.example.client.data.Category
import com.example.client.data.CategoryService
import com.example.client.data.model.CategoryData
import com.example.client.data.model.CategoryResult
import com.example.client.ui.signup.SignUpActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signupButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    //api 요청 - roomDB에 카테고리 저장
    private fun getCategory(userId:Int){
        val roomDb = AppDatabase.getCategoryInstance(this)

        val service: CategoryService = APIObject.getInstance().create(CategoryService::class.java)

        val call = service.getCategory(userId)
        var list :ArrayList<CategoryResult>?

        call.enqueue(object: Callback<CategoryData> {

            override fun onResponse(call: Call<CategoryData>, response: Response<CategoryData>) {
                if (response.isSuccessful){
                    list = response.body()?.result
                    //Log.e("Retrofit",list.toString())

                    //roomDB에 서버에서 가져온 데이터 저장
                    if (list != null) {
                        for(data in list!!){
                            if(data.isUserCreated){ //사용자가 만든 카테고리만 넣기기
                                var userImageId=0
                                var userOrderId: Int = roomDb!!.CategoryDao().selectAll().filter { category -> category.typeId==data.typeId }.size

                                if(data.typeId==1){userImageId=R.drawable.ic_category_user}
                                else if(data.typeId==2){userImageId=R.drawable.ic_category_income_user}
                                roomDb.CategoryDao().insert(Category(data.name,userImageId,data.typeId,userOrderId,data.isUserCreated,data.categoryId))
                            }
                        }
                    }
                }
                else{
                    Log.w("Retrofit", "Response Not Successful ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CategoryData>, t: Throwable) {
                Log.w("Retrofit", "Error!", t)
            }
        })



    }
}