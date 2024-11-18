package com.example.petmate.remote.response.community

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class CommunityResponse(

    @SerializedName("isSuccess")
    var isSuccess: Boolean,

    @SerializedName("code")
    var code: Int,

    @SerializedName("message")
    var message: String,

    @SerializedName("result")
    var result: ArrayList<CommunityResult>
)

@Parcelize
data class CommunityResult(

    @SerializedName("postIdx")
    var postIdx: Int,

    @SerializedName("userImage")
    var userImage: String,

    @SerializedName("nickName")
    var nickName: String,

    @SerializedName("title")
    var title: String,

    @SerializedName("detail")
    var detail: String,

    @SerializedName("image")
    var image: String
) : Parcelable
