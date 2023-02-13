package com.example.client.ui.navigation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.example.client.ui.setting.SettingBudgetSettingActivity
import com.example.client.databinding.FragmentSettingBinding
import com.example.client.ui.category.SettingCategoryActivity
import com.example.client.ui.login.LoginActivity
import com.example.client.ui.modal.SettingInitializationModal
import com.example.client.ui.modal.SettingUserDeleteModal
import com.example.client.ui.setting.SettingBankAppChoiceActivity
import com.example.client.ui.setting.SettingLetterAddRegistraionActivity
import kotlinx.coroutines.InternalCoroutinesApi

//import kotlinx.android.synthetic.main.fragment_setting.*
//import kotlinx.android.synthetic.main.fragment_setting.view.*

@InternalCoroutinesApi
class SettingFragment : Fragment() {
   private lateinit var binding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSettingBinding.inflate(inflater, container, false)

        binding.budgetSettingBtn.setOnClickListener(){
            val intent = Intent(activity, SettingBudgetSettingActivity::class.java)
            intent.putExtra("pageId",3)
            startActivity(intent)
        }

        binding.letterAddRegistrationBtn.setOnClickListener(){
            val intent = Intent(activity, SettingLetterAddRegistraionActivity::class.java)
            startActivity(intent)
        }
        binding.incomeExpenseCategoryManagementBtn.setOnClickListener {
            val intent = Intent(activity, SettingCategoryActivity::class.java)
            startActivity(intent)
        }

        binding.bankAppBtn.setOnClickListener(){
            val intent = Intent(activity, SettingBankAppChoiceActivity::class.java)
            startActivity(intent)
        }

        binding.logoutBtn.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.initDataBtn.setOnClickListener {
            val intent = Intent(activity, SettingInitializationModal::class.java)
            startActivity(intent)
        }

        binding.userDeleteBtn.setOnClickListener {
            val intent = Intent(activity, SettingUserDeleteModal::class.java)
            startActivity(intent)
        }

        binding.budgetWarniningNotificatonBtn.setOnClickListener {
            if(binding.detail.visibility == View.VISIBLE) {
                binding.detail.visibility = View.GONE
                binding.budgetWarniningNotificatonBtn.animate().apply {
                    duration = 300
                }
            } else {
                binding.detail.visibility = View.VISIBLE
                binding.budgetWarniningNotificatonBtn.animate().apply {
                    duration = 300
                }
            }
        }

        binding.seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                userProgress.text = "${progress}%"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

//        binding.fingerprintSwitch.setOnClickListener(){
//            if ()
//        }
        return binding.root
    }
}