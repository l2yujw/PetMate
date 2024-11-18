package com.example.petmate.remote.response.home.petowner

import com.google.gson.annotations.SerializedName

data class PetOwnerResponse (

    @SerializedName("isSuccess")
    var isSuccess:Boolean,

    @SerializedName("code")
    var code:Int,

    @SerializedName("message")
    var message:String,

    @SerializedName("result")
    var result:ArrayList<PetOwnerResult>

)

data class PetOwnerResult (

    @SerializedName("petId")
    var petId:Int,

    @SerializedName("image")
    var image:String,

    @SerializedName("name")
    var name:String
)
