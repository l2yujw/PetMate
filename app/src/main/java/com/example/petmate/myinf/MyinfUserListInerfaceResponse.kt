package com.example.petmate.myinf

import com.google.gson.annotations.SerializedName

data class MyinfUserListInerfaceResponse(
    @SerializedName("isSuccess")
    var isSuccess: Boolean,

    @SerializedName("code")
    var code: Int,

    @SerializedName("message")
    var message: String,

    @SerializedName("result")
    var result: ArrayList<MyinfUserListInterfaceResponseResult>

)

data class MyinfUserListInterfaceResponseResult(
    @SerializedName("userIdx")
    var userIdx:Int,

    @SerializedName("name")
    var name:String,

    @SerializedName("image")
    var image:String
)
