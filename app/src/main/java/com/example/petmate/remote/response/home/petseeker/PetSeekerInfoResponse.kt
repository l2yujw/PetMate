package com.example.petmate.remote.response.home.petseeker

import com.google.gson.annotations.SerializedName

data class PetSeekerInfoResponse (

    @SerializedName("isSuccess")
    var isSuccess:Boolean,

    @SerializedName("code")
    var code:Int,

    @SerializedName("message")
    var message:String,

    @SerializedName("result")
    var result:ArrayList<PetSeekerInfoResult>

)

data class PetSeekerInfoResult (

    @SerializedName("userIdx")
    var userIdx:Int,

    @SerializedName("recommend")
    var recommend:String
)
