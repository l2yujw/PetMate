package com.example.petmate.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.petmate.ui.login.Login00Activity

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        moveMain(2.5) //1초 후 main activity 로 넘어감
    }

    private fun moveMain(sec: Double) {
        Handler().postDelayed(Runnable {
            //new Intent(현재 context, 이동할 activity)
            val intent = Intent(applicationContext, Login00Activity::class.java)
            startActivity(intent) //intent 에 명시된 액티비티로 이동
            finish() //현재 액티비티 종료
        }, (1000 * sec).toLong()) // sec초 정도 딜레이를 준 후 시작
    }
}