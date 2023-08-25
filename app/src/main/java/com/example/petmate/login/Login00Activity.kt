package com.example.petmate.login

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import com.example.petmate.navigation.BottomNavActivity
import com.example.petmate.databinding.ActivityLogin00Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import net.daum.mf.map.api.MapView
import java.security.MessageDigest

// Tool -> Firebase -> Authentication -> 아무거나 클릭후 add
class Login00Activity : AppCompatActivity() {
    private val TAG = "SOL_LOG"

    private lateinit var binding : ActivityLogin00Binding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin00Binding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            var email = binding.etEmail.text.toString()
            var password = binding.etPwd.text.toString()
            login(email, password)
        }

        binding.btnRegister.setOnClickListener{
            var intent = Intent(this, Login10Activity::class.java)
            startActivity(intent)
        }

    }

    fun login(email:String,password:String){
        auth.signInWithEmailAndPassword(email,password) // 로그인
            .addOnCompleteListener {
                    result->
                if(result.isSuccessful){
                    var intent = Intent(this, BottomNavActivity::class.java)
                    startActivity(intent)
                }
            }
    }
}