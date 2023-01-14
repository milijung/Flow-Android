package com.example.client.ui.board

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.client.ui.category.ChangeCategoryActivity
import com.example.client.R
import com.example.client.data.AppDatabase
import com.example.client.data.Category
import com.example.client.databinding.ActivityListDetailBinding

class ListDetailActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityListDetailBinding
    private lateinit var listItem : com.example.client.data.List
    private lateinit var selectedCategory : Category
    // DB 초기값 insert, 나중에 수정해야 함
    fun InsertData(context: Context) {
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("하루세끼", R.drawable.ic_category_food,1,0,false))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("주거·통신", R.drawable.ic_category_living,1,1,false))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("교통관련", R.drawable.ic_category_traffic,1,2,false))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("생필품", R.drawable.ic_category_market,1,3,false))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("나를 위한", R.drawable.ic_category_formeexpense,1,4,false))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("선물준비", R.drawable.ic_category_present,1,5,false))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("자기계발",
                R.drawable.ic_category_selfimprovement,1,6,false))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("카페·간식", R.drawable.ic_category_cafe,1,7,false))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("저축", R.drawable.ic_category_saving,1,8,false))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("술·외식", R.drawable.ic_category_alchol,1,9,false))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("의료·건강", R.drawable.ic_category_medical,1,10,false))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("오락·취미",
                R.drawable.ic_category_entertainment,1,11,false))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("여행", R.drawable.ic_category_travel,1,12,false))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("자산이동", R.drawable.ic_category_assetmovement,1,13,false))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("기타지출", R.drawable.ic_category_others,1,14,false))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("애견용품", R.drawable.ic_category_user,1,15,true))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("회비", R.drawable.ic_category_user,1,16,true))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("수입", R.drawable.ic_category_income,2,0,false))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("장학금", R.drawable.ic_category_income_user,2,1,true))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("이자", R.drawable.ic_category_income_user,2,2,true))
        AppDatabase.getListInstance(context)
            ?.ListDao()?.insert(com.example.client.data.List(1,"2023","01","15","03:47","(주)우아한형제들",10000,"",1,true))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityListDetailBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val listDetailIntent = intent
        val listId = listDetailIntent.getIntExtra("listId",1) // 넘겨받은 내역 id
        val listDb = AppDatabase.getListInstance(this) // 내역 DB
        val categoryDb = AppDatabase.getCategoryInstance(this) // 카테고리 DB
        if(categoryDb?.CategoryDao()?.selectAll()?.size!! == 0)
            InsertData(this)

        if (listDb != null) {
            listItem = listDb.ListDao().selectById(listId) // 넘겨받은 내역의 Data
        }
        if (categoryDb != null) {
            selectedCategory = categoryDb.CategoryDao().selectById(listItem.categoryId)
        }// 넘겨받은 내역의 카테고리 Data


        // 넘겨받은 내역의 Data를 화면에 표시
        viewBinding.listDetailButton.text = getText(R.string.finish_button)
        viewBinding.listDetailMemoContent.setText(listItem.memo)
        viewBinding.listDetailTime.text = listItem.year + "년 "+listItem.month+"월 "+listItem.day+"일 "+listItem.time
        viewBinding.listDetailPlace.text = listItem.shop
        viewBinding.listDetailPrice.text = listItem.price.toString()+"원"
        viewBinding.listDetailCategoryNameButton.text = selectedCategory.name
        viewBinding.listDetailBubble.text = listItem.shop+" 내역의 카테고리가 선택한 카테고리로 모두 바뀌게 돼요!"
        viewBinding.listDetailSwitch1.isChecked = listItem.isBudgetIncluded

        viewBinding.listDetailBackButton.setOnClickListener(){
            // 내용이 변경되지 않는다는 modal창 추가
            //뒤로가기
            super.onBackPressed()
        }

        viewBinding.listDetailCategoryNameButton.setOnClickListener(){
            // 카테고리 수정 화면으로 이동. 내역의 id와 typeId와 categoryId를 담아서 전송
            val intent = Intent(this, ChangeCategoryActivity::class.java)
            intent.putExtra("listId",listItem.listId)
            intent.putExtra("typeId",listItem.typeId)
            intent.putExtra("order",selectedCategory.order)
            startActivity(intent)
        }
        viewBinding.listDetailButton.setOnClickListener(){
            // Memo 내용, 예산 저장 여부 DB에 update
            if (listDb != null) {
                listDb.ListDao().updateMemo(listId,viewBinding.listDetailMemoContent.text.toString())
                listDb.ListDao().updateIsBudgetIncluded(listId, viewBinding.listDetailSwitch1.isChecked)
            }

        }
    }
}