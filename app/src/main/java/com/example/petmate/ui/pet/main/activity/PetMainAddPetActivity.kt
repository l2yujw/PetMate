package com.example.petmate.ui.pet.main.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.petmate.core.util.GlobalUserId
import com.example.petmate.R
import com.example.petmate.core.network.NetworkClient
import com.example.petmate.databinding.ActivityPetMainAddPetBinding
import com.example.petmate.remote.api.pet.main.PetMainService
import com.example.petmate.remote.response.pet.main.PetMainDetailCreateResponse
import com.example.petmate.remote.response.pet.main.PetMainCreateResponse
import retrofit2.Call
import retrofit2.Callback
import java.io.ByteArrayOutputStream
import java.io.InputStream

class PetMainAddPetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPetMainAddPetBinding
    private val TAG = "PetMainAddPetActivity123"
    private lateinit var imageURI: Uri
    private var insertpetIdx = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetMainAddPetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //버튼 이벤트
        binding.ivPetImage.setOnClickListener {

            //갤러리 호출
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            activityResult.launch(intent)
        }

        binding.btnAddPet.setOnClickListener{
            addPet()
            finish()
        }
    }

    private fun addPetRelationship() {

        val retrofit = NetworkClient.getRetrofit()

        val service = retrofit.create(PetMainService::class.java)
        service.addPetRelationship(insertpetIdx, GlobalUserId.getUserId()).enqueue(object : Callback<PetMainDetailCreateResponse> {

            override fun onResponse(call: Call<PetMainDetailCreateResponse>, response: retrofit2.Response<PetMainDetailCreateResponse>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    val result: PetMainDetailCreateResponse? = response.body()
                    Log.d(TAG, "onResponse 성공: " + result?.toString())

                    when (result?.code) {
                        200 -> {
                            Toast.makeText(applicationContext, "$insertpetIdx 관계정보 등록에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                        }

                        else -> {
                            Log.d(TAG, "addPetRelationship onResponse: ㅈ버그발생 보내는 데이터가 문제임 ")
                        }
                    }

                } else {
                    // 통신이 실패한 경우(응답 코드 3xx, 4xx 등)
                    Log.d(TAG, "addPetRelationship onResponse 실패" + response.code())
                }
            }

            override fun onFailure(call: Call<PetMainDetailCreateResponse>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d(TAG, "addPetRelationship onFailure 에러: " + t.message.toString())
            }
        })
    }

    private fun addPetDetail() {
        val datePicker = findViewById<DatePicker>(R.id.dp_vaccination_date)

        // DatePicker에서 선택한 날짜 가져오기
        val year = datePicker.year
        val month = datePicker.month+1
        val dayOfMonth = datePicker.dayOfMonth

        val vaccination = "$year-$month-$dayOfMonth"

        val datepicker1 = findViewById<DatePicker>(R.id.dp_deworming_date)

        val year1 = datepicker1.year
        val month1 = datepicker1.month+1
        val dayOfMonth1 = datepicker1.dayOfMonth

        val helminthic = "$year1-$month1-$dayOfMonth1"

        val weight = binding.etPetWeight.text.toString().toFloat()
        val bcs = 3

        val retrofit = NetworkClient.getRetrofit()

        val service = retrofit.create(PetMainService::class.java)
        service.addPetDetail(insertpetIdx,vaccination,helminthic,weight,bcs).enqueue(object : Callback<PetMainDetailCreateResponse> {

            override fun onResponse(call: Call<PetMainDetailCreateResponse>, response: retrofit2.Response<PetMainDetailCreateResponse>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    val result: PetMainDetailCreateResponse? = response.body()
                    Log.d(TAG, "onResponse 성공: " + result?.toString())

                    when (result?.code) {
                        200 -> {
                            Toast.makeText(applicationContext, "$insertpetIdx 상세정보 등록에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                        }

                        else -> {
                            Log.d(TAG, "addPetDetail onResponse : ㅈ버그발생 보내는 데이터가 문제임 ")
                        }
                    }

                } else {
                    // 통신이 실패한 경우(응답 코드 3xx, 4xx 등)
                    Log.d(TAG, "addPetDetail onResponse 실패" + response.code())
                }
            }

            override fun onFailure(call: Call<PetMainDetailCreateResponse>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d(TAG, "addPetDetail onFailure 에러: " + t.message.toString())
            }
        })
    }

    private fun addPet() {
        val category = binding.etPetCategory.text.toString()
        val species = binding.etPetSpecies.text.toString()
        val name = binding.etPetName.text.toString()
        val gender = binding.etPetGender.text.toString()
        val age = Integer.parseInt(binding.etPetAge.text.toString())

        val imageUrl = uriToString(imageURI)

        val retrofit = NetworkClient.getRetrofit()

        val service = retrofit.create(PetMainService::class.java)
        service.addPet(category, species, imageUrl, name, gender, age).enqueue(object : Callback<PetMainCreateResponse> {

            override fun onResponse(call: Call<PetMainCreateResponse>, response: retrofit2.Response<PetMainCreateResponse>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    val result: PetMainCreateResponse? = response.body()
                    Log.d(TAG, "onResponse 성공: " + result?.toString())

                    when (result?.code) {
                        200 -> {
                            Toast.makeText(applicationContext, "새로운 반려동물 등록에 성공하였습니다.${result.petId}", Toast.LENGTH_SHORT).show()
                            insertpetIdx = result.petId
                            addPetDetail()
                            addPetRelationship()
                        }

                        else -> {
                            Log.d(TAG, "addPet onResponse: ㅈ버그발생 보내는 데이터가 문제임 ")
                        }
                    }

                } else {
                    // 통신이 실패한 경우(응답 코드 3xx, 4xx 등)
                    Log.d(TAG, "addPet onResponse 실패" + response.code())
                }
            }

            override fun onFailure(call: Call<PetMainCreateResponse>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d(TAG, "addPet onFailure 에러: " + t.message.toString())
            }
        })
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

    //결과 가져오기
    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){

        //결과 코드 OK , 결가값 null 아니면
        if(it.resultCode == RESULT_OK){
            imageURI = it.data!!.data!!
            binding.ivPetImage.setImageURI(imageURI)
        }
    }
}