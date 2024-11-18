package com.example.petmate.ui.myinfo.activity

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
import com.example.petmate.core.util.GlobalUserId
import com.example.petmate.core.network.NetworkClient
import com.example.petmate.databinding.ActivityMyinfPhotoBinding
import com.example.petmate.remote.api.myinfo.MyInfoService
import com.example.petmate.remote.response.myinfo.MyInfoAddPostResponse
import com.example.petmate.ui.myinfo.adapter.MyInfoPhotoAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.InputStream

class MyInfoPhotoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyinfPhotoBinding
    private lateinit var myInfPhotoAdapter: MyInfoPhotoAdapter
    private var imageList: ArrayList<Uri> = ArrayList()
    private lateinit var imageUri: Uri
    private val TAG = "MyinfPhotoActivity123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyinfPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myInfPhotoAdapter = MyInfoPhotoAdapter(imageList, this)
        setupButtonListeners()
    }

    private fun setupButtonListeners() {
        binding.ivPostImage.setOnClickListener { openGallery() }
        binding.btnPostSubmit.setOnClickListener { addPostToServer() }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        activityResult.launch(intent)
    }

    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            it.data?.data?.let { uri ->
                imageUri = uri
                binding.ivPostImage.setImageURI(imageUri)
            }
        }
    }

    private fun addPostToServer() {
        val userIdx = GlobalUserId.getUserId()
        val title = binding.etPostTitle.text.toString()
        val detail = binding.etPostContent.text.toString()

        if (title.isBlank() || detail.isBlank()) {
            Toast.makeText(this, "제목과 내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        val photoString = uriToString(imageUri)

        val retrofit = NetworkClient.getRetrofit()
        val service = retrofit.create(MyInfoService::class.java)
        service.addPost(userIdx, title, detail, photoString).enqueue(object : Callback<MyInfoAddPostResponse> {
            override fun onResponse(call: Call<MyInfoAddPostResponse>, response: Response<MyInfoAddPostResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { result ->
                        if (result.code == 200) {
                            Toast.makeText(applicationContext, "게시글이 등록 되었습니다.", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Log.d(TAG, "onResponse: 데이터 전송 문제 발생")
                        }
                    }
                } else {
                    Log.d(TAG, "onResponse 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MyInfoAddPostResponse>, t: Throwable) {
                Log.d(TAG, "onFailure 에러: ${t.message}")
            }
        })
    }

    private fun uriToString(uri: Uri): String {
        return try {
            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            val bytes = inputStream?.readBytes()
            inputStream?.close()

            bytes?.let {
                val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                val byteArray = byteArrayOutputStream.toByteArray()
                Base64.encodeToString(byteArray, Base64.NO_WRAP)
            } ?: run {
                Log.d(TAG, "uriToString: image load 실패")
                " "
            }
        } catch (e: Exception) {
            Log.e(TAG, "uriToString 예외 발생: ${e.message}")
            " "
        }
    }
}
