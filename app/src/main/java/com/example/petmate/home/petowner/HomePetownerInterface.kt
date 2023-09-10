package com.example.petmate.home.petowner

import com.example.petmate.login.LoginInterfaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.sql.Date

interface HomePetownerInterface {

    @GET("HomePetowner/userIdx")
    fun getPetList(@Query("userIdx") userIdx:Int): Call<HomePetownerInterfaceResponse>

    @GET("HomePetowner/schedule")
    fun getPetScheduleList(@Query("userIdx") userIdx:Int, @Query("date") date:String):Call<PetScheduleInterfaceResponse>
}