package com.example.client.ui.setting

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.client.R
import com.example.client.databinding.ActivitySettingBudgetSettingBinding
//import kotlinx.android.synthetic.main.activity_setting_budget_setting.*
//import kotlinx.android.synthetic.main.fragment_bottom_sheet.*

class SettingBudgetSettingActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivitySettingBudgetSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySettingBudgetSettingBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.completBtn.text = getText(R.string.finish_button)


        // 예산시작일 고르는 창
        viewBinding.budgetStartBtn.setOnClickListener {
            val bottomSheet = BottomSheet()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)

        }

        viewBinding.edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val edit : String = Integer.parseInt(p0.toString()).div(10000).toString()+"만 원"
                viewBinding.result.text = edit
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

//        viewBinding.oneBtn.setOnClickListener(View.OnClickListener {
//            result.setText("1만원")
//        })
//
//        viewBinding.fiveBtn.setOnClickListener(View.OnClickListener {
//            result.setText("5만원")
//        })
//
//        viewBinding.tenBtn.setOnClickListener(View.OnClickListener {
//            result.setText("10만원")
//        })
//
//        viewBinding.twentyBtn.setOnClickListener(View.OnClickListener {
//            result.setText("20만원")
//        })
//
//        viewBinding.thirtyBtn.setOnClickListener(View.OnClickListener {
//            result.setText("30만원")
//        })
    }
}
