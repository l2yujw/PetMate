package com.example.petmate.remote.api.login

import com.example.petmate.remote.response.login.CheckEmailResponse
import com.example.petmate.remote.response.login.LoginResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface LoginService {

    @GET("/user/login")
    fun login(@Query("email") email:String, @Query("pw") pw:String): Call<LoginResponse>

    @GET("/user/check-email")
    fun checkEmail(@Query("email") email:String): Call<CheckEmailResponse>

    @POST("/user/create")
    fun createUser(
        @Query("name") name: String,
        @Query("email") email: String,
        @Query("pw") pw: String,
        @Query("recommend") tfliteResult: String
    ): Call<LoginResponse>
}
