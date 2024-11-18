package com.example.petmate.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.petmate.ui.navigation.BottomNavActivity
import com.example.petmate.core.util.GlobalUserId
import com.example.petmate.core.network.NetworkClient
import com.example.petmate.databinding.ActivityLoginMainBinding
import com.example.petmate.remote.api.login.LoginService
import com.example.petmate.remote.response.login.LoginResponse
import com.example.petmate.ui.navigation.BottomNavAnonyActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 로그인 버튼 클릭 이벤트
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPwd.text.toString().trim()

            // 입력 검증
            if (validateInput(email, password)) {
                login(email, password)
            } else {
                Toast.makeText(this, "이메일과 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
            }
        }

        // 회원가입 버튼 클릭 이벤트
        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, LoginRegisterActivity::class.java)
            startActivity(intent)
        }

        // 익명 로그인 버튼 클릭 이벤트
        binding.btnAnonymousLogin.setOnClickListener {
            val intent = Intent(this, BottomNavAnonyActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }

    private fun login(email: String, password: String) {
        val retrofit = NetworkClient.getRetrofit()
        val service = retrofit.create(LoginService::class.java)

        service.login(email, password).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val result: LoginResponse? = response.body()
                    result?.let {
                        when (it.code) {
                            200 -> handleLoginSuccess(it)
                            else -> Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                        }
                    } ?: run {
                        Toast.makeText(applicationContext, "응답 데이터 없음", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    handleLoginError(response)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "네트워크 오류: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("Login00Activity", "onFailure: ${t.message}")
            }
        })
    }

    private fun handleLoginSuccess(result: LoginResponse) {
        Toast.makeText(applicationContext, "로그인 성공", Toast.LENGTH_SHORT).show()
        GlobalUserId.setUserId(result.userId)

        val intent = Intent(applicationContext, BottomNavActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }

    private fun handleLoginError(response: Response<LoginResponse>) {
        val errorMessage = when (response.code()) {
            400 -> "잘못된 요청입니다."
            401 -> "인증에 실패했습니다."
            500 -> "서버 오류입니다. 잠시 후 다시 시도해주세요."
            else -> "알 수 없는 오류가 발생했습니다. 코드: ${response.code()}"
        }
        Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
        Log.e("Login00Activity", "onResponse 실패 - 코드: ${response.code()}, 메시지: ${response.message()}")
    }
}
