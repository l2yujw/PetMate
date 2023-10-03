package com.example.petmate.pet.main

import com.google.gson.annotations.SerializedName

data class PetDetailaddInterfaceResponse (
    @SerializedName("isSuccess")
    var isSuccess:Boolean,

    @SerializedName("code")
    var code:Int,

    @SerializedName("message")
    var message:String
)