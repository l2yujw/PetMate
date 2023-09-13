package com.example.petmate.community

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CommunityInterface {

    @GET("/Post/Popular")
    fun getPopularList(@Query("date") date:String): Call<CommunityInterfaceResponse>

    @GET("/Post/Board")
    fun getBoardList(): Call<CommunityInterfaceResponse>
}