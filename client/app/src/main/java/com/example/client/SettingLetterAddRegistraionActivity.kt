package com.example.client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.client.databinding.ActivitySettingLetterAddRegistraionBinding

class SettingLetterAddRegistraionActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingLetterAddRegistraionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}