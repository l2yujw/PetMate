package com.example.petmate.ui.myinf.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.petmate.core.util.GlobalUserIdx
import com.example.petmate.core.network.Tool
import com.example.petmate.databinding.ActivityMyinfPhotoBinding
import com.example.petmate.remote.api.myinf.MyinfInterface
import com.example.petmate.remote.response.myinf.MyinfaddPostInterfaceResponse
import com.example.petmate.ui.myinf.adapter.MyinfPhotoAdapter
import retrofit2.Call
import retrofit2.Callback
import java.io.ByteArrayOutputStream
import java.io.InputStream

class MyinfPhotoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyinfPhotoBinding

    private lateinit var myInfPhotoAdapter: MyinfPhotoAdapter

    private var imageList: ArrayList<Uri> = ArrayList()

    private lateinit var imageUri:Uri

    private val TAG = "MyinfPhotoActivity123"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyinfPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myInfPhotoAdapter = MyinfPhotoAdapter(imageList, this)

        //버튼 이벤트
        binding.imageviewPost.setOnClickListener {

            //갤러리 호출
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            activityResult.launch(intent)
        }

        binding.btnAddPost.setOnClickListener {
            val userIdx = GlobalUserIdx.getUserIdx()
            val title = binding.editPostTitle.text.toString()
            val detail = binding.editPostDetail.text.toString()

            val phototoString = uriToString(imageUri)

            val retrofit = Tool.getRetrofit()

            val service = retrofit.create(MyinfInterface::class.java)
            service.addPost(userIdx,title,detail,phototoString).enqueue(object : Callback<MyinfaddPostInterfaceResponse> {

                override fun onResponse(call: Call<MyinfaddPostInterfaceResponse>, response: retrofit2.Response<MyinfaddPostInterfaceResponse>) {
                    if (response.isSuccessful) {
                        // 정상적으로 통신이 성고된 경우
                        val result: MyinfaddPostInterfaceResponse? = response.body()
                        Log.d(TAG, "PopularList onResponse 성공: " + result?.toString())

                        when (result?.code) {
                            200 -> {
                                Toast.makeText(applicationContext, "게시글이 등록 되었습니다.", Toast.LENGTH_SHORT).show()
                                finish()
                            }

                            else -> {
                                Log.d(TAG, "onResponse: ㅈ버그발생 보내는 데이터가 문제임 ")
                            }
                        }

                    } else {
                        // 통신이 실패한 경우(응답 코드 3xx, 4xx 등)
                        Log.d(TAG, "onResponse 실패" + response.code())
                    }
                }

                override fun onFailure(call: Call<MyinfaddPostInterfaceResponse>, t: Throwable) {
                    // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                    Log.d(TAG, "onFailure 에러: " + t.message.toString())
                }
            })
        }
    }

    //결과 가져오기
    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        //결과 코드 OK , 결가값 null 아니면
        if (it.resultCode == RESULT_OK) {

            imageUri = it.data!!.data!!

            binding.imageviewPost.setImageURI(imageUri)
        }
    }

    private fun uriToString(uri:Uri): String {
        val inputStream: InputStream? = applicationContext.contentResolver.openInputStream(uri)
        val bytes: ByteArray? = inputStream?.readBytes()
        inputStream?.close()

        return if (bytes != null) {
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            Base64.encodeToString(byteArray, Base64.NO_WRAP)
        }else{
            Log.d(TAG, "uriToString: image load 실패")
            " "
        }
    }
}