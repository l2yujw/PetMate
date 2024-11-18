package com.example.petmate.remote.response.navigation

import com.google.gson.annotations.SerializedName

data class PetRelationshipResponse(

    @SerializedName("isSuccess")
    var isSuccess:Boolean,

    @SerializedName("code")
    var code:Int,

    @SerializedName("message")
    var message:String,


    @SerializedName("result")
    var result:List<PetRelationshipResult>
)

data class PetRelationshipResult(
    @SerializedName("petId")
    var petId:Int,

    @SerializedName("userId")
    var userId:Int
)
