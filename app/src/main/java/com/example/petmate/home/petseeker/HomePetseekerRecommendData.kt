package com.example.petmate.home.petseeker

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class HomePetseekerRecommendData(
    var dataIdx:Int,
    var noticeNumber:String,
    var imageUrl:String,
    var receiptDate:String,
    var species:String,
    var gender:String,
    var discoveryPlace:String,
    var characteristic:String,
    var publicNoticeStart:String,
    var publicNoticeEnd:String,
    var colorCd:String,
    var age:Int,
    var weight:String,
    var orgNm:String,
    var careAddr:String,
    var officetel:String
):Parcelable {
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
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(dataIdx)
        parcel.writeString(noticeNumber)
        parcel.writeString(imageUrl)
        parcel.writeString(receiptDate)
        parcel.writeString(species)
        parcel.writeString(gender)
        parcel.writeString(discoveryPlace)
        parcel.writeString(characteristic)
        parcel.writeString(publicNoticeStart)
        parcel.writeString(publicNoticeEnd)
        parcel.writeString(colorCd)
        parcel.writeInt(age)
        parcel.writeString(weight)
        parcel.writeString(orgNm)
        parcel.writeString(careAddr)
        parcel.writeString(officetel)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HomePetseekerRecommendData> {
        override fun createFromParcel(parcel: Parcel): HomePetseekerRecommendData {
            return HomePetseekerRecommendData(parcel)
        }

        override fun newArray(size: Int): Array<HomePetseekerRecommendData?> {
            return arrayOfNulls(size)
        }
    }
}