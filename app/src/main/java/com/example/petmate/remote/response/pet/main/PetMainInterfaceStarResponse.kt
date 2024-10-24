package com.example.petmate.remote.response.pet.main

import com.google.gson.annotations.SerializedName

data class PetMainInterfaceStarResponse(
    @SerializedName("isSuccess")
    var isSuccess: Boolean,

    @SerializedName("code")
    var code: Int,

    @SerializedName("message")
    var message: String,

    @SerializedName("result")
    var result: ArrayList<PetMainInterfaceStarResponseResult>

)

data class PetMainInterfaceStarResponseResult (
   @SerializedName("name")
   var name: String,

   @SerializedName("level")
   var level: Int,

   @SerializedName("detail")
   var detail:String,

   @SerializedName("url")
   var url:String
)