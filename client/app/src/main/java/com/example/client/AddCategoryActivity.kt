package com.example.client

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.client.databinding.ActivityAddCategoryBinding

class AddCategoryActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityAddCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityAddCategoryBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.addCategoryButton.text = getText(R.string.add_category_button)
        viewBinding.addCategoryType.check(R.id.addCategory_type1)
        viewBinding.addCategoryType.setOnCheckedChangeListener{ radioGroup, checkedId ->
            when(checkedId){
                R.id.addCategory_type1 ->{
                    viewBinding.addCategoryType.check(R.id.addCategory_type1)
                }
                R.id.addCategory_type2 -> {
                    viewBinding.addCategoryType.check(R.id.addCategory_type2)
                }
            }
        }
    }
}