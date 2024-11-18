package com.example.petmate.remote.response.training

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class PetTrainingResponse(
    @SerializedName("isSuccess")
    var isSuccess: Boolean,

    @SerializedName("code")
    var code: Int,

    @SerializedName("message")
    var message: String,

    @SerializedName("result")
    var result: ArrayList<TrainingResult>
)

@Parcelize
data class TrainingResult(
    @SerializedName("trainingId")
    var trainingId: Int,

    @SerializedName("name")
    var name: String,

    @SerializedName("level")
    var level: Int,

    @SerializedName("detail")
    var detail: String,

    @SerializedName("url")
    var url: String,

    @SerializedName("petIdx")
    var petIdx: Int
) : Parcelable
