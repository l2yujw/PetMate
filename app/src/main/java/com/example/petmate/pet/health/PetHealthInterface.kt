package com.example.petmate.pet.health

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PetHealthInterface {

    @GET("/Pet/Detail_petIdx")
    fun getInfo(@Query("petIdx")petIdx:Int): Call<PetHealthInterfaceResponse>
}