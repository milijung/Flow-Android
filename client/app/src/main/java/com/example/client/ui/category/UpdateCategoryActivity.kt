package com.example.client.ui.category

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.client.APIObject
import com.example.client.R
import com.example.client.data.AppDatabase
import com.example.client.data.Category
import com.example.client.data.CategoryService
import com.example.client.data.model.CategoryRequestData
import com.example.client.data.model.CategoryResponseData
import com.example.client.databinding.ActivityUpdateCategoryBinding
import kotlinx.android.synthetic.main.activity_add_category.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateCategoryActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityUpdateCategoryBinding
    private lateinit var category: Category
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityUpdateCategoryBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val categoryIntent = intent
        val categoryId : Int = categoryIntent.getIntExtra("categoryId",1)



        val roomDb = AppDatabase.getCategoryInstance(this)
        if(roomDb != null){
            category = roomDb.CategoryDao().selectById(categoryId)
        }
        var typeId = category.typeId

        viewBinding.updateCategoryButton.text = getText(R.string.finish_button)
        viewBinding.updateCategoryIcon.setImageResource(category.image)
        viewBinding.updateCategoryIconName.text = category.name
        viewBinding.updateCategoryName.setText(category.name)

        when(typeId){
            1 -> viewBinding.updateCategoryType.check(R.id.updateCategory_type1)
            else -> viewBinding.updateCategoryType.check(R.id.updateCategory_type2)
        }
        // 지출 수입 radio button
        if(category.isUserCreated){
            viewBinding.updateCategoryType.setOnCheckedChangeListener{ radioGroup, checkedId ->
                when(checkedId){
                    R.id.updateCategory_type1 ->{
                        viewBinding.updateCategoryType.check(R.id.updateCategory_type1)
                        typeId = 1
                    }
                    R.id.updateCategory_type2 -> {
                        viewBinding.updateCategoryType.check(R.id.updateCategory_type2)
                        typeId = 2
                    }
                }
            }
        } else{
            // 기본 카테고리인 경우 이름만 변경 가능. type은 변경할 수 없음.
            viewBinding.updateCategoryType1.isEnabled = false
            viewBinding.updateCategoryType2.isEnabled = false

        }
        // 닫기 버튼
        viewBinding.updateCategoryBackButton.setOnClickListener(){
            super.onBackPressed()
        }
        // 수정 완료하기 버튼
        viewBinding.updateCategoryButton.setOnClickListener(){
            if (roomDb != null) {
                val newName : String  = viewBinding.updateCategoryName.text.toString().trim()
                if(newName == ""){
                    // 공백 안된다는 modal창 띄우기
                }else if((roomDb.CategoryDao().selectByName(newName) != 0) and (newName != viewBinding.updateCategoryIconName.text)){
                    // 같은 이름의 카테고리가 있다는 modal창 띄우기

                }else {
                    roomDb.CategoryDao().updateCategoryInfo(
                        categoryId,
                        newName,
                        typeId
                    )

                    //서버에 카테고리 수정하기
                    updateCategory(1,categoryId,CategoryRequestData(newName,typeId))

                    val intent = Intent(this, SettingCategoryActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    //api 요청 - 카테고리 수정
    private fun updateCategory(userId:Int, categoryId:Int, requestData: CategoryRequestData) {

        val service: CategoryService = APIObject.getInstance().create(
            CategoryService::class.java)

        val call = service.patchCategory(userId,categoryId,requestData)

        call.enqueue(object: Callback<CategoryResponseData> {
            override fun onResponse(call: Call<CategoryResponseData>, response: Response<CategoryResponseData>) {
                if (response.isSuccessful){
                    //Log.d("log",response.toString())
                    //Log.d("log", response.body().toString())
                }
                else{
                    Log.w("Retrofit", "Response Not Successful ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CategoryResponseData>, t: Throwable) {
                Log.w("Retrofit", "Error!", t)
            }
        })

    }
}