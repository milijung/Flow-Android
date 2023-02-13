package com.example.client.ui.category

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.client.R
import com.example.client.api.CategoryRequestData
import com.example.client.api.HttpConnection
import com.example.client.data.AppDatabase
import com.example.client.data.Category
import com.example.client.databinding.ActivityAddCategoryBinding
import com.example.client.ui.navigation.BottomNavigationActivity
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class AddCategoryActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityAddCategoryBinding
    // default: 지출 카테고리
    var typeId : Int = 1
    var iconImage : Int = R.drawable.ic_category_user
    private val httpConnection : HttpConnection = HttpConnection()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityAddCategoryBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val listDetailIntent = intent
        val listId:Int = listDetailIntent.getIntExtra("listId",-1)
        val categoryType : Int = listDetailIntent.getIntExtra("typeId",1)
        val selectedCategoryPosition: Int = listDetailIntent.getIntExtra("order",0)
        val roomDb = AppDatabase.getInstance(this) // 카테고리 DB


        viewBinding.addCategoryButton.text = getText(R.string.add_category_button)
        viewBinding.addCategoryType.check(R.id.addCategory_type1)
        
        // 카테고리 타입 change listener(지출 or 수입)
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
        viewBinding.addCategoryBackButton.setOnClickListener(){
            // 추가되지 않는다는 modal창 띄우기
            // 뒤로가기
            super.onBackPressed()
        }
        viewBinding.addCategoryButton.setOnClickListener(){
            // 카테고리 이름이 비어있는 경우
            if(viewBinding.addCategoryName.text.toString().trim() == ""){
                Toast.makeText(this, "카테고리 이름을 입력해주세요", Toast.LENGTH_SHORT).show()
            } else if(roomDb?.CategoryDao()?.selectByName(viewBinding.addCategoryName.text.toString().trim()) != 0){
                Toast.makeText(this, "같은 이름의 카테고리가 있습니다\n     다른 이름을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
            else{
                // 카테고리 추가
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