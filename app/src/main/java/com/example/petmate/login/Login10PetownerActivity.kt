package com.example.petmate.login

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.petmate.R
import com.example.petmate.databinding.ItemLoginPetownerBinding

class Login10PetownerActivity(private val context: AppCompatActivity) {

    private lateinit var binding: ItemLoginPetownerBinding
    private lateinit var listener: petownerDialogCompleteClickedListener
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감

    fun show() {
        binding = ItemLoginPetownerBinding.inflate(LayoutInflater.from(context))

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(binding.root)     //다이얼로그에 사용할 xml 파일 로드
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함
        dlg.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dlg.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)


        //ok 버튼 동작
        binding.btnLoginPerownerComplete.setOnClickListener {

            //TODO: 부모 액티비티로 내용을 돌려주기 위해 작성할 코드

            listener.onConpleteClicked("확인을 눌렀습니다")

            dlg.dismiss()
        }

        dlg.show()
    }

    fun setOnCompleteClickedListener(listener: (String) -> Unit) {
        this.listener = object : petownerDialogCompleteClickedListener {
            override fun onConpleteClicked(content: String) {
                listener(content)
            }
        }
    }

    interface petownerDialogCompleteClickedListener {
        fun onConpleteClicked(content: String)
    }

}