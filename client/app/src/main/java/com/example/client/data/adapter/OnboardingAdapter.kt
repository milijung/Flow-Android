package com.example.client.data.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.client.ui.onboarding.OnboardingFragment1
import com.example.client.ui.onboarding.OnboardingFragment2
import com.example.client.ui.onboarding.OnboardingFragment3
import kotlinx.coroutines.InternalCoroutinesApi

private const val NUM_TABS = 3
@InternalCoroutinesApi
class OnboardingAdapter(fragmentManager: FragmentManager,lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OnboardingFragment1()
            1 -> OnboardingFragment2()
            2 -> OnboardingFragment3()
            else -> OnboardingFragment1()
        }

    }
}