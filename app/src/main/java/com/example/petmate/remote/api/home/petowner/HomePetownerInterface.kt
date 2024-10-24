package com.example.petmate.remote.api.home.petowner

import com.example.petmate.remote.response.home.petowner.HomePetownerInterfaceResponse
import com.example.petmate.remote.response.home.petowner.PetScheduleInterfaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HomePetownerInterface {

    @GET("/HomePetowner/userIdx")
    fun getPetList(@Query("userIdx") userIdx:Int): Call<HomePetownerInterfaceResponse>

    @GET("/HomePetowner/schedule")
    fun getPetScheduleList(@Query("userIdx") userIdx:Int, @Query("date") date:String):Call<PetScheduleInterfaceResponse>
}