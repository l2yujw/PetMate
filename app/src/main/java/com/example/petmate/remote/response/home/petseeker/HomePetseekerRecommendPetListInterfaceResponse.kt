package com.example.petmate.remote.response.home.petseeker

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class HomePetseekerRecommendPetListInterfaceResponse (
    @SerializedName("isSuccess")
    var isSuccess:Boolean,

    @SerializedName("code")
    var code:Int,

    @SerializedName("message")
    var message:String,

    @SerializedName("result")
    var result:ArrayList<HomePetseekerRecommendPetListInterfaceResponseResponseResult>

)

data class HomePetseekerRecommendPetListInterfaceResponseResponseResult (
    @SerializedName("dataIdx")
    var dataIdx:Int,

    @SerializedName("noticeNumber")
    var noticeNumber:String,

    @SerializedName("imageUrl")
    var imageUrl:String,

    @SerializedName("receiptDate")
    var receiptDate:String,

    @SerializedName("species")
    var species:String,

    @SerializedName("gender")
    var gender:String,

    @SerializedName("discoveryPlace")
    var discoveryPlace:String,

    @SerializedName("characteristic")
    var characteristic:String,

    @SerializedName("publicNoticeStart")
    var publicNoticeStart:String,

    @SerializedName("publicNoticeEnd")
    var publicNoticeEnd:String,

    @SerializedName("colorCd")
    var colorCd:String,

    @SerializedName("age")
    var age:Int,

    @SerializedName("weight")
    var weight:String,

    @SerializedName("orgNm")
    var orgNm:String,

    @SerializedName("careAddr")
    var careAddr:String,

    @SerializedName("officetel")
    var officetel:String,

    @SerializedName("careNm")
    var careNm:String
    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<HomePetseekerRecommendPetListInterfaceResponseResponseResult> {
        override fun createFromParcel(parcel: Parcel): HomePetseekerRecommendPetListInterfaceResponseResponseResult {
            return HomePetseekerRecommendPetListInterfaceResponseResponseResult(parcel)
        }

        override fun newArray(size: Int): Array<HomePetseekerRecommendPetListInterfaceResponseResponseResult?> {
            return arrayOfNulls(size)
        }
    }
}