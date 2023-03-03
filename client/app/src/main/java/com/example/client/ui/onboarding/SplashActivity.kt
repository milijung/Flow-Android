package com.example.client.ui.onboarding

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.example.client.data.model.AppDatabase
import com.example.client.data.BankStatementRepository
import com.example.client.ui.login.LoginActivity
import com.example.client.ui.navigation.BottomNavigationActivity
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class SplashActivity : AppCompatActivity() {
    // DB 초기값 insert, 나중에 수정해야 함
    private fun insertData(context: Context) {
        val bankStatementRepository : BankStatementRepository = BankStatementRepository(this)
        bankStatementRepository.initCategoryKeywordList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bankStatementRepository : BankStatementRepository = BankStatementRepository(this)
        val roomDb = AppDatabase.getInstance(this) // 카테고리 DB
        // 앱 설치 후, 처음 시작한 경우 -> 온보딩 화면으로 이동
        when {
            roomDb?.CategoryDao()?.selectAll()?.size!! == 0 -> {
                insertData(this)
                val intent = Intent(this, OnboardingActivity::class.java)
                startActivity(intent)
                finish()
            }
            // 로그인 안된 경우 -> LoginView로 이동
            roomDb.UserDao().isLogin()==0 -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            // 로그인한 경우 -> BottomNavigationView로 이동
            else -> {
                val intent = Intent(this, BottomNavigationActivity::class.java)
                startActivity(intent)
                finish()
            }
        } //OnboardingActivity SettingCategoryActivity BottomNavigationActivity
    }
}