package com.example.petmate.remote.api.myinfo

import com.example.petmate.remote.response.myinfo.MyInfoImageResponse
import com.example.petmate.remote.response.myinfo.MyInfoUserResponse
import com.example.petmate.remote.response.myinfo.MyInfoUserListResponse
import com.example.petmate.remote.response.myinfo.MyInfoAddPostResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MyInfoService {

    @GET("/user/read")
    fun getUser(@Query("userIdx") userIdx: Int): Call<MyInfoUserResponse>

    // 사용자 사진 목록 조회
    @GET("/post/read")
    fun getPictures(@Query("userIdx") userIdx: Int): Call<MyInfoImageResponse>

    // 사용자 목록 조회
    @GET("/users/read")
    fun getUsers(@Query("userIdx") userIdx: Int): Call<MyInfoUserListResponse>

    // 새로운 게시글 추가
    @POST("/post/insert")
    fun addPost(
        @Query("userIdx") userIdx: Int,
        @Query("title") title: String,
        @Query("detail") detail: String,
        @Query("image") image: String
    ): Call<MyInfoAddPostResponse>
}
