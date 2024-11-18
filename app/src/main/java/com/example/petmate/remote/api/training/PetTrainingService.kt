package com.example.petmate.remote.api.training

import com.example.petmate.remote.response.training.PetTrainingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PetTrainingService {

    // 모든 훈련 데이터를 조회
    @GET("/training/all")
    fun getAll(@Query("petIdx") petIdx: Int): Call<PetTrainingResponse>

    // 특정 레벨에 따른 훈련 데이터 조회
    @GET("/training/by-level")
    fun getByLevel(@Query("level") level: Int, @Query("petIdx") petIdx: Int): Call<PetTrainingResponse>

    // 특정 이름에 따른 훈련 데이터 조회
    @GET("/training/by-name")
    fun getByName(@Query("name") name: String, @Query("petIdx") petIdx: Int): Call<PetTrainingResponse>
}
