package com.example.petmate.login

import android.content.Intent
import android.net.Uri
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

private val convertList=listOf("[개] 골든 리트리버","[개] 그레이 하운드","[개] 그레이트 피레니즈","[개] 닥스훈트","[개] 달마시안","[개] 도베르만","도사","[개] 라브라도 리트리버","[고양이] 러시안 블루","[고양이] 레그돌",
"[개] 로트와일러","[개] 말라뮤트","[개] 말티즈","[개] 미니어쳐 핀셔","[개] 베들링턴 테리어","[고양이] 벵갈","[개] 보더 콜리","[개] 보스턴 테리어","[개] 불독","[고양이] 브리티시 쇼트헤어",
"[개] 비글","[개] 비숑 프리제","[개] 사모예드","[개] 삽살개","[개] 샤페이","[고양이] 샴","[개] 셰퍼드","[개] 슈나우져","[고양이] 스코티시폴드","[개] 스피츠",
"[고양이] 스핑크스","[개] 시바","[개] 시베리안 허스키","[개] 시츄","[개] 요크셔 테리어","[개] 웰시 코기","[개] 진도견","[개] 차우차우","[개] 치와와","[개] 코카 스파니엘",
"[개] 푸들","[개] 퍼그","[고양이] 페르시안","[개] 포메라니안","[개] 프렌치 불독")

class Login10Activity : AppCompatActivity() {

    private lateinit var binding: ActivityLogin10Binding
    private var petImage: Uri? = null
    private var tfliteresult:String=""


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
                Toast.makeText(this, "반려동물 유무를 선택해주세요.", Toast.LENGTH_SHORT).show()
            } else if(!binding.btnLoginPetowner.isSelected && tfliteresult==""){
                Toast.makeText(this, "반려동물 없음 클릭 후 사진을 선택해주세요.", Toast.LENGTH_SHORT).show()
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
                            service.postCreateUser(name, email, password,tfliteresult).enqueue(object : Callback<LoginInterfaceResponse> {

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
            val test= result.data!!.getStringExtra("result")
            tfliteresult = convertList[test!!.split(" ")[0].toInt()]
            Toast.makeText(this, tfliteresult, Toast.LENGTH_SHORT).show()
        } else {
            Log.d("petImage", "not selected")
            binding.btnLoginPetowner.isSelected = false
            binding.btnLoginPetseeker.isSelected = false
            petImage = null
        }
    }
}