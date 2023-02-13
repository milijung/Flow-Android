package com.example.client.ui.login

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.client.databinding.ActivityLoginBinding

import com.example.client.R
import com.example.client.api.HttpConnection
import com.example.client.data.AppDatabase
import com.example.client.databinding.FragmentHomeBinding
import com.example.client.ui.board.ListDetailActivity
import com.example.client.ui.category.AddCategoryActivity
import com.example.client.ui.category.SettingCategoryActivity
import com.example.client.ui.navigation.HomeFragment
import com.example.client.ui.signup.SignUpActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val TAG = this.javaClass.simpleName
    private lateinit var GoogleSignResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var roomDb : AppDatabase
    private val httpConnection = HttpConnection()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val kakao = binding.kakaoLogin
        val naver = binding.naverLogin
        val google = binding.googleLogin
        roomDb = AppDatabase.getInstance(this)!!

        binding.signupButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        // kakao login
        kakao.setOnClickListener {
            // 카카오계정으로 로그인 공통 callback 구성
            // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Toast.makeText(this, "카카오계정 로그인에 실패했습니다\n     나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()

                } else if (token != null) {
                    Toast.makeText(this, "성공적으로 로그인되었습니다", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, SettingCategoryActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()
                }
            }

            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    if (error != null) {

                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (뒤로가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                    } else if (token != null) {
                        Toast.makeText(this, "성공적으로 로그인되었습니다", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, SettingCategoryActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                        finish()
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }

        //naver login
        naver.setOnClickListener {
            val oAuthLoginCallback = object : OAuthLoginCallback {
                override fun onSuccess() {
                    // 네이버 로그인 API 호출 성공 시 유저 정보를 가져오기
                    NidOAuthLogin().callProfileApi(object :
                        NidProfileCallback<NidProfileResponse> {
                        override fun onSuccess(result: NidProfileResponse) {
                            val userId = Integer.parseInt(result.profile?.id)
                            httpConnection.getUserInfo(this@LoginActivity, roomDb!!, userId)
                            httpConnection.getCategory(this@LoginActivity,roomDb, userId)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            Toast.makeText(this@LoginActivity, "성공적으로 로그인되었습니다", Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                            finish()
                        }

                        override fun onError(errorCode: Int, message: String) {
                            Toast.makeText(this@LoginActivity, "사용자 정보를 받아오지 못했습니다\n      나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                        }

                        override fun onFailure(httpStatus: Int, message: String) {
                            Toast.makeText(this@LoginActivity, "사용자 정보를 받아오지 못했습니다\n      나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                        }
                    })
                }

                override fun onError(errorCode: Int, message: String) {
                    Toast.makeText(this@LoginActivity, "네이버계정 로그인에 실패했습니다\n     나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(httpStatus: Int, message: String) {
                    Toast.makeText(this@LoginActivity, "네이버계정 로그인에 실패했습니다\n     나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                }
            }

            NaverIdLoginSDK.initialize(this, "xr2COSDB3sry_QeeLe9F", "cbJYHJ1bEY", "Flow")
            NaverIdLoginSDK.authenticate(this, oAuthLoginCallback)
        }

        //google login
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_client_id))
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this,gso)

//        val account = GoogleSignIn.getLastSignedInAccount(this)
//        if (account == null) {
//            Log.e("Google account", "로그인 안되있음")
//        } else {
//            Log.e("Google account", "로그인 완료된 상태")
//        }

        GoogleSignResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){ result ->
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        }

        google.setOnClickListener {
            var signIntent: Intent = mGoogleSignInClient.getSignInIntent()
            GoogleSignResultLauncher.launch(signIntent)
        }

    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val userId = Integer.parseInt(account?.id)
            httpConnection.getUserInfo(this@LoginActivity, roomDb, userId)
            httpConnection.getCategory(this@LoginActivity,roomDb, userId)
            Toast.makeText(this@LoginActivity, "성공적으로 로그인되었습니다", Toast.LENGTH_SHORT).show()
        } catch (e: ApiException){
            Toast.makeText(this, "구글계정 로그인에 실패했습니다\n    나중에 다시 시도해주세요", Toast.LENGTH_SHORT).show()
        }
    }

}