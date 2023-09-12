package com.example.petmate.navigation

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CheckUserPetInterface {

    @GET("/PetRelationship/readByuserIdx")
    fun getReadByUserIdx(@Query("userIdx") userIdx:Int): Call<PetRelationshipResponse>
}