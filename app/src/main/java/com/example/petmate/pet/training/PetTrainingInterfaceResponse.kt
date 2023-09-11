package com.example.petmate.pet.training

import com.google.gson.annotations.SerializedName

data class PetTrainingInterfaceResponse(

    @SerializedName("isSuccess")
    var isSuccess: Boolean,

    @SerializedName("code")
    var code: Int,

    @SerializedName("message")
    var message: String,

    @SerializedName("result")
    var result: ArrayList<PetTrainingInterfaeResponseResult>

)

data class PetTrainingInterfaeResponseResult (
    @SerializedName("trainingIdx")
    var trainingIdx:Int,

    @SerializedName("name")
    var name: String,

    @SerializedName("level")
    var level: Int,

    @SerializedName("detail")
    var detail:String,

    @SerializedName("url")
    var url:String,

    @SerializedName("petIdx")
    var petIdx:Int
)
