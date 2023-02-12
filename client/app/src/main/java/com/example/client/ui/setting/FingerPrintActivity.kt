package com.example.client.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.client.R
import com.example.client.databinding.ActivityFingerPrintBinding
import java.util.concurrent.Executor


private lateinit var executor: Executor
private lateinit var biometricPrompt: BiometricPrompt
private lateinit var promptInfo: BiometricPrompt.PromptInfo

class FingerPrintActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFingerPrintBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFingerPrintBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fingerprintBtn.text = getText(R.string.fingerprint_button)

        executor = ContextCompat.getMainExecutor(this@FingerPrintActivity)
        biometricPrompt = BiometricPrompt(this@FingerPrintActivity, executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int,
                                                       errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        Toast.makeText(applicationContext, "에러", Toast.LENGTH_SHORT).show()
                    }

                    override fun onAuthenticationSucceeded(
                            result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        Toast.makeText(applicationContext, "성공", Toast.LENGTH_SHORT).show()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        Toast.makeText(applicationContext, "실패", Toast.LENGTH_SHORT).show()
                    }
                })

        // 프롬포트 창 내용
        promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("지문 인증")
                .setSubtitle("기기에 등록된 지문을 이용하여 지문을 인증해주세요.")
                .setNegativeButtonText("취소")
                .build()

        // 버튼 클릭 시 지문인식
        binding.fingerprintBtn.setOnClickListener{
            biometricPrompt.authenticate(promptInfo)
        }

    }
}