package com.example.client.ui.board

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.client.ui.category.ChangeCategoryActivity
import com.example.client.R
import com.example.client.data.AppDatabase
import com.example.client.data.BankStatementRepository
import com.example.client.data.Category
import com.example.client.data.Keyword
import com.example.client.databinding.ActivityListDetailBinding
import com.example.client.ui.navigation.BottomNavigationActivity
import kotlinx.android.synthetic.main.activity_bottom_navigation.*

class ListDetailActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityListDetailBinding
    private lateinit var listItem : com.example.client.data.List
    private lateinit var selectedCategory : Category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityListDetailBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val listDetailIntent = intent
        val listId = listDetailIntent.getIntExtra("listId",1) // 넘겨받은 내역 id
        val listDb = AppDatabase.getListInstance(this) // 내역 DB
        val categoryDb = AppDatabase.getCategoryInstance(this) // 카테고리 DB
        val keywordDb = AppDatabase.getKeywordInstance(this) // 키워드 DB
        val bankStatementRepository = BankStatementRepository(this)

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
        viewBinding.listDetailPrice.text = listItem.price+"원"
        viewBinding.listDetailCategoryNameButton.text = selectedCategory.name
        viewBinding.listDetailBubble.text = listItem.shop+" 내역의 카테고리가 선택한 카테고리로 모두 바뀌게 돼요!"
        viewBinding.listDetailSwitch1.isChecked = listItem.isBudgetIncluded
        viewBinding.listDetailSwitch2.isChecked = listItem.isKeywordIncluded

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
                listDb.ListDao().updateIsBudgetIncluded(listId,viewBinding.listDetailSwitch2.isChecked)
                when(viewBinding.listDetailSwitch2.isChecked){
                    true -> {
                        keywordDb!!.KeywordDao().insert(Keyword(listItem.categoryId,listItem.shop,true))
                        keywordDb!!.KeywordDao().updateListCategoryByKeyword(listItem.shop, listItem.categoryId)
                    }
                    else -> {
                        keywordDb!!.KeywordDao().deleteKeyword(listItem.categoryId, listItem.shop)
                        val newCategoryId : Int = bankStatementRepository.extractCategory(listItem.shop,listItem.typeId)
                        keywordDb!!.KeywordDao().updateListCategoryByKeyword(listItem.shop, newCategoryId)
                    }
                }
            }
            // Board 화면으로 이동
            val intent = Intent(this, BottomNavigationActivity::class.java)
            intent.putExtra("pageId",1)
            startActivity(intent)
        }
    }
}