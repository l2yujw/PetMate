package com.example.petmate.core.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class Tool {
    companion object {
        fun getRetrofit(): Retrofit {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://3.34.34.132:3000/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

            return retrofit
        }
    }
}