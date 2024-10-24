package com.example.petmate.remote.api.pet.health

import com.example.petmate.remote.response.pet.health.PetHealthInterfaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PetHealthInterface {

    @GET("/Pet/Detail_petIdx")
    fun getInfo(@Query("petIdx")petIdx:Int): Call<PetHealthInterfaceResponse>
}