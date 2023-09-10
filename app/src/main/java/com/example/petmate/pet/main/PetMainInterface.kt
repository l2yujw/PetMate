package com.example.petmate.pet.main

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PetMainInterface {

    @GET("/Pet/detail")
    fun getInfo(@Query("userIdx")userIdx:Int): Call<PetMainInterfaceResponse>
}