package com.example.petmate.myinf

import com.google.gson.annotations.SerializedName

data class MyinfUserInterfaceResponse(
    @SerializedName("isSuccess")
    var isSuccess: Boolean,

    @SerializedName("code")
    var code: Int,

    @SerializedName("message")
    var message: String,

    @SerializedName("result")
    var result: ArrayList<MyinfUserInterfaceResponseResult>

)

data class MyinfUserInterfaceResponseResult(

    @SerializedName("name")
    var name:String,

    @SerializedName("image")
    var image:String,

    @SerializedName("nickName")
    var nickName: String,

    @SerializedName("lineInfo")
    var lineInfo:String

)