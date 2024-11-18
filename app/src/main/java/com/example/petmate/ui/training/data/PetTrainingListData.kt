package com.example.petmate.ui.training.data

import android.graphics.Bitmap

data class PetTrainingListData(
    val imageUrl: String,         // 이미지 URL 저장
    var bitmap: Bitmap? = null,  // 비동기적으로 로드될 Bitmap
    val isStar: Boolean          // 즐겨찾기 여부
)
