package com.example.client.ui.signup

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationManagerCompat
import com.example.client.databinding.FragmentSignUp2Binding


class SignUpFragment2 : Fragment() {
    private lateinit var viewBinding: FragmentSignUp2Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding= FragmentSignUp2Binding.inflate(inflater,container,false)
        return viewBinding.root
    }
}