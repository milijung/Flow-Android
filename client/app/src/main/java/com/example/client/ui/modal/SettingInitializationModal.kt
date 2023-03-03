package com.example.client.ui.modal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.client.api.HttpConnection
import com.example.client.data.model.AppDatabase
import com.example.client.databinding.ModalSettingInitializationBinding
import kotlinx.coroutines.InternalCoroutinesApi

class SettingInitializationModal : AppCompatActivity() {
    private lateinit var binding: ModalSettingInitializationBinding
    private val httpConnection = HttpConnection()

    @OptIn(InternalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ModalSettingInitializationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val roomDatabase = AppDatabase.getInstance(this)
        val userId = roomDatabase!!.UserDao().getUserId()

        binding.modalDelete.setOnClickListener {
            httpConnection.allDataDelete(this, userId)
        }
    }
}