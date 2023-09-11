package com.example.petmate.pet.training

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PetTrainingInterface {

    @GET("/Training/readAll")
    fun readAll(@Query("petIdx") petIdx:Int): Call<PetTrainingInterfaceResponse>
}