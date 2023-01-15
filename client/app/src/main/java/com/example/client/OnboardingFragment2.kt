package com.example.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.client.databinding.FragmentOnboarding2Binding


class OnboardingFragment2 : Fragment() {
    private lateinit var viewBinding: FragmentOnboarding2Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding= FragmentOnboarding2Binding.inflate(layoutInflater)
        return viewBinding.root
    }
}