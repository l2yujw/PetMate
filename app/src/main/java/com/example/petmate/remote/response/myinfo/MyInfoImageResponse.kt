package com.example.petmate.remote.response.myinfo

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class MyInfoImageResponse(

    @SerializedName("isSuccess")
    var isSuccess: Boolean,

    @SerializedName("code")
    var code: Int,

    @SerializedName("message")
    var message: String,

    @SerializedName("result")
    var result: ArrayList<MyInfoImageResult>
)

@Parcelize
data class MyInfoImageResult(

    @SerializedName("postIdx")
    var postIdx: Int,

    @SerializedName("userImage")
    var userImage:String,

    @SerializedName("nickName")
    var nickName: String,

    @SerializedName("title")
    var title:String,

    @SerializedName("detail")
    var detail:String,

    @SerializedName("image")
    var image:String
) : Parcelable

data class MyInfoAddPostResponse(

    @SerializedName("isSuccess")
    var isSuccess: Boolean,

    @SerializedName("code")
    var code: Int,

    @SerializedName("message")
    var message: String
)
