package com.example.petmate.login

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.petmate.R


class Login10PetseekerActivity : Activity() {

    val PERM_STORAGE = 9
    val REQ_GALLERY = 12
    lateinit var context: Context
    var petImage: Uri? = null

    lateinit var addImage: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                intent.putExtra("petImage", petImage)
                setResult(RESULT_OK, intent)
            } else {
                Log.d("petImage", "Image not selected")
                setResult(RESULT_CANCELED, intent)
            }
            finish()
        }
    }

    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, REQ_GALLERY)
    }

    fun requirePermissions(permissions: Array<String>, requestCode: Int) {
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

    fun permissionGranted(requestCode: Int) {
        when (requestCode) {
            PERM_STORAGE -> initViews()
        }
    }

    fun permissionDenied(requestCode: Int) {
        when (requestCode) {
            PERM_STORAGE -> {
                Toast.makeText(this, "공용 저장소 권한을 승인해야 앱을 정상적으로 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    fun initViews() {
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
                        addImage.setImageURI(uri)
                        petImage = uri
                    }
                }
            }
        }
    }


}