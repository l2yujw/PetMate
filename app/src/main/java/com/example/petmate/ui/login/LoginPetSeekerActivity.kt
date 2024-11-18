package com.example.petmate.ui.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.petmate.R
import com.example.petmate.core.classifier.Classifier
import java.io.IOException

class LoginPetSeekerActivity : AppCompatActivity() {
    private lateinit var classifier: Classifier
    private var petImageUri: Uri? = null
    private var classificationResult: String = ""

    private lateinit var addImageButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initClassifier()

        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.item_login_petseeker)

        checkStoragePermission()

        setupContent()
    }

    private fun setupContent() {
        val confirmButton = findViewById<Button>(R.id.btn_login_perseeker_complete)
        addImageButton = findViewById(R.id.login_petseeker_addimage)

        confirmButton.setOnClickListener {
            val intent = Intent(this, LoginRegisterActivity::class.java)
            if (petImageUri != null) {
                Log.d("petImage", "Image selected")
                intent.putExtra("result", classificationResult)
                Toast.makeText(this, classificationResult, Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK, intent)
            } else {
                Log.d("petImage", "Image not selected")
                setResult(RESULT_CANCELED, intent)
            }
            finish()
        }

        addImageButton.setOnClickListener {
            openGallery()
        }
    }

    private fun checkStoragePermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestStoragePermission()
            } else {
                initViews()
            }
        } else {
            initViews()
        }
    }

    private fun requestStoragePermission() {
        requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERM_STORAGE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERM_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initViews()
            } else {
                Toast.makeText(this, "공용 저장소 권한을 승인해야 앱을 정상적으로 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK).apply {
            type = MediaStore.Images.Media.CONTENT_TYPE
        }
        galleryLauncher.launch(galleryIntent)
    }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.data?.let { uri ->
                handleSelectedImage(uri)
            }
        }
    }

    private fun handleSelectedImage(uri: Uri) {
        petImageUri = uri
        try {
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(contentResolver, uri)
            }
            classifyImage(bitmap)
        } catch (e: IOException) {
            Log.e("ImageError", "이미지 처리 오류: ${e.message}")
            Toast.makeText(this, "이미지 처리 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun classifyImage(bitmap: Bitmap) {
        val output = classifier.classify(bitmap)
        classificationResult = output.label
        addImageButton.setImageURI(petImageUri)
        Toast.makeText(this, "분류 결과: $classificationResult", Toast.LENGTH_SHORT).show()
    }

    private fun initClassifier() {
        classifier = Classifier(this, Classifier.IMAGENET_CLASSIFY_MODEL)
        try {
            classifier.initializeModel()
        } catch (e: IOException) {
            Toast.makeText(this, "모델 초기화 실패", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initViews() {
        addImageButton.setOnClickListener {
            openGallery()
        }
    }

    companion object {
        private const val PERM_STORAGE = 9
    }
}
