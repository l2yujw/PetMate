package com.example.petmate.login

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 서버에 요청하는 함수
 * "@Query("id")" == "?id="
 */
interface loginService {

    @GET("/User/login")
    fun getLogin(@Query("id") id:String, @Query("pw") pw:String): Call<LoginResult>

}