package com.example.petmate.pet.training

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class PetTrainingInterfaceResponse(

    @SerializedName("isSuccess")
    var isSuccess: Boolean,

    @SerializedName("code")
    var code: Int,

    @SerializedName("message")
    var message: String,

    @SerializedName("result")
    var result: ArrayList<PetTrainingInterfaeResponseResult>

)

data class PetTrainingInterfaeResponseResult (
    @SerializedName("trainingIdx")
    var trainingIdx:Int,

    @SerializedName("name")
    var name: String,

    @SerializedName("level")
    var level: Int,

    @SerializedName("detail")
    var detail:String,

    @SerializedName("url")
    var url:String,

    @SerializedName("petIdx")
    var petIdx:Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(trainingIdx)
        parcel.writeString(name)
        parcel.writeInt(level)
        parcel.writeString(detail)
        parcel.writeString(url)
        parcel.writeInt(petIdx)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PetTrainingInterfaeResponseResult> {
        override fun createFromParcel(parcel: Parcel): PetTrainingInterfaeResponseResult {
            return PetTrainingInterfaeResponseResult(parcel)
        }

        override fun newArray(size: Int): Array<PetTrainingInterfaeResponseResult?> {
            return arrayOfNulls(size)
        }
    }
}
