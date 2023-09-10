package com.example.petmate.home.petowner

import com.google.gson.annotations.SerializedName

data class HomePetownerInterfaceResponse (
    @SerializedName("isSuccess")
    var isSuccess:Boolean,

    @SerializedName("code")
    var code:Int,

    @SerializedName("message")
    var message:String,

    @SerializedName("result")
    var result:ArrayList<Result>

)

data class Result (
    @SerializedName("petIdx")
    var petIdx:Int,

    @SerializedName("image")
    var image:String,

    @SerializedName("name")
    var name:String
)