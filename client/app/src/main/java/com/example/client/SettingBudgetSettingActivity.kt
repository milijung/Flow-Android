package com.example.client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.client.databinding.ActivitySettingBudgetSettingBinding

class SettingBudgetSettingActivity : AppCompatActivity() {

    private lateinit var viewBinding : ActivitySettingBudgetSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySettingBudgetSettingBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.oneBtn.setOnClickListener(View.OnClickListener {
            var resultText = viewBinding.edit.text.toString()
            viewBinding.result.setText(resultText)
        })

        viewBinding.fiveBtn.setOnClickListener(View.OnClickListener {
            var resultText = viewBinding.edit.text.toString()
            viewBinding.result.setText(resultText)

        })

        viewBinding.tenBtn.setOnClickListener(View.OnClickListener {
            var resultText = viewBinding.edit.text.toString()
            viewBinding.result.setText(resultText)

        })

        viewBinding.twentyBtn.setOnClickListener(View.OnClickListener {
            val resultText = viewBinding.edit.text.toString()
            viewBinding.result.setText(resultText)

        })

        viewBinding.thirtyBtn.setOnClickListener(View.OnClickListener {
            val resultText = viewBinding.edit.text.toString()
            viewBinding.result.setText(resultText)

        })
    }

}