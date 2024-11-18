package com.example.petmate.remote.response.pet.main

import com.google.gson.annotations.SerializedName

data class PetMainCreateResponse (
    @SerializedName("isSuccess")
    var isSuccess:Boolean,

    @SerializedName("code")
    var code:Int,

    @SerializedName("message")
    var message:String,

    @SerializedName("petId")
    var petId:Int
)
