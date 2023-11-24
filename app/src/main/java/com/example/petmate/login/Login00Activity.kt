package com.example.petmate.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.petmate.databinding.ActivityLogin00Binding
import com.example.petmate.navigation.BottomNavActivity
import com.example.petmate.GlobalUserIdx
import com.example.petmate.Tool
import com.example.petmate.navigation.BottomNavAnonyActivity
import retrofit2.Call
import retrofit2.Callback



// Tool -> Firebase -> Authentication -> 아무거나 클릭후 add
class Login00Activity : AppCompatActivity() {

    private lateinit var binding : ActivityLogin00Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin00Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPwd.text.toString()

            //테스트 용 email:admin / password:1234
            login(email,password)

        }

        binding.btnRegister.setOnClickListener{
            val intent = Intent(this, Login10Activity::class.java)
            startActivity(intent)
        }

        binding.btnAnonymousLogin.setOnClickListener{
            val intent = Intent(this, BottomNavAnonyActivity::class.java)
            startActivity(intent)
        }

    }

    private fun login(email:String, password:String){

        val retrofit = Tool.getRetrofit()

        val service = retrofit.create(LoginInterface::class.java)

        service.getLogin(email,password).enqueue(object : Callback<LoginInterfaceResponse> {

            override fun onResponse(call: Call<LoginInterfaceResponse>, response: retrofit2.Response<LoginInterfaceResponse>) {
                if(response.isSuccessful){
                    // 정상적으로 통신이 성고된 경우
                    val result: LoginInterfaceResponse? = response.body()

                    when (result?.code) {
                        200 -> {
                            Toast.makeText(applicationContext, "로그인 성공", Toast.LENGTH_SHORT).show()
                            val intent = Intent(applicationContext, BottomNavActivity::class.java)

                            GlobalUserIdx.setUserIdx(result.userIdx)

                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)

                        }
                        201 -> {
                            //Toast.makeText(applicationContext, result.message, Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            //Toast.makeText(applicationContext, "ㅈ버그발생 보내는 데이터가 문제임", Toast.LENGTH_SHORT).show()
                        }
                    }

                }else{
                    // 통신이 실패한 경우(응답 코드 3xx, 4xx 등)
                    Toast.makeText(applicationContext, "Code: "+response.code(), Toast.LENGTH_SHORT).show()
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
}