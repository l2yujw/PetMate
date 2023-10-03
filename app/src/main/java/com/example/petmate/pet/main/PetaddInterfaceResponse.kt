package com.example.petmate.pet.main

import com.google.gson.annotations.SerializedName

data class PetaddInterfaceResponse (
    @SerializedName("isSuccess")
    var isSuccess:Boolean,

    @SerializedName("code")
    var code:Int,

    @SerializedName("message")
    var message:String,

    @SerializedName("petIdx")
    var petIdx:Int
)