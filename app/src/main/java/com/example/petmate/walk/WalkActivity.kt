package com.example.petmate.walk

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.petmate.databinding.ActivityWalkBinding
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.text.SimpleDateFormat
import kotlin.properties.Delegates


class WalkActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWalkBinding
    private lateinit var mapView: MapView
    private lateinit var startPoint: MapPoint
    private lateinit var endPoint: MapPoint
    private var startTime by Delegates.notNull<Long>()


    private lateinit var trainingTime: String
    private lateinit var calories: String
    private lateinit var avgFrequency: String
    private lateinit var maxFrequency: String
    private lateinit var breaks: String
    private lateinit var breakTimes: String

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

        binding.walkStart.setOnClickListener{
            startTracking()
        }

        binding.walkFinish.setOnClickListener{
            stopTracking()
        }
    }

    //start 버튼을 눌렀을 때 실행되는 함수
    private fun startTracking(){
        val lm: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        //지도를 현재 위치로 옮겨줌
        try {
            val uNowPosition = getCurrentPosition(lm)
            mapView.setMapCenterPoint(uNowPosition, true)
            //이동 거리를 구하기 위한 시작 장소 정보 보관
            startPoint = uNowPosition
            //이동 시간을 구하기 위한 시작 시간 정보 보관
            startTime = System.currentTimeMillis()

        }catch(e: NullPointerException){
            Log.e("LOCATION_ERROR", e.toString())
            ActivityCompat.finishAffinity(this)
        }
    }

    private fun stopTracking(){
        val lm: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        //지도를 현재 위치로 옮겨줌
        try {
            val uNowPosition = getCurrentPosition(lm)
            //이동 거리를 구하기 위한 도착 장소 정보 보관
            endPoint = uNowPosition
            //이동 시간을 구해서 보관
            val diffTime = System.currentTimeMillis() - startTime
            val dateFormat = SimpleDateFormat("hh:mm:ss")
            trainingTime = dateFormat.format(diffTime)

        }catch(e: NullPointerException){
            Log.e("LOCATION_ERROR", e.toString())
            ActivityCompat.finishAffinity(this)
        }
    }

    private fun getCurrentPosition(lm: LocationManager) : MapPoint {
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        return if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
            val userNowLocation: Location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)!!
            val uLatitude = userNowLocation.latitude
            val uLongitude = userNowLocation.longitude
            MapPoint.mapPointWithGeoCoord(uLatitude, uLongitude)
        }else{
            Toast.makeText(this, "위치 권한이 없습니다.", Toast.LENGTH_SHORT).show()
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            Log.d("PERMISSION_ERROR", "getCurrentPosition Func")
            MapPoint.mapPointWithGeoCoord(0.0, 0.0)
        }
    }
}