package com.example.petmate.walk

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface WalkKakaoAPI {
    // 카카오 API 인증키 [필수]
    @Headers("Authorization: KakaoAK 621c889e0678eaf2ae4d854d900ebbf3")
    @GET("/v2/local/search/keyword.json") // Keyword.json의 정보를 받아옴
    fun getSearchKeyword(
        @Query("x") x: String,
        @Query("y") y: String,
        @Query("radius") distance: String,
        @Query("query") place_name: String

    ): Call<WalkNearPlaceKeywordSearchResult> // 받아온 정보가 WalkNearPlaceKeywordSearchResult 클래스의 구조로 담김
}

