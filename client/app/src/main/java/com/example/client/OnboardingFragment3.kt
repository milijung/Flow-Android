package com.example.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.client.databinding.FragmentOnboarding3Binding

class OnboardingFragment3 : Fragment() {
    private lateinit var viewBinding: FragmentOnboarding3Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding=FragmentOnboarding3Binding.inflate(layoutInflater)
        return viewBinding.root
    }
}