package com.example.client.ui.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import com.example.client.R
import com.example.client.databinding.FragmentBottomSheetBinding
import com.example.client.databinding.FragmentSettingBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottom_sheet.*


class BottomSheet : BottomSheetDialogFragment() {
    private lateinit var viewBinding: FragmentBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //setupnumberpickervalue()

        viewBinding = FragmentBottomSheetBinding.inflate(layoutInflater)
        viewBinding.chooseBtn.text = getText(R.string.select_button)

        viewBinding.startdayPicker.minValue = 1
        viewBinding.startdayPicker.maxValue = 28

        // numberpicker에 string 넣기
//        val values = arrayOf("1", "2", "3", )
//        viewBinding.startdayPicker.displayedValues = values


        // 날짜 순환 기능
        viewBinding.startdayPicker.wrapSelectorWheel = false

        viewBinding.closeBtn.setOnClickListener{
            dismiss()
        }
        viewBinding.chooseBtn.setOnClickListener {
            dismiss()
        }
        return viewBinding.root

    }


}