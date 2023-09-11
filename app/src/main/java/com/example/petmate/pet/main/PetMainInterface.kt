package com.example.petmate.pet.main

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PetMainInterface {

    @GET("/Pet/Detail")
    fun getInfo(@Query("petIdx") petIdx:Int): Call<PetMainInterfaceResponse>

    @GET("/Training/getStar")
    fun getStar(@Query("petIdx") petIdx: Int): Call<PetMainInterfaceStarResponse>
}