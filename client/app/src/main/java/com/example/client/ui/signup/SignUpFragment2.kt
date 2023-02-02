package com.example.client.ui.signup

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.client.databinding.FragmentSignUp2Binding


class SignUpFragment2 : Fragment() {
    private lateinit var viewBinding: FragmentSignUp2Binding
    private lateinit var signUpActivity: SignUpActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        signUpActivity = context as SignUpActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding= FragmentSignUp2Binding.inflate(layoutInflater)
        return viewBinding.root
    }
}