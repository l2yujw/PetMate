package com.example.petmate.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.petmate.ui.login.LoginMainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToLogin(2.5) // 2.5초 후에 로그인 화면으로 이동
    }

    private fun navigateToLogin(delayInSeconds: Double) {
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginMainActivity::class.java)
            startActivity(intent)
            finish() // 현재 액티비티 종료
        }, (delayInSeconds * 1000).toLong())
    }
}
