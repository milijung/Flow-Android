package com.example.client.ui.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.client.R
import com.example.client.databinding.FragmentBottomSheetBinding
import com.example.client.databinding.FragmentSettingBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheet : BottomSheetDialogFragment() {
    private lateinit var viewBinding: FragmentBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = FragmentBottomSheetBinding.inflate(layoutInflater)

c

        viewBinding.startdayPicker.minValue = 1
        viewBinding.startdayPicker.maxValue = 31

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