package com.example.client.ui.navigation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.client.SettingBudgetSettingActivity
import com.example.client.databinding.FragmentSettingBinding
import kotlinx.android.synthetic.main.fragment_setting.*


class SettingFragment : Fragment() {
   private lateinit var viewBinding: FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentSettingBinding.inflate(layoutInflater)
        return viewBinding.root

        viewBinding.budgetSettingBtn.setOnClickListener(){
            val intent = Intent(activity, SettingBudgetSettingActivity::class.java)
        }
    }


}