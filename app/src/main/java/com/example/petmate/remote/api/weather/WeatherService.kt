package com.example.petmate.remote.api.weather

import com.example.petmate.BuildConfig
import com.example.petmate.ui.home.petowner.weather.data.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// 결과 xml 파일에 접근해서 정보 가져오기
interface WeatherService {

    //TODO 기상 예보 초단기에서 단기예보로 바꾸기
    @GET("getVilageFcst?serviceKey=" + BuildConfig.WEATHER_API_KEY)         // 단기 예보
//    @GET("getUltraSrtFcst?serviceKey=" + BuildConfig.WEATHER_API_KEY)     // 초단기 예보
    fun getWeather(
        @Query("pageNo") pageNo: Int,          // 페이지 번호
        @Query("numOfRows") numOfRows: Int,   // 한 페이지 경과 수
        @Query("dataType") dataType: String,   // 응답 자료 형식
        @Query("base_date") baseDate: String,  // 발표 일자
        @Query("base_time") baseTime: String,  // 발표 시각
        @Query("nx") nx: Int,                   // 예보지점 X 좌표
        @Query("ny") ny: Int                    // 예보지점 Y 좌표
    ): Call<Weather>
}
