package com.example.petmate.home.petowner.weather

import com.example.petmate.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HomePetownerWeatherObject {
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.WEATHER_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Json데이터를 사용자가 정의한 Java 객채로 변환해주는 라이브러리
            .build()
    }

    fun getRetrofitService(): HomePetownerWeatherInterface {
        return getRetrofit().create(HomePetownerWeatherInterface::class.java) //retrofit객체 생성
    }


}