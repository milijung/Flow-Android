package com.example.client.ui.onboarding

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.example.client.R
import com.example.client.data.AppDatabase
import com.example.client.data.Category
import com.example.client.ui.board.ListDetailActivity


class SplashActivity : AppCompatActivity() {
    // DB 초기값 insert, 나중에 수정해야 함
    private fun insertData(context: Context) {
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

        val categoryDb = AppDatabase.getCategoryInstance(this) // 카테고리 DB
        if(categoryDb?.CategoryDao()?.selectAll()?.size!! == 0)
            insertData(this)

        val intent = Intent(this, OnboardingActivity::class.java)
        startActivity(intent)
        finish()
    }
}