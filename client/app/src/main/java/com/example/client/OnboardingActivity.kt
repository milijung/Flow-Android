package com.example.client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.client.databinding.ActivityOnboardingBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_onboarding.*


class OnboardingActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding= ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val vpAdapter=OnboardingAdapter(supportFragmentManager, lifecycle)

        viewBinding.viewPager.adapter=vpAdapter

        TabLayoutMediator(viewBinding.tabDot, viewBinding.viewPager) { tab, position ->

        }.attach()


    }
}