package com.example.petmate.remote.response.login

import com.google.gson.annotations.SerializedName

data class CheckEmailResponse(

    @SerializedName("isSuccess")
    var isSuccess:Boolean,

    @SerializedName("code")
    var code:Int,

    @SerializedName("message")
    var message:String
)
