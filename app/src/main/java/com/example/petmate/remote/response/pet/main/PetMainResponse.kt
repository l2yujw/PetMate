package com.example.petmate.remote.response.pet.main

import com.google.gson.annotations.SerializedName

data class PetMainResponse(
    @SerializedName("isSuccess")
    var isSuccess: Boolean,

    @SerializedName("code")
    var code: Int,

    @SerializedName("message")
    var message: String,

    @SerializedName("result")
    var result: ArrayList<PetMainResult>
)

data class PetMainResult(
    @SerializedName("petId")
    var petId: Int,

    @SerializedName("category")
    var category: String,

    @SerializedName("species")
    var species: String,

    @SerializedName("image")
    var image: String,

    @SerializedName("name")
    var name: String,

    @SerializedName("gender")
    var gender: String,

    @SerializedName("age")
    var age: Int,

    @SerializedName("vaccination")
    var vaccination: String,

    @SerializedName("helminthic")
    var helminthic: String,

    @SerializedName("weight")
    var weight: Float,

    @SerializedName("bcs")
    var bcs: Int
)
