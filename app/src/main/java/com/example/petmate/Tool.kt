package com.example.petmate

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class Tool {
    companion object {
        fun getRetrofit(): Retrofit {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://13.124.16.204:3000/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

            return retrofit
        }
    }
}