package com.example.client.ui.setting

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
//import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.client.R
import com.example.client.databinding.ActivitySettingLetterAddInputBinding

class SettingLetterAddInputActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySettingLetterAddInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingLetterAddInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registrationBtn.text = getText(R.string.register_button)

        binding.backButton.setOnClickListener(){
                super.onBackPressed()
        }

    }

}