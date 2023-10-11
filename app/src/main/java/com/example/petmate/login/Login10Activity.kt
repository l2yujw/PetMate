package com.example.petmate.login

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.petmate.Tool
import com.example.petmate.databinding.ActivityLogin10Binding
import retrofit2.Call
import retrofit2.Callback


class Login10Activity : AppCompatActivity() {

    private lateinit var binding: ActivityLogin10Binding
    private var petImage: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin10Binding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnRegister.setOnClickListener {
            val name = binding.activityRegisterEditTextName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPwd.text.toString()
            val passwordcheck = binding.activityRegisterEditTextPasswordMatch.text.toString()

            if (name.isBlank()) {
                Toast.makeText(this, "이름을 입력해야합니다.", Toast.LENGTH_SHORT).show()
            } else if (email.isBlank()) {
                Toast.makeText(this, "이메일을 입력해야합니다.", Toast.LENGTH_SHORT).show()
            } else if (password.isBlank()) {
                Toast.makeText(this, "비밀번호를 입력해야합니다.", Toast.LENGTH_SHORT).show()
            } else if (passwordcheck.isBlank()) {
                Toast.makeText(this, "비밀번호 확인을 입력해야합니다.", Toast.LENGTH_SHORT).show()
            } else if (password != passwordcheck) {
                Toast.makeText(this, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
            } else if (!verifyEmail(email)) {
                Toast.makeText(this, "이메일 형식을 확인해주세요.", Toast.LENGTH_SHORT).show()
            } else if (!binding.btnLoginPetowner.isSelected && !binding.btnLoginPetseeker.isSelected) {
                Toast.makeText(this, "반려동물 유무를 선택해 주세요.", Toast.LENGTH_SHORT).show()
            } else {
                register(name, email, password/*,petowner*/)    // TODO petowner 변수는 반려동물이 있으면 true, 없으면 false. 관심동물 이미지는 전역변수인 petImage: Uri?로 할당.
            }

        }

        binding.activityRegisterBtnBack.setOnClickListener {
            finish()
        }

        binding.btnLoginPetowner.setOnClickListener {
            if (!binding.btnLoginPetowner.isSelected) {
                binding.btnLoginPetowner.isSelected = true
                binding.btnLoginPetseeker.isSelected = false
            } else {
                binding.btnLoginPetowner.isSelected = false
                binding.btnLoginPetseeker.isSelected = false
            }
        }

        binding.btnLoginPetseeker.setOnClickListener {
            if (!binding.btnLoginPetseeker.isSelected) {
                binding.btnLoginPetowner.isSelected = false
                binding.btnLoginPetseeker.isSelected = true

                petseekerForResult.launch(Intent(this, Login10PetseekerActivity::class.java))
            }
        }
    }

    private fun verifyEmail(email: String): Boolean {
        val regexEmail = """^([a-zA-Z0-9_\-.]+)@([a-zA-Z0-9_\-.]+)\.([a-zA-Z]{2,5})${'$'}""".toRegex()
        return regexEmail.matches(email)
    }

    private fun register(name: String, email: String, password: String) {

        val retrofit = Tool.getRetrofit()

        val service = retrofit.create(LoginInterface::class.java)

        service.getCheckEmail(email).enqueue(object : Callback<CheckEmailResponse> {

            override fun onResponse(call: Call<CheckEmailResponse>, response: retrofit2.Response<CheckEmailResponse>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    val result: CheckEmailResponse? = response.body()
                    Log.d("Login1", "onResponse 성공: " + result?.toString())

                    when (result?.code) {
                        200 -> {
                            service.postCreateUser(name, email, password).enqueue(object : Callback<LoginInterfaceResponse> {

                                override fun onResponse(call: Call<LoginInterfaceResponse>, response: retrofit2.Response<LoginInterfaceResponse>) {
                                    if (response.isSuccessful) {
                                        // 정상적으로 통신이 성고된 경우
                                        val result2: LoginInterfaceResponse? = response.body()
                                        Log.d("Login1", "onResponse 성공: " + result2?.toString())

                                        if (result2?.code == 200) {
                                            Toast.makeText(applicationContext, result2.message, Toast.LENGTH_SHORT).show()
                                            finish()
                                        } else {
                                            Toast.makeText(applicationContext, "ㅈ버그발생 보내는 데이터가 문제임", Toast.LENGTH_SHORT).show()
                                        }

                                    } else {
                                        Toast.makeText(applicationContext, "Code: " + response.code(), Toast.LENGTH_SHORT).show()
                                        Log.d("Login1", "onResponse 실패")
                                    }
                                }

                                override fun onFailure(call: Call<LoginInterfaceResponse>, t: Throwable) {
                                    // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                                    Toast.makeText(applicationContext, "통신 실패", Toast.LENGTH_SHORT).show()
                                    Log.d("Login1", "onFailure 에러: " + t.message.toString())
                                }
                            })

                        }

                        201 -> {
                            //message = '이미 가입된 이메일입니다.';
                            Toast.makeText(applicationContext, result.message, Toast.LENGTH_SHORT).show()
                        }

                        else -> {
                            Toast.makeText(applicationContext, "ㅈ버그발생 보내는 데이터가 문제임", Toast.LENGTH_SHORT).show()
                        }
                    }

                } else {
                    Toast.makeText(applicationContext, "Code: " + response.code(), Toast.LENGTH_SHORT).show()
                    Log.d("Login1", "onResponse 실패")
                }
            }

            override fun onFailure(call: Call<CheckEmailResponse>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Toast.makeText(applicationContext, "통신 실패", Toast.LENGTH_SHORT).show()
                Log.d("Login1", "onFailure 에러: " + t.message.toString())
            }
        })

    }

    private val petseekerForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        Log.d("petImage", "data transformed")
        if (result.resultCode == RESULT_OK) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                petImage = intent.getParcelableExtra("petImage", Uri::class.java)
                Log.d("petImage", "selected")
            } else {
                petImage = intent.getParcelableExtra("petImage")
                Log.d("petImage", "selected")
            }
        } else {
            Log.d("petImage", "not selected")
            binding.btnLoginPetowner.isSelected = false
            binding.btnLoginPetseeker.isSelected = false
            petImage = null
        }
    }
}