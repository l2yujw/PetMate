package com.example.petmate.login

import android.Manifest
import android.app.Activity
import android.content.Context
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
import androidx.core.app.ActivityCompat
import com.example.petmate.Classifier
import com.example.petmate.R
import com.example.petmate.databinding.ItemLoginPetseekerBinding
import java.io.IOException
import java.util.Locale


class Login10PetseekerActivity : Activity() {
    private lateinit var classifier: Classifier
    private val PERM_STORAGE = 9
    private val REQ_GALLERY = 12
    lateinit var context: Context
    private var petImage: Uri? = null
    var tfliteresult: String = ""

    private lateinit var addImage: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initClassifier()

        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.item_login_petseeker)

        requirePermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERM_STORAGE)

        setContent()
    }

    private fun setContent() {
        val mConfirm = findViewById<Button>(R.id.btn_login_perseeker_complete)
        addImage = findViewById(R.id.login_petseeker_addimage)

        mConfirm.setOnClickListener {
            val intent = Intent(this, Login10Activity::class.java)
            if (petImage != null) {
                Log.d("petImage", "Image selected")
                //intent.putExtra("petImage", petImage)
                intent.putExtra("result",tfliteresult)
                Toast.makeText(this, tfliteresult, Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK, intent)
            } else {
                Log.d("petImage", "Image not selected")
                setResult(RESULT_CANCELED, intent)
            }
            finish()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, REQ_GALLERY)
    }

    private fun requirePermissions(permissions: Array<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(this, permissions, requestCode)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            permissionGranted(requestCode)
        } else {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                Log.d("requestPermission", "requestPermission: granted")
                permissionGranted(requestCode)
            } else {
                Log.d("requestPermission", "requestPermission: denied")
                permissionDenied(requestCode)
            }
        }
    }

    private fun permissionGranted(requestCode: Int) {
        when (requestCode) {
            PERM_STORAGE -> initViews()
        }
    }

    private fun permissionDenied(requestCode: Int) {
        when (requestCode) {
            PERM_STORAGE -> {
                Toast.makeText(this, "공용 저장소 권한을 승인해야 앱을 정상적으로 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun initViews() {
        addImage.setOnClickListener {
            openGallery()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQ_GALLERY -> {
                    data?.data?.let { uri ->
                        val selectedImage = uri
                        val src = ImageDecoder.createSource(contentResolver, selectedImage)
                        val bitmap = ImageDecoder.decodeBitmap(src)
                        val output = classifier.classify(bitmap)
                        val resultStr =
                            String.format(Locale.ENGLISH, "class : %s, prob : %.2f%%", output.first, output.second * 100)
                        //Toast.makeText(applicationContext, output.first, Toast.LENGTH_SHORT).show()

                        addImage.setImageURI(uri)
                        petImage = uri
                        tfliteresult = output.first
                    }
                }
            }
        }
    }
    private fun initClassifier() {
        classifier = Classifier(this, Classifier.IMAGENET_CLASSIFY_MODEL)
        try {
            classifier.init()
        } catch (exception: IOException) {
            Toast.makeText(this, "Can not init Classifier!!", Toast.LENGTH_SHORT).show()
        }
    }
}