package com.example.client.ui.setting

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.client.R
import com.example.client.api.BudgetRequest
import com.example.client.api.HttpConnection
import com.example.client.data.AppDatabase
import com.example.client.databinding.ActivitySettingBudgetSettingBinding
import kotlinx.coroutines.InternalCoroutinesApi
//import kotlinx.android.synthetic.main.activity_setting_budget_setting.*
//import kotlinx.android.synthetic.main.fragment_bottom_sheet.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SettingBudgetSettingActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivitySettingBudgetSettingBinding
    private val httpConnection = HttpConnection()
    lateinit var bottomSheet : BottomSheet

    @OptIn(InternalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySettingBudgetSettingBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val roomDatabase = AppDatabase.getInstance(this)
        val userId = roomDatabase!!.UserDao().getUserId()

        viewBinding.completBtn.text = getText(R.string.finish_button)

        viewBinding.backButton.setOnClickListener(){
            super.onBackPressed()
        }

        // 예산시작일 고르는 창
        viewBinding.budgetStartBtn.setOnClickListener {
            bottomSheet = BottomSheet()
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

        viewBinding.oneBtn.setOnClickListener(View.OnClickListener {
            viewBinding.result.setText("1만원")
        })

        viewBinding.fiveBtn.setOnClickListener(View.OnClickListener {
            viewBinding.result.setText("5만원")
        })

        viewBinding.tenBtn.setOnClickListener(View.OnClickListener {
            viewBinding.result.setText("10만원")
        })

        viewBinding.twentyBtn.setOnClickListener(View.OnClickListener {
            viewBinding.result.setText("20만원")
        })

        viewBinding.thirtyBtn.setOnClickListener(View.OnClickListener {
            viewBinding.result.setText("30만원")
        })

        viewBinding.completBtn.setOnClickListener{
            httpConnection.updateBudget(this, userId, BudgetRequest(Integer.parseInt(viewBinding.edit.text.toString()),bottomSheet.getBudgetStartDate()) )
        }


    }
}

