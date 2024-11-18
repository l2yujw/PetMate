package com.example.petmate.ui.walk.data

data class WalkRecordData(
    val guardian: String = "Unknown Guardian",
    val trainingTime: String = "00:00:00",
    val calories: String = "0",
    val avgFrequency: String = "0",
    val maxFrequency: String = "0",
    val breaks: String = "0",
    val breakTimes: String = "00:00"
)
