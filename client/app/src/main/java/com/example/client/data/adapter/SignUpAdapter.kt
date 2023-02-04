package com.example.client.data.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.client.ui.signup.*

private const val NUM_TABS = 5

class SignUpAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SignUpFragment2()
            1 -> SignUpFragment3()
            2 -> SignUpFragment4()
            3 -> SignUpFragment5()
            4 -> SignUpFragment6()
            else -> SignUpFragment2()
        }

    }
}