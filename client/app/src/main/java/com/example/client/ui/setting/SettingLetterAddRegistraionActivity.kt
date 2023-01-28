package com.example.client.ui.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.client.R
import com.example.client.databinding.ActivitySettingLetterAddInputBinding
import com.example.client.databinding.ActivitySettingLetterAddRegistraionBinding

class SettingLetterAddRegistraionActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingLetterAddRegistraionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingLetterAddRegistraionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.directInputBtn.text = getText(R.string.direct_input_button)
        binding.registraionBtn.text = getText(R.string.register_button)



        binding.directInputBtn.setOnClickListener(){
            val intent = Intent(this, ActivitySettingLetterAddInputBinding::class.java)
            startActivity(intent)
        }
    }
}