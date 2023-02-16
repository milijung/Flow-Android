package com.example.client.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.example.client.R
import com.example.client.api.BudgetRequest
import com.example.client.api.HttpConnection
import com.example.client.data.AppDatabase
import com.example.client.data.User
import com.example.client.data.adapter.SignUpAdapter
import com.example.client.databinding.ActivitySignUpBinding
import com.example.client.databinding.FragmentSignUp5Binding
import com.example.client.ui.navigation.BottomNavigationActivity
import kotlinx.coroutines.InternalCoroutinesApi
import kotlin.properties.Delegates
@InternalCoroutinesApi
class SignUpActivity : AppCompatActivity(), SignUpFragment1.SetPageMover{
    private lateinit var viewBinding: ActivitySignUpBinding
    private val httpConnection  = HttpConnection()
    private var pageIndex by Delegates.notNull<Int>()
    private val buttonTextElement : List<Int> = listOf(
        R.string.go_to_setting,
        R.string.next_button,
        R.string.next_button,
        R.string.next_button,
        R.string.finish_signup_button,
    )
    private val button1Element : List<Boolean> = listOf(
        false,
        true,
        true,
        false,
        false
    )
    private val passButtonElement : List<Boolean> = listOf(
        true,
        true,
        true,
        false,
        false
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val vpAdapter = SignUpAdapter(supportFragmentManager, lifecycle)
        pageIndex = viewBinding.signupFragment.currentItem
        viewBinding.signupFragment.adapter = vpAdapter
        val roomDb = AppDatabase.getInstance(this)

        // 스와이프 이벤트 제거
        viewBinding.seekBar.isEnabled = false
        viewBinding.signupFragment.run{
            isUserInputEnabled = false
        }

        // 페이지 이동(버튼)
        viewBinding.signupButton2.setOnClickListener() {
            when (pageIndex) {
                5 -> goBottomNavigationActivity()
                4 -> {
                    val userId = 1
                    if((supportFragmentManager.fragments[2] as SignUpFragment5).isBudgetNull())
                        Toast.makeText(this,"예산을 입력해주세요", Toast.LENGTH_SHORT).show()
                    else {
                        val budgetInfo = (supportFragmentManager.fragments[2] as SignUpFragment5).getBudgetInfo()
                        httpConnection.getCategory(this, roomDb!!, userId)
                        httpConnection.updateBudget(
                            this,
                            roomDb,
                            -1,
                            userId,
                            budgetInfo
                        )
                        goNextFragment()
                    }
                }
                else -> goNextFragment()
            }
        }
        viewBinding.signupButton1.setOnClickListener {
            goNextFragment()
        }

        // 건너뛰기
        viewBinding.signupPassButton.setOnClickListener {
            goNextFragment()
        }

        // 뒤로가기
        viewBinding.signupBackButton.setOnClickListener {
            goPreviousFragment()
        }
        //상단바 숫자 바꾸기, 움직이기
        viewBinding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener
        {
            override fun onProgressChanged(seekBar: SeekBar?, p1: Int, p2: Boolean) {
                val value = seekBar?.progress
                viewBinding.stepNumber.text = value.toString()
                when(value){
                    0,5 ->viewBinding.stepNumber.visibility=View.GONE
                    else ->{
                        viewBinding.stepNumber.visibility=View.VISIBLE
                        val cur = seekBar!!.width/seekBar.max
                        viewBinding.stepNumber.x = (cur * value!!).toFloat()-20
                    }
                }
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {

            }
            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })
    }

    override fun goNextFragment(){
        viewBinding.seekBar.progress = pageIndex + 1
        viewBinding.signupFragment.setCurrentItem(pageIndex + 1, true)
        viewBinding.signupButton2.setText(buttonTextElement[pageIndex])
        viewBinding.buttonContainer.visibility = View.VISIBLE
        when(button1Element[pageIndex]){
            true -> viewBinding.signupButton1.visibility = View.VISIBLE
            else -> viewBinding.signupButton1.visibility = View.GONE
        }
        when(passButtonElement[pageIndex]){
            true -> viewBinding.signupPassButton.visibility = View.VISIBLE
            else -> viewBinding.signupPassButton.visibility = View.GONE
        }
        pageIndex += 1
    }

    private fun goPreviousFragment(){
        viewBinding.seekBar.progress = pageIndex - 1
        viewBinding.signupFragment.setCurrentItem(pageIndex - 1, true)
        when(pageIndex){
            0 -> super.onBackPressed()
            1 -> {
                viewBinding.buttonContainer.visibility = View.GONE
                viewBinding.signupPassButton.visibility = View.GONE
            }
            else -> {
                viewBinding.buttonContainer.visibility = View.VISIBLE
                viewBinding.signupButton2.setText(buttonTextElement[pageIndex-2])
                when(button1Element[pageIndex-2]){
                    true -> viewBinding.signupButton1.visibility = View.VISIBLE
                    else -> viewBinding.signupButton1.visibility = View.GONE
                }
                when(passButtonElement[pageIndex-2]){
                    true -> viewBinding.signupPassButton.visibility = View.VISIBLE
                    else -> viewBinding.signupPassButton.visibility = View.GONE
                }
            }
        }
        pageIndex -= 1
    }
    //바텀 네비게이션 액티비티로 이동
    private fun goBottomNavigationActivity(){
        val intent = Intent(this, BottomNavigationActivity::class.java)
        startActivity(intent)
    }

}