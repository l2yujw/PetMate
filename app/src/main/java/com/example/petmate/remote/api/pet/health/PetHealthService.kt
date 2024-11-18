package com.example.petmate.remote.api.pet.health

import com.example.petmate.remote.response.pet.health.PetHealthResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PetHealthService {

    @GET("/pet/details")
    fun getPetHealthDetails(@Query("petIdx")petIdx:Int): Call<PetHealthResponse>
}
