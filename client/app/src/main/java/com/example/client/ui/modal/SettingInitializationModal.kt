package com.example.client.ui.modal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.client.databinding.ModalSettingInitializationBinding

class SettingInitializationModal : AppCompatActivity() {
    private lateinit var binding: ModalSettingInitializationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ModalSettingInitializationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}