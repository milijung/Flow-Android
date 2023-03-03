package com.example.client.ui.category

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.client.R
import com.example.client.api.CategoryRequestData
import com.example.client.api.HttpConnection
import com.example.client.data.model.AppDatabase
import com.example.client.databinding.ActivityAddCategoryBinding
import com.example.client.ui.navigation.BottomNavigationActivity
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class AddCategoryActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityAddCategoryBinding
    private val httpConnection : HttpConnection = HttpConnection()
    // default: 지출 카테고리
    private var typeId : Int = 1
    private var iconImage : Int = R.drawable.ic_category_user
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityAddCategoryBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val listDetailIntent = intent
        val listId:Int = listDetailIntent.getIntExtra("listId",-1)
        val selectedCategoryPosition: Int = listDetailIntent.getIntExtra("order",0)

        val roomDb = AppDatabase.getInstance(this)

        viewBinding.addCategoryButton.text = getText(R.string.add_category_button)
        viewBinding.addCategoryType.check(R.id.addCategory_type1)
        
        // 카테고리 타입 변경(지출 or 수입)
        viewBinding.addCategoryType.setOnCheckedChangeListener{ radioGroup, checkedId ->
            when(checkedId){
                R.id.addCategory_type1 ->{
                    viewBinding.addCategoryType.check(R.id.addCategory_type1)
                    typeId = 1
                    iconImage = R.drawable.ic_category_user
                }
                R.id.addCategory_type2 -> {
                    viewBinding.addCategoryType.check(R.id.addCategory_type2)
                    typeId = 2
                    iconImage = R.drawable.ic_category_income_user
                }
            }
        }
        // 뒤로가기
        viewBinding.addCategoryBackButton.setOnClickListener(){
            super.onBackPressed()
        }
        viewBinding.addCategoryButton.setOnClickListener(){
            when {
                // 카테고리 이름란이 비어있는 경우
                viewBinding.addCategoryName.text.toString().trim() == "" -> {
                    Toast.makeText(this, "카테고리 이름을 입력해주세요", Toast.LENGTH_SHORT).show()
                }
                // 이미 같은 이름의 카테고리가 있는 경우
                roomDb?.CategoryDao()?.selectByName(viewBinding.addCategoryName.text.toString().trim()) != 0 -> {
                    Toast.makeText(this, "같은 이름의 카테고리가 있습니다\n     다른 이름을 입력해주세요", Toast.LENGTH_SHORT).show()
                }
                // 카테고리 추가
                else -> {
                    val newCategoryOrder : Int = roomDb.CategoryDao().selectByTypeId(typeId).size
                    httpConnection.insertCategory(this, listId, selectedCategoryPosition, roomDb,1,
                        CategoryRequestData(viewBinding.addCategoryName.text.toString().trim(),typeId), newCategoryOrder
                    )
                    val intent = Intent(this, BottomNavigationActivity::class.java)
                    intent.putExtra("pageId",1)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }


}