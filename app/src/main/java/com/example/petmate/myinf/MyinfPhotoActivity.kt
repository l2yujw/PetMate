package com.example.petmate.myinf

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.petmate.R
import com.example.petmate.databinding.ActivityBottomNavBinding
import com.example.petmate.databinding.ActivityMyinfPhotoBinding
import java.io.File

class MyinfPhotoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyinfPhotoBinding

    lateinit var myInfPhotoAdapter: MyinfPhotoAdapter

    var imageList: ArrayList<Uri> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyinfPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myInfPhotoAdapter = MyinfPhotoAdapter(imageList, this)

        //recyclerView 설정
        binding.rcvMyinfPost.layoutManager = LinearLayoutManager(this)
        binding.rcvMyinfPost.adapter = myInfPhotoAdapter

        //버튼 이벤트
        binding.btnPost.setOnClickListener {

            //갤러리 호출
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            //멀티 선택 기능
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            activityResult.launch(intent)
            binding.btnPost.visibility = View.INVISIBLE
        }

        binding.btnAddPost.setOnClickListener{
            finish()
        }
    }

    //결과 가져오기
    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){

        //결과 코드 OK , 결가값 null 아니면
        if(it.resultCode == RESULT_OK){

            //멀티 선택은 clipData
            if(it.data!!.clipData != null){ //멀티 이미지

                //선택한 이미지 갯수
                val count = it.data!!.clipData!!.itemCount

                for(index in 0 until count){
                    //이미지 담기
                    val imageUri = it.data!!.clipData!!.getItemAt(index).uri
                    //이미지 추가
                    imageList.add(imageUri)
                }
            }else{ //싱글 이미지
                val imageUri = it.data!!.data
                imageList.add(imageUri!!)
            }
            myInfPhotoAdapter.notifyDataSetChanged()
        }
    }

}