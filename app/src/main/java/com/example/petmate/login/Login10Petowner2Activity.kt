package com.example.petmate.login

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import com.example.petmate.R



class Login10Petowner2Activity : Activity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.item_login_petowner)

        setContent()
    }

    /*//확인 버튼 클릭
    fun onConpleteClicked(v: View?) {
        //데이터 전달하기
        val intent = Intent()
        intent.putExtra("result", "Close Popup")
        setResult(RESULT_OK, intent)

        //액티비티(팝업) 닫기
        finish()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        //바깥레이어 클릭시 안닫히게
        return if (event.action == MotionEvent.ACTION_OUTSIDE) {
            false
        } else true
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        //안드로이드 백버튼 막기
        return
    }*/

    private fun setContent() {
        val mConfirm = findViewById<View>(R.id.btn_login_perowner_complete)
        mConfirm.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_login_perowner_complete -> finish()
            else -> {}
        }
    }
}