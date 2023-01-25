package com.example.client.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.client.R
import com.example.client.databinding.ActivitySettingBankAppChoiceBinding
import com.example.client.databinding.ActivitySettingLetterAddInputBinding

class SettingBankAppChoiceActivity : AppCompatActivity() {

    private lateinit var viewbinding: ActivitySettingBankAppChoiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewbinding = ActivitySettingBankAppChoiceBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_setting_bank_app_choice)
    }
}