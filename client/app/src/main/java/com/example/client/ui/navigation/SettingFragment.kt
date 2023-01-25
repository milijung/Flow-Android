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
import com.example.client.ui.setting.SettingBankAppChoiceActivity
import com.example.client.ui.setting.SettingLetterAddRegistraionActivity
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
            val intent = Intent(getActivity(), SettingBudgetSettingActivity::class.java)
            startActivity(intent)
        }

        viewBinding.letterAddRegistrationBtn.setOnClickListener(){
            val intent = Intent(getActivity(), SettingLetterAddRegistraionActivity::class.java)
            startActivity(intent)
        }

        viewBinding.bankAppBtn.setOnClickListener(){
            val intent = Intent(getActivity(), SettingBankAppChoiceActivity::class.java)
            startActivity(intent)
        }

        viewBinding.budgetWarningNotification.setOnClickListener {
            if(viewBinding.detail.visibility == View.VISIBLE) {
                viewBinding.detail.visibility = View.GONE
                viewBinding.budgetWarniningNotificatonBtn.animate().apply {
                    duration = 300
                    rotation(0f)
                }
            } else {
                viewBinding.detail.visibility = View.VISIBLE
                viewBinding.budgetWarniningNotificatonBtn.animate().apply {
                    duration = 300
                    rotation(180f)
                }
            }
        }

        seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                userProgress.text = "$progress%"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
    }


}