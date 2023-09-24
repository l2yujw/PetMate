package com.example.petmate.myinf

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MyinfInterface {

    @GET("/User/read")
    fun getUserInfo(@Query("userIdx") userIdx:Int):Call<MyinfUserInterfaceResponse>

    @GET("/Post/read")
    fun getPicList(@Query("userIdx") userIdx:Int): Call<MyinfPicInterfaceResponse>

    @GET("/Userlist/read")
    fun getUserList(@Query("userIdx") userIdx:Int): Call<MyinfUserListInerfaceResponse>

    @POST("/Post/insert")
    fun addPost(@Query("userIdx") userIdx: Int,@Query("title") title:String, @Query("detail") detail:String, @Query("image") image:String):Call<MyinfaddPostInterfaceResponse>

}