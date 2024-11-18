package com.example.petmate.remote.response.myinfo

import com.google.gson.annotations.SerializedName

data class MyInfoUserResponse(

    @SerializedName("isSuccess")
    var isSuccess: Boolean,

    @SerializedName("code")
    var code: Int,

    @SerializedName("message")
    var message: String,

    @SerializedName("result")
    var result: ArrayList<MyInfoUserResult>
)

data class MyInfoUserResult(

    @SerializedName("name")
    var name:String,

    @SerializedName("image")
    var image:String,

    @SerializedName("nickname")
    var nickname: String,

    @SerializedName("lineInfo")
    var lineInfo:String
)