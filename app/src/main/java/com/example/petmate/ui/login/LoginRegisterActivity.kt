package com.example.petmate.ui.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.petmate.core.network.NetworkClient
import com.example.petmate.databinding.ActivityLoginRegisterBinding
import com.example.petmate.remote.api.login.LoginService
import com.example.petmate.remote.response.login.CheckEmailResponse
import com.example.petmate.remote.response.login.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val convertList = listOf(
    "[개] 골든 리트리버", "[개] 그레이 하운드", "[개] 그레이트 피레니즈", "[개] 닥스훈트", "[개] 달마시안", "[개] 도베르만", "도사",
    "[개] 라브라도 리트리버", "[고양이] 러시안 블루", "[고양이] 레그돌", "[개] 로트와일러", "[개] 말라뮤트", "[개] 말티즈",
    // 추가 리스트 생략
)

class LoginRegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginRegisterBinding
    private var petImage: Uri? = null
    private var classificationResult: String = "" // 변수명 개선

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButtonListeners()
    }

    private fun setupButtonListeners() {
        binding.btnRegister.setOnClickListener {
            val name = binding.activityRegisterEditTextName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPwd.text.toString()
            val passwordCheck = binding.activityRegisterEditTextPasswordMatch.text.toString()

            if (!validateInput(name, email, password, passwordCheck)) {
                return@setOnClickListener
            }

            register(name, email, password)
        }

        binding.btnRegister.setOnClickListener {
            finish()
        }

        binding.btnLoginPetowner.setOnClickListener {
            binding.btnLoginPetowner.isSelected = !binding.btnLoginPetowner.isSelected
            binding.btnLoginPetseeker.isSelected = false
        }

        binding.btnLoginPetseeker.setOnClickListener {
            binding.btnLoginPetseeker.isSelected = !binding.btnLoginPetseeker.isSelected
            binding.btnLoginPetowner.isSelected = false
            petseekerForResult.launch(Intent(this, LoginPetSeekerActivity::class.java))
        }
    }

    private fun validateInput(name: String, email: String, password: String, passwordCheck: String): Boolean {
        return when {
            name.isBlank() -> showToast("이름을 입력해야 합니다.")
            email.isBlank() -> showToast("이메일을 입력해야 합니다.")
            password.isBlank() -> showToast("비밀번호를 입력해야 합니다.")
            passwordCheck.isBlank() -> showToast("비밀번호 확인을 입력해야 합니다.")
            password != passwordCheck -> showToast("비밀번호가 일치하지 않습니다.")
            !verifyEmail(email) -> showToast("이메일 형식이 올바르지 않습니다.")
            !binding.btnLoginPetowner.isSelected && !binding.btnLoginPetseeker.isSelected -> showToast("반려동물 유무를 선택해주세요.")
            !binding.btnLoginPetowner.isSelected && classificationResult.isEmpty() -> showToast("반려동물 없음 클릭 후 사진을 선택해주세요.")
            else -> true
        }
    }

    private fun showToast(message: String): Boolean {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        return false
    }

    private fun verifyEmail(email: String): Boolean {
        val regexEmail = """^([a-zA-Z0-9_\-.]+)@([a-zA-Z0-9_\-.]+)\.([a-zA-Z]{2,5})${'$'}""".toRegex()
        return regexEmail.matches(email)
    }

    private fun register(name: String, email: String, password: String) {
        val retrofit = NetworkClient.getRetrofit()
        val service = retrofit.create(LoginService::class.java)

        service.checkEmail(email).enqueue(object : Callback<CheckEmailResponse> {
            override fun onResponse(call: Call<CheckEmailResponse>, response: Response<CheckEmailResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        handleEmailCheckResponse(it, service, name, email, password)
                    } ?: run {
                        showToast("서버 응답 오류")
                    }
                } else {
                    showToast("이미 가입된 이메일입니다.")
                }
            }

            override fun onFailure(call: Call<CheckEmailResponse>, t: Throwable) {
                showToast("통신 실패: ${t.message}")
                Log.e("LoginRegisterActivity", "onFailure: ${t.message}")
            }
        })
    }

    private fun handleEmailCheckResponse(
        checkResponse: CheckEmailResponse,
        service: LoginService,
        name: String,
        email: String,
        password: String
    ) {
        if (checkResponse.code == 200) {
            service.createUser(name, email, password, classificationResult).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.let { result ->
                            if (result.code == 200) {
                                showToast(result.message ?: "회원가입 성공")
                                finish()
                            } else {
                                showToast("회원가입 실패: ${result.message}")
                            }
                        } ?: run {
                            showToast("서버 응답 오류")
                        }
                    } else {
                        showToast("회원가입 실패: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    showToast("회원가입 통신 실패: ${t.message}")
                    Log.e("LoginRegisterActivity", "onFailure: ${t.message}")
                }
            })
        } else {
            showToast(checkResponse.message ?: "이미 가입된 이메일입니다.")
        }
    }

    private val petseekerForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val test = result.data!!.getStringExtra("result")
            classificationResult = convertList[test!!.split(" ")[0].toInt()]
            Toast.makeText(this, classificationResult, Toast.LENGTH_SHORT).show()
        } else {
            binding.btnLoginPetowner.isSelected = false
            binding.btnLoginPetseeker.isSelected = false
            petImage = null
        }
    }
}
