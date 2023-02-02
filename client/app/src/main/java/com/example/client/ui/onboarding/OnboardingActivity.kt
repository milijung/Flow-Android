package com.example.client.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationManagerCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.client.R
import com.example.client.data.OnboardingAdapter
import com.example.client.databinding.ActivityOnboardingBinding
import com.example.client.ui.login.LoginActivity
import com.google.android.material.tabs.TabLayoutMediator


class OnboardingActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityOnboardingBinding
    fun isNotificationPermissionAllowed(): Boolean {
        return NotificationManagerCompat.getEnabledListenerPackages(applicationContext)
            .any { enabledPackageName ->
                enabledPackageName == packageName
            }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isNotificationPermissionAllowed())
            startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        /**
         * Notification 접근 권한 체크 메서드
         * @return 접근권한이 있을 경우 true, 아니면 false
         */
        viewBinding= ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        val buttonTextElement : List<Int> = listOf(
            R.string.next_button,
            R.string.next_button,
            R.string.start_button
        )
        val vpAdapter= OnboardingAdapter(supportFragmentManager, lifecycle)
        var pageIndex = viewBinding.onboardFragment.currentItem
        viewBinding.onboardFragment.adapter=vpAdapter
        TabLayoutMediator(viewBinding.tabDot, viewBinding.onboardFragment, true, true) { tab, position ->
        }.attach()

        // 페이지 이동 버튼 text 설정
        viewBinding.onboardButton.text = getText(buttonTextElement[pageIndex])

        // 건너뛰기 버튼
        viewBinding.onboardPassButton.setOnClickListener(){
            goLoginActivity()
        }
        // 페이지 이동 (버튼)
        viewBinding.onboardButton.setOnClickListener(){
            pageIndex = viewBinding.onboardFragment.currentItem
            viewBinding.onboardButton.text = getText(buttonTextElement[pageIndex])
            when(pageIndex){
                2 -> goLoginActivity()
                else -> viewBinding.onboardFragment.setCurrentItem(pageIndex + 1, true)
            }
        }
        // 페이지 이동 (스와이프)
        viewBinding.onboardFragment.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                pageIndex = position
                viewBinding.onboardButton.text = getText(buttonTextElement[pageIndex])
            }
        })
    }
    // 로그인 화면으로 이동
    fun goLoginActivity(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
