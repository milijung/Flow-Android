package com.example.client.ui.category

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.client.APIObject
import com.example.client.api.ResponseData
import com.example.client.api.api
import com.example.client.data.model.AppDatabase
import com.example.client.data.Category
import com.example.client.data.adapter.CategoryAdapter
import com.example.client.databinding.ActivitySettingCategoryBinding
import com.example.client.ui.navigation.BottomNavigationActivity
import kotlinx.coroutines.InternalCoroutinesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.roundToInt

lateinit var settingCategoryViewBinding:ActivitySettingCategoryBinding
@InternalCoroutinesApi
class SettingCategoryActivity : AppCompatActivity() {
    private lateinit var categoryList : ArrayList<Category>
    private lateinit var adapter1 : CategoryAdapter
    private lateinit var adapter2 : CategoryAdapter
    private lateinit var roomDb : AppDatabase
    private val request: api = APIObject.getInstance().create(api::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingCategoryViewBinding = ActivitySettingCategoryBinding.inflate(layoutInflater)
        setContentView(settingCategoryViewBinding.root)

        roomDb= AppDatabase.getInstance(this)!!
        val userId = roomDb.UserDao().getUserId()
        settingCategoryViewBinding.settingCategoryDeleteButton.visibility = View.GONE
        settingCategoryViewBinding.settingCategoryModifyButton.visibility = View.GONE

        // gridview에 adapter 연결
        adapter1 = CategoryAdapter(this, roomDb.CategoryDao().selectByTypeId(1) as ArrayList<Category>, -1)
        adapter2 = CategoryAdapter(this, roomDb.CategoryDao().selectByTypeId(2) as ArrayList<Category>, -1)
        settingCategoryViewBinding.settingCategoryList.adapter = adapter1
        settingCategoryViewBinding.settingCategoryList2.adapter = adapter2

        // gridview height 설정
        val list1Params: ViewGroup.LayoutParams = settingCategoryViewBinding.settingCategoryList.layoutParams
        list1Params.height = getGridviewHeight(settingCategoryViewBinding.settingCategoryList.adapter.count)
        settingCategoryViewBinding.settingCategoryList.layoutParams = list1Params

        val list2Params: ViewGroup.LayoutParams = settingCategoryViewBinding.settingCategoryList2.layoutParams
        list2Params.height = getGridviewHeight(settingCategoryViewBinding.settingCategoryList2.adapter.count)
        settingCategoryViewBinding.settingCategoryList2.layoutParams = list2Params

        // 뒤로가기
        settingCategoryViewBinding.settingCategoryBackButton.setOnClickListener() {
            val intent = Intent(this, BottomNavigationActivity::class.java)
            intent.putExtra("pageId",3)
            startActivity(intent)
            finish()
        }
        // 카테고리 추가 화면으로 이동
        settingCategoryViewBinding.settingCategoryAddButton.setOnClickListener() {
            val intent = Intent(this, AddCategoryActivity::class.java)
            startActivity(intent)
        }
        // 카테고리 삭제
        settingCategoryViewBinding.settingCategoryDeleteButton.setOnClickListener(){
            val categoryId : Int = roomDb.CategoryDao().selectByName((currentFocus as AppCompatButton).text.toString())
            val category : Category = roomDb.CategoryDao().selectById(categoryId)
            if(categoryId != 0) {
                //카테고리 삭제
                deleteCategory(this, roomDb,userId, category)
            }
        }
        // 카테고리 수정
        settingCategoryViewBinding.settingCategoryModifyButton.setOnClickListener(){
            val intent = Intent(this, UpdateCategoryActivity::class.java)
            intent.putExtra("categoryId", roomDb.CategoryDao().selectByName((currentFocus as AppCompatButton).text.toString()))
            startActivity((intent))
        }
    }
    private fun deleteCategory(context: Context, roomDb: AppDatabase, userId:Int, category: Category) {
        val call = request.deleteCategory(userId,category.categoryId)

        call.enqueue(object: Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                if(response.body()!!.isSuccess){
                    // 카테고리 삭제
                    roomDb.CategoryDao().deleteCategoryById(category.categoryId)
                    // 해당 카테고리에 연결된 키워드 삭제
                    roomDb.KeywordDao().deleteByCategoryId(category.categoryId)
                    // 카테고리 position 재정렬
                    roomDb.CategoryDao().updateCategoryOrder(category.categoryId, category.typeId)
                    // 목록 새로고침
                    refreshCategoryList(category)
                    when(category.typeId){
                        1->Toast.makeText(context, "카테고리가 성공적으로 삭제되었습니다       \n${category.name}의 내역은 기타지출로 분류됩니다", Toast.LENGTH_SHORT).show()
                        2->Toast.makeText(context, "카테고리가 성공적으로 삭제되었습니다      \n${category.name}의 내역은 수입으로 분류됩니다", Toast.LENGTH_SHORT).show()
                    }

                } else{
                    Toast.makeText(context, "카테고리가 삭제되지 않았습니다\n    나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                }
                println(response.body()!!.message)
            }
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Toast.makeText(context, "요청을 성공적으로 전송하지 못했습니다\n        나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }
        })
    }
    // 카테고리 삭제 후 목록 refresh
    private fun refreshCategoryList(deleteCategory: Category) {
        val categoryListView : GridView
        val typeId : Int
        val adapter : CategoryAdapter
        when {
            settingCategoryViewBinding.settingCategoryList.hasFocus() -> {
                typeId = 1
                adapter = adapter1
                categoryListView = settingCategoryViewBinding.settingCategoryList
            }
            else -> {
                typeId = 2
                adapter = adapter2
                categoryListView = settingCategoryViewBinding.settingCategoryList2
            }
        }
        adapter.updateCategoryList(roomDb.CategoryDao().selectByTypeId(typeId) as ArrayList<Category>)
        categoryListView.adapter = adapter
        (categoryListView.adapter as CategoryAdapter).notifyDataSetChanged()
    }
    private fun getGridviewHeight(size : Int) : Int{
        return if ((size / 3 != 0) or (size < 3))
            convertDPtoPX(this, 55) * (size / 3 + 1)
        else
            convertDPtoPX(this, 55) * (size / 3)
    }

    // dp를 픽셀 단위로 변환
    private fun convertDPtoPX(context: Context, dp: Int): Int {
        val density: Float = context.resources.displayMetrics.density
        return (dp.toFloat() * density).roundToInt()
    }
}

