package com.example.petmate.remote.api.home.petowner

import com.example.petmate.remote.response.home.petowner.PetOwnerResponse
import com.example.petmate.remote.response.home.petowner.PetScheduleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PetOwnerService {

    @GET("/home/petowner/userid")
    fun getPetList(@Query("userId") userIdx:Int): Call<PetOwnerResponse>

    @GET("/home/petowner/schedule")
    fun getPetScheduleList(@Query("userId") userIdx:Int, @Query("date") date:String):Call<PetScheduleResponse>
}
