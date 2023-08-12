package com.example.petmate.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.petmate.navigation.BottomNavActivity
import com.example.petmate.databinding.ActivityLogin00Binding

class Login00Activity : AppCompatActivity() {

    private lateinit var binding : ActivityLogin00Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin00Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            Log.d("ryu","D")
            val intent = Intent(this, BottomNavActivity::class.java)
            startActivity(intent)

        }
    }
}