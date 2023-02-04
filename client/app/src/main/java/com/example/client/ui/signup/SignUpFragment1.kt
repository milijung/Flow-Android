package com.example.client.ui.signup

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.client.databinding.FragmentSignUp1Binding


class SignUpFragment1 : Fragment(){
    private lateinit var viewBinding: FragmentSignUp1Binding
    private var setPageMover : SetPageMover? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is SetPageMover){
            setPageMover = context
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding= FragmentSignUp1Binding.inflate(inflater,container,false)
        // 카카오 로그인
        viewBinding.signupKakao.setOnClickListener {
            setPageMover?.goNextFragment()
        }
        // 네이버 로그인
        viewBinding.signupNaver.setOnClickListener {
            setPageMover?.goNextFragment()
        }
        // 구글 로그인
        viewBinding.signupGoogle.setOnClickListener {
            setPageMover?.goNextFragment()
        }
        return viewBinding.root
    }
    interface SetPageMover{
        fun goNextFragment()
    }
}