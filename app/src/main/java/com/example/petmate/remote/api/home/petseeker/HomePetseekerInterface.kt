package com.example.petmate.remote.api.home.petseeker

import com.example.petmate.remote.response.home.petseeker.HomePetseekerRecommendInterfaceResponse
import com.example.petmate.remote.response.home.petseeker.HomePetseekerRecommendPetListInterfaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HomePetseekerInterface {

    @GET("HomePetseeker/getrecommend")
    fun getRecommend(@Query("userIdx") userIdx:Int): Call<HomePetseekerRecommendInterfaceResponse>

    @GET("HomePetseeker/getrecommendpetlist")
    fun getRecommendPetList(@Query("species") species:String): Call<HomePetseekerRecommendPetListInterfaceResponse>

    @GET("HomePetseeker/getcategorypetlist")
    fun getCategoryPetList(@Query("category") category:String): Call<HomePetseekerRecommendPetListInterfaceResponse>

    @GET("HomePetseeker/getCareNm")
    fun getCareNmList(@Query("careNm") careNm:String): Call<HomePetseekerRecommendPetListInterfaceResponse>
}