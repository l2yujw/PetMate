package com.example.petmate.remote.api.pet.main

import com.example.petmate.remote.response.pet.main.PetMainDetailCreateResponse
import com.example.petmate.remote.response.pet.main.PetMainResponse
import com.example.petmate.remote.response.pet.main.PetMainStarResponse
import com.example.petmate.remote.response.pet.main.PetMainCreateResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PetMainService {

    // 애완동물 정보 조회
    @GET("/pet/detail")
    fun getPetDetails(@Query("petIdx") petIdx: Int): Call<PetMainResponse>

    // 훈련 별점 조회
    @GET("/training/star")
    fun getTrainingStar(@Query("petIdx") petIdx: Int): Call<PetMainStarResponse>

    // 애완동물 등록
    @POST("/pet/insert")
    fun addPet(
        @Query("category") category: String,
        @Query("species") species: String,
        @Query("image") image: String,
        @Query("name") name: String,
        @Query("gender") gender: String,
        @Query("age") age: Int
    ): Call<PetMainCreateResponse>

    // 애완동물 상세 정보 등록
    @POST("/pet-detail/insert")
    fun addPetDetail(
        @Query("petIdx") petIdx: Int,
        @Query("vaccination") vaccination: String,
        @Query("helminthic") helminthic: String,
        @Query("weight") weight: Float,
        @Query("bcs") bcs: Int
    ): Call<PetMainDetailCreateResponse>

    // 애완동물 관계 등록
    @POST("/pet-relationship/insert")
    fun addPetRelationship(@Query("petIdx") petIdx: Int, @Query("userIdx") userIdx: Int): Call<PetMainDetailCreateResponse>
}
