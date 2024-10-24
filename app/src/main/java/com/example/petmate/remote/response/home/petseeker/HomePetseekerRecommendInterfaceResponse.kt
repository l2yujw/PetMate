package com.example.petmate.remote.response.home.petseeker

import com.google.gson.annotations.SerializedName

data class HomePetseekerRecommendInterfaceResponse (
    @SerializedName("isSuccess")
    var isSuccess:Boolean,

    @SerializedName("code")
    var code:Int,

    @SerializedName("message")
    var message:String,

    @SerializedName("result")
    var result:ArrayList<HomePetseekerInterfaceResponseResult>

)

data class HomePetseekerInterfaceResponseResult (
    @SerializedName("userIdx")
    var userIdx:Int,

    @SerializedName("recommend")
    var recommend:String
)