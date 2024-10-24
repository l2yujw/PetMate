package com.example.petmate.remote.api.login

import com.example.petmate.remote.response.login.CheckEmailResponse
import com.example.petmate.remote.response.login.LoginInterfaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface LoginInterface {

    @GET("/User/login")
    fun getLogin(@Query("email") email:String, @Query("pw") pw:String): Call<LoginInterfaceResponse>

    @GET("/User/checkemail")
    fun getCheckEmail(@Query("email") email:String): Call<CheckEmailResponse>
    @POST("/User/insert")
    fun postCreateUser(@Query("name") name:String, @Query("email") email:String, @Query("pw") pw:String,@Query("recommend") tfliteresult:String):Call<LoginInterfaceResponse>

}