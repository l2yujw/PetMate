package com.example.petmate.home.petseeker

import android.os.Parcel
import android.os.Parcelable

class HomePetseekerRecommendData(
    private var dataIdx:Int,
    private var noticeNumber:String,
    private var imageUrl:String,
    private var receiptDate:String,
    private var species:String,
    private var gender:String,
    private var discoveryPlace:String,
    private var characteristic:String,
    private var publicNoticeStart:String,
    private var publicNoticeEnd:String,
    private var colorCd:String,
    private var age:Int,
    private var weight:String,
    private var orgNm:String,
    private var careAddr:String,
    private var officetel:String
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
    )
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