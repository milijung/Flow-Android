package com.example.client.ui.setting

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
//import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.client.databinding.ActivitySettingLetterAddInputBinding

class SettingLetterAddInputActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySettingLetterAddInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingLetterAddInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //에딧텍스트 생성 버튼
//        binding.addBtn.setOnClickListener{
//           createEditText()
//        }
    }

//    private fun createEditText(){
//
//        //에딧텍스트 생성
//        val editText:EditText = EditText(applicationContext)
//
//        //에딧텍스트 힌트글자
//        editText.hint = "입출금 문자를 복사하여 입력해주세요"
//
//        editText.textSize = 16f
//
//        editText.id = 0
//
//        val param: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.MATCH_PARENT,
//            LinearLayout.LayoutParams.WRAP_CONTENT )
//
//        param.leftMargin = 20
//
//        editText.layoutParams = param
//
//        editText.setTextColor(Color.GRAY)
//    }
}