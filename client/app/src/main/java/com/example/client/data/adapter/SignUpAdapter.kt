package com.example.client.data.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.client.ui.signup.*

private const val NUM_TABS = 6

class SignUpAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SignUpFragment1()
            1 -> SignUpFragment2()
            2 -> SignUpFragment3()
            3 -> SignUpFragment4()
            4 -> SignUpFragment5()
            5 -> SignUpFragment6()
            else -> SignUpFragment1()
        }

    }
}