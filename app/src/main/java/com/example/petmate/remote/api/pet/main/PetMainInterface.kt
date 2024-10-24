package com.example.petmate.remote.api.pet.main

import com.example.petmate.remote.response.pet.main.PetDetailaddInterfaceResponse
import com.example.petmate.remote.response.pet.main.PetMainInterfaceResponse
import com.example.petmate.remote.response.pet.main.PetMainInterfaceStarResponse
import com.example.petmate.remote.response.pet.main.PetaddInterfaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
interface PetMainInterface {

    @GET("/Pet/Detail")
    fun getInfo(@Query("petIdx") petIdx:Int): Call<PetMainInterfaceResponse>

    @GET("/Training/getStar")
    fun getStar(@Query("petIdx") petIdx: Int): Call<PetMainInterfaceStarResponse>

    @POST("/Pet/insert")
    fun addPet(@Query("category") category:String, @Query("species") species:String, @Query("image") image:String,@Query("name") name:String, @Query("gender") gender:String, @Query("age") age:Int):Call<PetaddInterfaceResponse>

    @POST("/PetDetail/insert")
    fun addPetDetail(@Query("petIdx") petIdx:Int, @Query("vaccination") vaccination:String, @Query("helminthic") helminthic:String, @Query("weight") weight:Float, @Query("bcs") bcs:Int):Call<PetDetailaddInterfaceResponse>

    @POST("/PetRelationship/insert")
    fun addPetRelationship(@Query("petIdx") petIdx:Int,@Query("userIdx") userIdx:Int):Call<PetDetailaddInterfaceResponse>
}