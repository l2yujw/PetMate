package com.example.petmate.home.petowner

import com.example.petmate.login.LoginInterfaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HomePetownerInterface {

    @GET("HomePetowner/userIdx")
    fun getPetList(@Query("userIdx") userIdx:Int): Call<HomePetownerInterfaceResponse>
}