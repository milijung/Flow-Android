package com.example.client.ui.onboarding

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.example.client.R
import com.example.client.data.AppDatabase
import com.example.client.data.BankStatementRepository
import com.example.client.data.Category
import com.example.client.ui.board.ListDetailActivity


class SplashActivity : AppCompatActivity() {
    // DB 초기값 insert, 나중에 수정해야 함
    private fun insertData(context: Context) {
        val bankStatementRepository : BankStatementRepository = BankStatementRepository(this)
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
            ?.CategoryDao()?.insert(
                Category("자기계발",
                R.drawable.ic_category_selfimprovement,1,6,false)
            )
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("카페·간식", R.drawable.ic_category_cafe,1,7,false))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("저축", R.drawable.ic_category_saving,1,8,false))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("술·외식", R.drawable.ic_category_alchol,1,9,false))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("의료·건강", R.drawable.ic_category_medical,1,10,false))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(
                Category("오락·취미",
                R.drawable.ic_category_entertainment,1,11,false)
            )
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("여행", R.drawable.ic_category_travel,1,12,false))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("자산이동", R.drawable.ic_category_assetmovement,1,13,false))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("기타지출", R.drawable.ic_category_others,1,14,false))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("수입", R.drawable.ic_category_income,2,0,false))
        AppDatabase.getListInstance(context)
            ?.ListDao()?.insert(com.example.client.data.List(1,"2023","01","15","03:47","(주)우아한형제들","10,000","",1,true))
        bankStatementRepository.initCategoryKeywordList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bankStatementRepository : BankStatementRepository = BankStatementRepository(this)
        val categoryDb = AppDatabase.getCategoryInstance(this) // 카테고리 DB
        // 앱 설치 후, 처음 시작한 경우 -> 온보딩 화면으로 이동
        if(categoryDb?.CategoryDao()?.selectAll()?.size!! == 0) {
            insertData(this)
            val intent = Intent(this, OnboardingActivity::class.java)
            startActivity(intent)
            finish()
        }
        // 로그인 안된 경우 -> LoginView로 이동
        // 로그인한 경우 -> BottomNavigationView로 이동
        else{
            println(bankStatementRepository.getListInfo("입출금통지 안내\n" +
                    "***님 11/115 20:27\n" +
                    "60,000,000 FBS출금\n" +
                    "잔액 50,000"))

           // val intent = Intent(this, BottomNavigationActivity::class.java)
            val intent = Intent(this, ListDetailActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}