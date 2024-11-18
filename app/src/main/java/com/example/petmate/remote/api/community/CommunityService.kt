package com.example.petmate.remote.api.community

import com.example.petmate.remote.response.community.CommunityResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CommunityService {

    @GET("/post/popular")
    fun getPopularList(@Query("date") date: String): Call<CommunityResponse>

    @GET("/post/board")
    fun getBoardList(): Call<CommunityResponse>
}
