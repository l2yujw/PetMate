package com.example.petmate.login

import com.google.gson.annotations.SerializedName


/**
 * loginService의 Response인 JSON 형식에 맞춰 받기 위한 data class
 */
data class LoginResult(

    @SerializedName("isSuccess")
    var isSuccess:Boolean,

    @SerializedName("code")
    var code:Int,

    @SerializedName("message")
    var message:String

)
