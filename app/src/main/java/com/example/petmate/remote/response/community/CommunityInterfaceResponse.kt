package com.example.petmate.remote.response.community

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class CommunityInterfaceResponse(
    @SerializedName("isSuccess")
    var isSuccess: Boolean,

    @SerializedName("code")
    var code: Int,

    @SerializedName("message")
    var message: String,

    @SerializedName("result")
    var result: ArrayList<CommunityInterfaceResponseResult>

)

data class CommunityInterfaceResponseResult(
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

    companion object CREATOR : Parcelable.Creator<CommunityInterfaceResponseResult> {
        override fun createFromParcel(parcel: Parcel): CommunityInterfaceResponseResult {
            return CommunityInterfaceResponseResult(parcel)
        }

        override fun newArray(size: Int): Array<CommunityInterfaceResponseResult?> {
            return arrayOfNulls(size)
        }
    }
}
