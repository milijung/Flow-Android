package com.example.client.ui.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.client.data.model.AppDatabase
import com.example.client.ui.setting.SettingBudgetSettingActivity
import com.example.client.databinding.FragmentSettingBinding
import com.example.client.ui.category.SettingCategoryActivity
import com.example.client.ui.login.LoginActivity
import com.example.client.ui.modal.SettingInitializationModal
import com.example.client.ui.modal.SettingUserDeleteModal
import com.example.client.ui.setting.SettingBankAppChoiceActivity
import com.example.client.ui.setting.SettingLetterAddRegistraionActivity
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class SettingFragment : Fragment() {
   private lateinit var binding: FragmentSettingBinding
    private lateinit var bottomNavigationActivity : BottomNavigationActivity
    private lateinit var roomDb : AppDatabase
    override fun onAttach(context: Context) {
        super.onAttach(context)
        bottomNavigationActivity = context as BottomNavigationActivity
        roomDb = AppDatabase.getInstance(bottomNavigationActivity)!!
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val userId = roomDb.UserDao().getUserId()
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        binding.seekBar.value = roomDb.UserDao().getUserInfo().budgetAlarmPercent.toFloat()
        binding.userProgress.text = "${ binding.seekBar.value }%"
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
            roomDb.UserDao().deleteById(userId)
            roomDb.CategoryDao().deleteCategoryAll()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            bottomNavigationActivity.finish()
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

        binding.seekBar.addOnChangeListener { slider, value, fromUser ->
            binding.userProgress.text = "${value.toInt()}%"
            roomDb.UserDao().updateBudgetAlarmPercent(userId,value.toInt())
        }

//        binding.fingerprintSwitch.setOnClickListener(){
//            if ()
//        }
        return binding.root
    }
}