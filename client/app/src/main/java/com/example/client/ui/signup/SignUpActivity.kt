package com.example.client.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.SeekBar
import androidx.viewpager2.widget.ViewPager2
import com.example.client.data.adapter.OnboardingAdapter
import com.example.client.data.adapter.SignUpAdapter
import com.example.client.databinding.ActivitySignUpBinding
import com.example.client.ui.login.LoginActivity
import com.example.client.ui.navigation.BottomNavigationActivity
import com.google.android.material.tabs.TabLayoutMediator

class SignUpActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        /*
        supportFragmentManager
            .beginTransaction()
            .replace(viewBinding.signupFragment.id, SignUpFragment2())
            .commitAllowingStateLoss()

         */



        val vpAdapter = SignUpAdapter(supportFragmentManager, lifecycle)
        var pageIndex = viewBinding.signupFragment.currentItem
        viewBinding.signupFragment.adapter = vpAdapter
        viewBinding.seekBar.isEnabled = false //seekBar를 사용자가 못 움직이게 하기


        // 페이지 이동(스와이프)
        viewBinding.signupFragment.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                pageIndex = position
                viewBinding.seekBar.progress = pageIndex + 1 //상단바 움직이게 함
            }
        })


        // 페이지 이동(버튼)
        viewBinding.signupButton2.setOnClickListener() {
            pageIndex = viewBinding.signupFragment.currentItem
            //viewBinding.signupButton2.text = getText(buttonTextElement[pageIndex])
            when (pageIndex) {
                4 -> goBottomNavigationActivity()
                else -> viewBinding.signupFragment.setCurrentItem(pageIndex + 1, true)
            }
        }


        //상단바 숫자 바꾸기, 움직이기
        viewBinding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener
        {
            override fun onProgressChanged(seekBar: SeekBar?, p1: Int, p2: Boolean) {

                val value = seekBar?.progress
                viewBinding.stepNumber.text = "$value"
                if(value!=0 && value!=5) {
                    viewBinding.stepNumber.visibility=View.VISIBLE

                    val cur = seekBar!!.width/seekBar.max

                    viewBinding.stepNumber.x = (cur * value!!).toFloat()-20
                }
                else if(value==5){
                    viewBinding.stepNumber.visibility=View.GONE
                }

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                TODO("Not yet implemented")
            }


        })


    }

    //바텀 네비게이션 액티비티로 이동

    fun goBottomNavigationActivity(){
        val intent = Intent(this, BottomNavigationActivity::class.java)
        startActivity(intent)
    }
}