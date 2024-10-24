package com.example.petmate.remote.response.home.petowner

import com.google.gson.annotations.SerializedName

data class HomePetownerInterfaceResponse (
    @SerializedName("isSuccess")
    var isSuccess:Boolean,

    @SerializedName("code")
    var code:Int,

    @SerializedName("message")
    var message:String,

    @SerializedName("result")
    var result:ArrayList<HomePetownerInterfaceResponseResult>

)

data class HomePetownerInterfaceResponseResult (
    @SerializedName("petIdx")
    var petIdx:Int,

    @SerializedName("image")
    var image:String,

    @SerializedName("name")
    var name:String
)