package com.example.petmate.ui.home.petowner.weather.data

data class HomePetOwnerWeatherData(
    var rainType: String = "",  // 강수 형태 (기본값 빈 문자열)
    var sky: String = "",       // 하늘 상태 (기본값 빈 문자열)
    var temp: String = "",      // 기온 (기본값 빈 문자열)
    var fcstTime: String = ""   // 예보시각 (기본값 빈 문자열)
)


data class Weather (val response : Response)
data class Response(val header : Header, val body : Body)
data class Header(val resultCode : Int, val resultMsg : String)
data class Body(val dataType : String, val items : Items, val totalCount : Int)
data class Items(val item : List<Item>)
data class Item(val category : String, val fcstDate : String, val fcstTime : String, val fcstValue : String)