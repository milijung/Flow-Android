package com.example.client.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.R
import androidx.appcompat.app.AlertDialog
import com.example.client.databinding.ActivitySettingBudgetSettingBinding
import com.example.client.databinding.DialogBudgetStartdayBinding
import kotlinx.android.synthetic.main.activity_setting_budget_setting.*

class SettingBudgetSettingActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivitySettingBudgetSettingBinding

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

        viewBinding.budgetStartBtn.setOnClickListener {

        }

//        var selectNum = 0
//
//        budgetStartBtn.setOnClickListener {
//            val layout = layoutInflater.inflate(R.layout.dialog_budget_startday, null)
//            val build = AlertDialog.Builder(it.context).apply {
//                setView(layout)
//            }
//            val dialog = build.create()
//            dialog.show()
//
//            layout.startday_picker.minValue = 1
//            layout.startday_picker.maxValue = 31
//            if (selectNum != 0) layout.startday_picker.value = selectNum
//
//            layout.choose_btn.setOnClickListener {
//                selectNum = layout.startday_picker.value
//                dialog.dismiss()
//            }
//
//        }
    }
}

