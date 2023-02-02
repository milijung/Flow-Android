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
        setContentView(viewbinding.root)

        viewbinding.applyBtn.text = getText(R.string.apply_button)

    }
}