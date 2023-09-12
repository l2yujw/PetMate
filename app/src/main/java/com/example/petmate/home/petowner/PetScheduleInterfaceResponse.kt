package com.example.petmate.home.petowner

import com.google.gson.annotations.SerializedName
import java.util.Date

data class PetScheduleInterfaceResponse (
    @SerializedName("isSuccess")
    var isSuccess:Boolean,

    @SerializedName("code")
    var code:Int,

    @SerializedName("message")
    var message:String,

    @SerializedName("result")
    var result:ArrayList<PetScheduleInterfaceResponseResult>

)
data class PetScheduleInterfaceResponseResult (
    @SerializedName("petIdx")
    var petIdx:Int,

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