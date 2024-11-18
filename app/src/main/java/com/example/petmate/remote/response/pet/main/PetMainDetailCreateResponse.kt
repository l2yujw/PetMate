package com.example.petmate.remote.response.pet.main

import com.google.gson.annotations.SerializedName

data class PetMainDetailCreateResponse (
    @SerializedName("isSuccess")
    var isSuccess:Boolean,

    @SerializedName("code")
    var code:Int,

    @SerializedName("message")
    var message:String
)
