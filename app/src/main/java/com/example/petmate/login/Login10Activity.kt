package com.example.petmate.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.petmate.databinding.ActivityLogin10Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Login10Activity : AppCompatActivity() {

    private lateinit var binding : ActivityLogin10Binding
    //lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin10Binding.inflate(layoutInflater)
        setContentView(binding.root)

        //auth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener{
            val name = binding.activityRegisterEditTextName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPwd.text.toString()
            val passwordcheck = binding.activityRegisterEditTextPasswordMatch.text.toString()


            if(name.isBlank()){
                Toast.makeText(this, "이름을 입력해야합니다.", Toast.LENGTH_SHORT).show()
            }else if(email.isBlank()){
                Toast.makeText(this, "이메일을 입력해야합니다.", Toast.LENGTH_SHORT).show()
            }else if(password.isBlank()){
                Toast.makeText(this, "비밀번호를 입력해야합니다.", Toast.LENGTH_SHORT).show()
            }else if(passwordcheck.isBlank()) {
                Toast.makeText(this, "비밀번호 확인을 입력해야합니다.", Toast.LENGTH_SHORT).show()
            }else if(password != passwordcheck){
                Toast.makeText(this, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
            }else if(!verifyEmail(email)){
                Toast.makeText(this, "이메일 형식을 확인해주세요.", Toast.LENGTH_SHORT).show()
            }else{
                register(name,email,password)
            }

        }

        binding.activityRegisterBtnBack.setOnClickListener {
            finish()
        }
    }

    private fun verifyEmail(email: String) : Boolean {
        val regexEmail = """^([a-zA-Z0-9_\-.]+)@([a-zA-Z0-9_\-.]+)\.([a-zA-Z]{2,5})${'$'}""".toRegex()
        return regexEmail.matches(email)
    }
    private fun register(name:String, email: String, password: String){
        /*auth.createUserWithEmailAndPassword(email,password) // 회원 가입
            .addOnCompleteListener {
                    result ->
                if(result.isSuccessful){
                    Toast.makeText(this,"회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    if(auth.currentUser!=null){
                        finish()
                    }
                }
                else if(result.exception?.message.isNullOrEmpty()){
                    Toast.makeText(this,"오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this,"이미 존재하는 계정이거나, 회원가입에 실패했습니다.",Toast.LENGTH_SHORT).show()
                }
            }*/
        val retrofit = Retrofit.Builder()
            .baseUrl("http://13.124.16.204:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        //

        val service = retrofit.create(LoginInterface::class.java);

        service.getCheckEmail(email).enqueue(object : Callback<CheckEmailResponse> {

            override fun onResponse(call: Call<CheckEmailResponse>, response: retrofit2.Response<CheckEmailResponse>) {
                var isPossible = false
                var isResponse = false
                if(response.isSuccessful){
                    // 정상적으로 통신이 성고된 경우
                    val result: CheckEmailResponse? = response.body()
                    Log.d("Login1", "onResponse 성공: " + result?.toString());

                    when (result?.code) {
                        200 -> {
                            //message = '사용 가능한 이메일입니다.';

                            //유저 추가 코드
                            service.postCreateUser(name,email,password).enqueue(object : Callback<LoginInterfaceResponse> {

                                override fun onResponse(call: Call<LoginInterfaceResponse>, response: retrofit2.Response<LoginInterfaceResponse>) {
                                    if(response.isSuccessful){
                                        // 정상적으로 통신이 성고된 경우
                                        val result2: LoginInterfaceResponse? = response.body()
                                        Log.d("Login1", "onResponse 성공: " + result2?.toString());

                                        if(result2?.code == 200){
                                            //message = '회원가입에 성공했습니다.';
                                            Toast.makeText(applicationContext, result2.message, Toast.LENGTH_SHORT).show()
                                            finish()
                                        }else{
                                            Toast.makeText(applicationContext, "ㅈ버그발생 보내는 데이터가 문제임", Toast.LENGTH_SHORT).show()
                                        }

                                    }else{
                                        Toast.makeText(applicationContext, "Code: "+response.code(), Toast.LENGTH_SHORT).show()
                                        Log.d("Login1", "onResponse 실패")
                                    }
                                }

                                override fun onFailure(call: Call<LoginInterfaceResponse>, t: Throwable) {
                                    // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                                    Toast.makeText(applicationContext, "통신 실패", Toast.LENGTH_SHORT).show()
                                    Log.d("Login1", "onFailure 에러: " + t.message.toString());
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

                }else{
                    Toast.makeText(applicationContext, "Code: "+response.code(), Toast.LENGTH_SHORT).show()
                    Log.d("Login1", "onResponse 실패")
                }
            }

            override fun onFailure(call: Call<CheckEmailResponse>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Toast.makeText(applicationContext, "통신 실패", Toast.LENGTH_SHORT).show()
                Log.d("Login1", "onFailure 에러: " + t.message.toString());
            }
        })

    }

}