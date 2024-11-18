package com.example.petmate.remote.api.home.petseeker

import com.example.petmate.remote.response.home.petseeker.PetSeekerInfoResponse
import com.example.petmate.remote.response.home.petseeker.PetSeekerDetailInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PetSeekerService {

    @GET("/home/petseeker/recommendations")
    fun getRecommend(@Query("userIdx") userIdx:Int): Call<PetSeekerInfoResponse>

    @GET("/home/petseeker/recommended-pets")
    fun getPetsBySpecies(@Query("species") species:String): Call<PetSeekerDetailInfoResponse>

    @GET("/home/petseeker/category-pets")
    fun getCategoryPetList(@Query("category") category:String): Call<PetSeekerDetailInfoResponse>

    @GET("/home/petseeker/care-list")
    fun getCareNmList(@Query("careNm") careNm:String): Call<PetSeekerDetailInfoResponse>
}
