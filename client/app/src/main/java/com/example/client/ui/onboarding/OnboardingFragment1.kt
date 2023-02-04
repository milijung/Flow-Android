package com.example.client.ui.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.client.databinding.FragmentOnboarding1Binding
import com.example.client.ui.onboarding.OnboardingActivity


class OnboardingFragment1 : Fragment() {
    private lateinit var viewBinding: FragmentOnboarding1Binding
    private lateinit var onBoardingActivity : OnboardingActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onBoardingActivity = context as OnboardingActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding= FragmentOnboarding1Binding.inflate(inflater, container, false)
        return viewBinding.root
    }
}