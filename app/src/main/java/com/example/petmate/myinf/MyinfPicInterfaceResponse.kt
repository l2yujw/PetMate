package com.example.petmate.myinf

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class MyinfPicInterfaceResponse(
    @SerializedName("isSuccess")
    var isSuccess: Boolean,

    @SerializedName("code")
    var code: Int,

    @SerializedName("message")
    var message: String,

    @SerializedName("result")
    var result: ArrayList<MyInfPicInterfaceResponseResult>

)

data class MyInfPicInterfaceResponseResult(
    @SerializedName("postIdx")
    var postIdx: Int,

    @SerializedName("userImage")
    var userImage:String,

    @SerializedName("nickName")
    var nickName: String,

    @SerializedName("title")
    var title:String,

    @SerializedName("detail")
    var detail:String,

    @SerializedName("image")
    var image:String
) : Parcelable {
    constructor(parcel: Parcel) : this(
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

    companion object CREATOR : Parcelable.Creator<MyInfPicInterfaceResponseResult> {
        override fun createFromParcel(parcel: Parcel): MyInfPicInterfaceResponseResult {
            return MyInfPicInterfaceResponseResult(parcel)
        }

        override fun newArray(size: Int): Array<MyInfPicInterfaceResponseResult?> {
            return arrayOfNulls(size)
        }
    }
}
