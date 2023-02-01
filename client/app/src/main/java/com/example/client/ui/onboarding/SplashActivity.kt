package com.example.client.ui.onboarding

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.example.client.R
import com.example.client.data.AppDatabase
import com.example.client.data.BankStatementRepository
import com.example.client.data.Category
import com.example.client.data.List
import com.example.client.ui.board.ListDetailActivity
import com.example.client.ui.category.SettingCategoryActivity
import com.example.client.ui.navigation.BottomNavigationActivity


class SplashActivity : AppCompatActivity() {
    // DB 초기값 insert, 나중에 수정해야 함
    private fun insertData(context: Context) {
        val bankStatementRepository : BankStatementRepository = BankStatementRepository(this)
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("하루세끼", R.drawable.ic_category_food,1,0,false,1))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("주거·통신", R.drawable.ic_category_living,1,1,false,2))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("교통관련", R.drawable.ic_category_traffic,1,2,false,3))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("생필품", R.drawable.ic_category_market,1,3,false,4))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("나를 위한", R.drawable.ic_category_formeexpense,1,4,false,5))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("선물준비", R.drawable.ic_category_present,1,5,false,6))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(
                Category("자기계발",
                R.drawable.ic_category_selfimprovement,1,6,false,7)
            )
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("카페·간식", R.drawable.ic_category_cafe,1,7,false,8))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("저축", R.drawable.ic_category_saving,1,8,false,9))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("술·외식", R.drawable.ic_category_alchol,1,9,false,10))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("의료·건강", R.drawable.ic_category_medical,1,10,false,11))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(
                Category("오락·취미",
                R.drawable.ic_category_entertainment,1,11,false,12)
            )
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("여행", R.drawable.ic_category_travel,1,12,false,13))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("자산이동", R.drawable.ic_category_assetmovement,1,13,false,14))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("기타지출", R.drawable.ic_category_others,1,14,false,15))
        AppDatabase.getCategoryInstance(context)
            ?.CategoryDao()?.insert(Category("수입", R.drawable.ic_category_income,2,0,false,16))
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

            // 내역 파싱 함수 체크
            val list : List = bankStatementRepository.getListInfo("[KB]06/02 22:10\n" +
                    "017002**621 \n" +
                    "카카오택시\n" +
                    "전자금융출금\n" +
                    "24,000\n" +
                    "잔액 500,000")

            println("날짜 -> "+list.year+"년 "+list.month+"월 "+list.day+"일")
            println("시간 -> "+list.time)
            println("내역 타입(지출:1, 수입:2) -> "+list.typeId.toString())
            println("가격 -> "+list.price)
            println("거래처 -> "+list.shop)
            println("카테고리 -> "+categoryDb.CategoryDao().selectById(list.categoryId).name)

           // val intent = Intent(this, BottomNavigationActivity::class.java)
            val intent = Intent(this, BottomNavigationActivity::class.java)
            startActivity(intent)
            finish()
        } //OnboardingActivity SettingCategoryActivity BottomNavigationActivity
    }
}