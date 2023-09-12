package com.example.petmate.walk

import android.Manifest
import android.app.Activity
import android.app.Service
import android.app.Service.START_NOT_STICKY
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import androidx.lifecycle.lifecycleScope
import com.example.petmate.databinding.ActivityWalkBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.daum.android.map.coord.MapCoordLatLng
import net.daum.mf.map.api.MapCurrentLocationMarker
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapPolyline
import net.daum.mf.map.api.MapView
import java.lang.Math.round
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import kotlin.properties.Delegates


class WalkActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWalkBinding
    private lateinit var mapView: MapView
    private lateinit var mapPolyline: MapPolyline
    private lateinit var polylineService: PolylineService

    //장소 정보를 다루기 위한 LocationManager
    private lateinit var lm: LocationManager
    //시작 장소의 위치 정보
    private lateinit var startPoint: Location
    //도착 장소의 위치 정보
    private lateinit var endPoint: Location
    //시작 시간 정보
    private lateinit var startTime: LocalTime
    //도착 시간 정보
    private lateinit var endTime: LocalTime
    //산책 시간 정보
    private lateinit var trainingTime: Duration
    //산책 거리 정보(m 단위)
    private var trainingDistance: Int = 0
    //경로 체크 유지 여부를 저장하는 변수
    private var checkLoadBoolean: Boolean = false
    //start 버튼을 눌러서 시작을 했는지 확인하는 변수
    private var isStarted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val mapView = MapView(this)
        //binding.KakaoMapView.addView(mapView)

        //Kakao Developer 에서 제공하는 코드로 변경
        mapView = MapView(this)
        val mapViewContainer = binding.KakaoMapView as ViewGroup
        mapViewContainer.addView(mapView)

        //장소 정보를 다루기 위한 LocationManager 초기화
        lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        //경로를 추가하기 위한 MapPolyline 객체 초기화
        mapPolyline = MapPolyline()
        //경로를 그리기 위한 PolylineService 생성
        polylineService = PolylineService(mapView, mapPolyline, lm, applicationContext)

        binding.walkStart.setOnClickListener{
            if (!isStarted){
                Toast.makeText(this, "경로 추적을 시작합니다.", Toast.LENGTH_SHORT).show()
                startTracking()
            }
            else{
                Toast.makeText(this, "이미 경로 추적중입니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.walkFinish.setOnClickListener{
            if (isStarted){
                stopTracking()
            }
            else {
                Toast.makeText(this, "경로를 추적하고 있지 않습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //start 버튼을 눌렀을 때 실행되는 함수
    private fun startTracking(){
        //지도를 현재 위치로 옮겨줌
        try {
            val uNowLocation = getCurrentLocation(lm)!!
            val uNowPosition = changeLocationToMapPoint(uNowLocation)
            mapView.setMapCenterPoint(uNowPosition, true)
            //이동 거리를 구하기 위한 시작 장소 정보 보관
            startPoint = uNowLocation
            //이동 시간을 구하기 위한 시작 시간 정보 보관
            startTime = LocalTime.now()
            //이동 경로를 체크하기 위해 변수를 초기화
            mapView.removeAllPolylines()
            checkLoadBoolean = true
            isStarted = true
            //이동 경로를 구하기 위한 함수 실행(UI 스레드를 차단하지 않는 비동기 코드로 사용하기 위해 코루틴을 lifecycleScope를 사용하여 실행함)
            lifecycleScope.launch {
                checkLoad(lm, mapView, mapPolyline)
            }

            //이동 경로를 그리기 위해 백그라운드 서비스를 실행하려다 실패함
            //val intent = Intent(applicationContext, PolylineService::class.java)
            //polylineService.startPolylineService()

        }catch(e: NullPointerException){
            Log.e("LOCATION_ERROR", e.toString())
            ActivityCompat.finishAffinity(this)
        }catch(e: Exception){
            Log.e("LOCATION_ERROR", e.toString())
            ActivityCompat.finishAffinity(this)
        }
    }

    private fun stopTracking(){
        //지도를 현재 위치로 옮겨줌
        try {
            //이동 거리를 구하기 위해서 도착 장소 정보 보관
            endPoint = getCurrentLocation(lm)!!
            //이동 거리를 구해서 보관
            trainingDistance = round(startPoint.distanceTo(endPoint))

            //이동 시간을 구하기 위한 도착 시간 정보 보관
            endTime = LocalTime.now()
            //이동 시간을 구해서 보관
            trainingTime = Duration.between(startTime,endTime)
            //이동 경로 추적 중지
            checkLoadBoolean = false
            isStarted = false

            //이동 경로를 다 그렸으므로 백그라운드 서비스를 종료함
            //val intent = Intent(this, PolylineService::class.java)
            //stopService(intent)

            //Toast로 값 표시
            Toast.makeText(this, "${trainingTime.toHours()}시 ${trainingTime.toMinutes()}분 ${trainingTime.toMillis()/1000}초\n${trainingDistance}m", Toast.LENGTH_SHORT).show()

        }catch(e: NullPointerException){
            Log.e("LOCATION_ERROR", e.toString())
            ActivityCompat.finishAffinity(this)
        }
    }

    private fun getCurrentLocation(lm: LocationManager) : Location? {
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        return if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
            lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)!!
        }else{
            Toast.makeText(this, "위치 권한이 없습니다.", Toast.LENGTH_SHORT).show()
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            Log.d("PERMISSION_ERROR", "getCurrentPosition Func")
            throw Exception()
        }
    }

    private fun changeLocationToMapPoint(loc: Location) : MapPoint{
        return run {
            val latitude = loc.latitude
            val longitude = loc.longitude
            MapPoint.mapPointWithGeoCoord(latitude, longitude)
        }
    }

    private suspend fun checkLoad(lm: LocationManager, mapView: MapView, mapPolyline: MapPolyline){
        while(checkLoadBoolean){
            val currentLocation = getCurrentLocation(lm)!!
            val currentMapPoint = changeLocationToMapPoint(currentLocation)
            mapView.setMapCenterPoint(currentMapPoint, true)
            mapPolyline.addPoint(currentMapPoint)
            mapView.addPolyline(mapPolyline)
            delay(1000)
        }
    }
}