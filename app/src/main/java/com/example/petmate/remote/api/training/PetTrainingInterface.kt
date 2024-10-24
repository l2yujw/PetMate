package com.example.petmate.remote.api.training

import com.example.petmate.remote.response.training.PetTrainingInterfaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PetTrainingInterface {

    @GET("/Training/readAll")
    fun readAll(@Query("petIdx") petIdx: Int): Call<PetTrainingInterfaceResponse>

    @GET("/Training/readBylevel")
    fun readWithLevel(@Query("level") level: Int, @Query("petIdx") petIdx: Int): Call<PetTrainingInterfaceResponse>

    @GET("/Training/readByname")
    fun readWithName(@Query("name") name: String, @Query("petIdx") petIdx: Int): Call<PetTrainingInterfaceResponse>
}