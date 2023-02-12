package com.example.client.ui.modal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.client.api.HttpConnection
import com.example.client.data.AppDatabase
import com.example.client.databinding.ModalSettingUserDeleteBinding
import kotlinx.coroutines.InternalCoroutinesApi

class SettingUserDeleteModal : AppCompatActivity() {
    private lateinit var binding :  ModalSettingUserDeleteBinding
    private val httpConnection = HttpConnection()

    @OptIn(InternalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ModalSettingUserDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val roomDatabase = AppDatabase.getInstance(this)
        val userId = roomDatabase!!.UserDao().getUserId()

        binding.userDeleteBtn.setOnClickListener {
            httpConnection.allDataDelete(this, userId)
        }

    }
}