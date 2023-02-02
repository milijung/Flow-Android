package com.example.client.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.client.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        supportFragmentManager
            .beginTransaction()
            .replace(viewBinding.signupFragment.id, SignUpFragment2())
            .commitAllowingStateLoss()
    }
}