package com.example.client.ui.signup

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.client.R
import com.example.client.api.BudgetRequest
import com.example.client.api.HttpConnection
import com.example.client.data.AppDatabase
import com.example.client.databinding.ActivitySettingBudgetSettingBinding
import com.example.client.databinding.FragmentSignUp1Binding
import com.example.client.databinding.FragmentSignUp5Binding
import com.example.client.ui.navigation.BottomNavigationActivity
import com.example.client.ui.setting.BottomSheet
import kotlinx.coroutines.InternalCoroutinesApi

class SignUpFragment5 : Fragment() {
    private lateinit var viewBinding: FragmentSignUp5Binding
    private lateinit var bottomSheet : BottomSheet
//    private lateinit var bottomNavigationActivity : BottomNavigationActivity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            viewBinding = FragmentSignUp5Binding.inflate(inflater,container,false)
            bottomSheet = BottomSheet(1)

            viewBinding.edit.setText("200000")
            viewBinding.result.text = "${200000.div(10000)}만원"

            // 예산시작일 고르는 창
            viewBinding.budgetStartBtn.setOnClickListener {
                bottomSheet.show(requireFragmentManager(), bottomSheet.tag)
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

        return viewBinding.root
    }
    fun isBudgetNull(): Boolean{
        return when(viewBinding.edit.text.toString()){
            ""-> true
            else -> false
        }
    }
    fun getBudgetInfo() : BudgetRequest{
        return BudgetRequest(Integer.parseInt(viewBinding.edit.text.toString()), bottomSheet.getBudgetStartDate())
    }
}