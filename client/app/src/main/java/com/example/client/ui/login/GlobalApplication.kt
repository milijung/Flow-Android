package com.example.client.ui.login
import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // KaKao SDK 초기화
        KakaoSdk.init(this, "a176cb9610d769c4c9ca064705633b70")
        // 네이버 SDK 초기화
        NaverIdLoginSDK.initialize(this,"xr2COSDB3sry_QeeLe9F","cbJYHJ1bEY","Flow")
    }
}