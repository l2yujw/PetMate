package com.example.petmate.walk

import net.daum.mf.map.api.MapPoint

data class WalkNearPlaceKeywordSearchResult (
        var documents: List<WalkNearPlace> // 검색 결과
    ){
    data class WalkNearPlace(
        var place_name: String, // 장소명, 업체명
        var x: String, // X 좌표값 혹은 longitude
        var y: String, // Y 좌표값 혹은 latitude
        var place_url: String, // 장소 상세페이지 URL
        var distance: String // 중심좌표까지의 거리. 단, x,y 파라미터를 준 경우에만 존재. 단위는 meter
    )
}