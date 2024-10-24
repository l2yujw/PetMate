package com.example.petmate.core.util

import android.graphics.Point
import android.util.Log

class HomePetownerWeatherCommon {
    // baseTime 설정하기
    fun getBaseTime(HH: String/*, m: String*/): String {
        var result = ""

        /*// 45분 전이면
        if (m.toInt() < 45) {
            // 0시면 2330
            if (h == "00") result = "2330"
            // 아니면 1시간 전 날씨 정보 부르기
            else {
                var resultH = h.toInt() - 1
                // 1자리면 0 붙여서 2자리로 만들기
                if (resultH < 10) result = "0" + resultH + "30"
                // 2자리면 그대로
                else result = resultH.toString() + "30"
            }
        }
        // 45분 이후면 바로 정보 받아오기
        else result = h + "30"*/

        Log.d("WEATHER TEST", "날씨 정보 HHmm" + HH)
        when (HH) {
            "02", "03", "04" -> result = "0200"
            "05", "06", "07" -> result = "0500"
            "08", "09", "10" -> result = "0800"
            "11", "12", "13" -> result = "1100"
            "14", "15", "16" -> result = "1400"
            "17", "18", "19" -> result = "1700"
            "20", "21", "22" -> result = "2000"
            else -> result = "2300"
        }

        return result

        //TODO 기상 예보 초단기에서 단기예보로 바꾸기
        /*
        초단기 예보 기준 시간: 현재 실황에서부터 6시간 이내의 기상상황을 1시간 간격으로 발표(갱신주기: 10분)
        단기 예보 기준 시간: 일 8회 발표: 02, 05, 08, 11, 14, 17, 20, 23시
        단기 예보 사용 시 기준 시간에 정확하게 맞출 필요 있음.
        */
    }

    // 위경도를 기상청에서 사용하는 격자 좌표로 변환
    fun dfsXyConv(v1: Double, v2: Double): Point {
        val RE = 6371.00877     // 지구 반경(km)
        val GRID = 5.0          // 격자 간격(km)
        val SLAT1 = 30.0        // 투영 위도1(degree)
        val SLAT2 = 60.0        // 투영 위도2(degree)
        val OLON = 126.0        // 기준점 경도(degree)
        val OLAT = 38.0         // 기준점 위도(degree)
        val XO = 43             // 기준점 X좌표(GRID)
        val YO = 136            // 기준점 Y좌표(GRID)
        val DEGRAD = Math.PI / 180.0
        val re = RE / GRID
        val slat1 = SLAT1 * DEGRAD
        val slat2 = SLAT2 * DEGRAD
        val olon = OLON * DEGRAD
        val olat = OLAT * DEGRAD

        var sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5)
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn)
        var sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5)
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn
        var ro = Math.tan(Math.PI * 0.25 + olat * 0.5)
        ro = re * sf / Math.pow(ro, sn)

        var ra = Math.tan(Math.PI * 0.25 + (v1) * DEGRAD * 0.5)
        ra = re * sf / Math.pow(ra, sn)
        var theta = v2 * DEGRAD - olon
        if (theta > Math.PI) theta -= 2.0 * Math.PI
        if (theta < -Math.PI) theta += 2.0 * Math.PI
        theta *= sn

        val x = (ra * Math.sin(theta) + XO + 0.5).toInt()
        val y = (ro - ra * Math.cos(theta) + YO + 0.5).toInt()

        return Point(x, y)
    }
}