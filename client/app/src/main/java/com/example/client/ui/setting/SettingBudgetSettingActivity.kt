package com.example.client.ui.setting

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.client.databinding.ActivitySettingBudgetSettingBinding
import kotlinx.android.synthetic.main.fragment_bottom_sheet.*

class SettingBudgetSettingActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivitySettingBudgetSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySettingBudgetSettingBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // 예산시작일 고르는 창
        viewBinding.budgetStartBtn.setOnClickListener {
            val bottomSheet = BottomSheet()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)

            startday_picker.minValue = 1
            startday_picker.maxValue = 31

            // 날짜 순환 기능
            startday_picker.wrapSelectorWheel = false


        }

        viewBinding.oneBtn.setOnClickListener(View.OnClickListener {
            val resultText = viewBinding.edit.text.toString()
            viewBinding.result.setText(resultText)
        })

        viewBinding.fiveBtn.setOnClickListener(View.OnClickListener {
            val resultText = viewBinding.edit.text.toString()
            viewBinding.result.setText(resultText)

        })

        viewBinding.tenBtn.setOnClickListener(View.OnClickListener {
            val resultText = viewBinding.edit.text.toString()
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


//        viewBinding.budgetStartBtn.setOnClickListener{
//            // bottomSheetDialog 객체 생성
//            val bottomSheetDialog = BottomSheetDialog(
//                this@SettingBudgetSettingActivity, R.style.BottomSheetDialogTheme
//            )
//            // layout_bottom_sheet를 뷰 객체로 생성
//            val bottomSheetView = LayoutInflater.from(applicationContext).inflate(
//                R.layout.a
//                findViewById(R.id.) as LinearLayout?
//            )
//            // bottomSheetDialog의 dismiss 버튼 선택시 dialog disappear
//            bottomSheetView.viewBinding.setOnClickListener {
//                bottomSheetDialog.dismiss()
//            }
//            // bottomSheetDialog 뷰 생성
//            bottomSheetDialog.setContentView(bottomSheetView)
//            // bottomSheetDialog 호출
//            bottomSheetDialog.show()
//        }

    }
}

