package com.example.petmate.remote.api.navigation

import com.example.petmate.remote.response.navigation.PetRelationshipResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CheckUserPetService {

    @GET("/pet-relationships")
    fun getReadByUserIdx(@Query("userIdx") userIdx:Int): Call<PetRelationshipResponse>
}
