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
import java.lang.StrictMath.round

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
        val user = roomDatabase!!.UserDao().getUserInfo()

        viewBinding.completBtn.text = getText(R.string.finish_button)
        viewBinding.edit.setText(user.budget.toString())
        viewBinding.result.text = "${user.budget.div(10000)}만원"
        viewBinding.backButton.setOnClickListener(){
            super.onBackPressed()
        }

        // 예산시작일 고르는 창
        viewBinding.budgetStartBtn.setOnClickListener {
            bottomSheet = BottomSheet(user.budgetStartDay)
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        viewBinding.edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString() == ""){
                    viewBinding.result.text = ""
                }else{
                    val edit : Int = Integer.parseInt(p0.toString()).div(10000)
                    viewBinding.result.text = "${edit}만원"
                }

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        viewBinding.oneBtn.setOnClickListener(View.OnClickListener {
            viewBinding.edit.setText("10000")
            viewBinding.result.text = "1만원"
        })

        viewBinding.fiveBtn.setOnClickListener(View.OnClickListener {
            viewBinding.edit.setText("50000")
            viewBinding.result.text = "5만원"
        })

        viewBinding.tenBtn.setOnClickListener(View.OnClickListener {
            viewBinding.edit.setText("100000")
            viewBinding.result.text = "10만원"
        })

        viewBinding.twentyBtn.setOnClickListener(View.OnClickListener {
            viewBinding.edit.setText("200000")
            viewBinding.result.text = "20만원"
        })

        viewBinding.thirtyBtn.setOnClickListener(View.OnClickListener {
            viewBinding.edit.setText("300000")
            viewBinding.result.text = "30만원"
        })

        viewBinding.completBtn.setOnClickListener{
            if(viewBinding.edit.text.toString() == "")
                Toast.makeText(this,"예산을 입력해주세요",Toast.LENGTH_SHORT).show()
            else
                httpConnection.updateBudget(this, roomDatabase, user.userId, BudgetRequest(Integer.parseInt(viewBinding.edit.text.toString()),bottomSheet.getBudgetStartDate()))

        }


    }
}

