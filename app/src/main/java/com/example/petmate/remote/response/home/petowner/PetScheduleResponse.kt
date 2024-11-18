package com.example.petmate.remote.response.home.petowner

import com.google.gson.annotations.SerializedName
import java.util.Date

data class PetScheduleResponse (

    @SerializedName("isSuccess")
    var isSuccess:Boolean,

    @SerializedName("code")
    var code:Int,

    @SerializedName("message")
    var message:String,

    @SerializedName("result")
    var result:ArrayList<PetScheduleResult>

)

data class PetScheduleResult (

    @SerializedName("petId")
    var petId:Int,

    @SerializedName("name")
    var name:String,

    @SerializedName("schedulename")
    var schedulename:String,

    @SerializedName("detail")
    var detail:String,

    @SerializedName("date")
    var date:Date,

    @SerializedName("time")
    var time:String
)
