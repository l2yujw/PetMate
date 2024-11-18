package com.example.petmate.remote.response.myinfo

import com.google.gson.annotations.SerializedName

data class MyInfoUserListResponse(

    @SerializedName("isSuccess")
    var isSuccess: Boolean,

    @SerializedName("code")
    var code: Int,

    @SerializedName("message")
    var message: String,

    @SerializedName("result")
    var result: ArrayList<MyInfoUserListResult>
)

data class MyInfoUserListResult(
    @SerializedName("userId")
    var userId:Int,

    @SerializedName("name")
    var name:String,

    @SerializedName("image")
    var image:String
)
