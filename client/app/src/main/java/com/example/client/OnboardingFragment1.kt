package com.example.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.client.databinding.FragmentOnboarding1Binding



class OnboardingFragment1 : Fragment() {
    private lateinit var viewBinding: FragmentOnboarding1Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding= FragmentOnboarding1Binding.inflate(layoutInflater)
        return viewBinding.root
    }
}