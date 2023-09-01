package com.example.petmate.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.petmate.R
import com.example.petmate.databinding.ActivityLogin00Binding
import com.example.petmate.databinding.ActivityLogin10Binding
import com.example.petmate.navigation.BottomNavActivity
import com.google.firebase.auth.FirebaseAuth

class Login10Activity : AppCompatActivity() {

    private lateinit var binding : ActivityLogin10Binding
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin10Binding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener{
            var email = binding.etEmail.text.toString()
            var password = binding.etPwd.text.toString()

            register(email, password)
        }

        binding.activityRegisterBtnBack.setOnClickListener {
            finish()
        }
    }

    fun register(email: String, password: String){
        auth.createUserWithEmailAndPassword(email,password) // 회원 가입
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
            }

    }
}