package com.example.petmate.walk

import android.Manifest
import android.R
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.petmate.databinding.ActivityWalkBinding
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


class WalkActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWalkBinding
    private lateinit var mapView: MapView

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
    }

    //지도를 현재 위치로 옮겨주는 함수
    private fun startTracking(){
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
            val lm: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            try {
                val userNowLocation: Location =
                    lm!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)!!
                val uLatitude = userNowLocation.latitude
                val uLongitude = userNowLocation.longitude
                val uNowPosition = MapPoint.mapPointWithGeoCoord(uLatitude, uLongitude)
                mapView.setMapCenterPoint(uNowPosition, true)
            }catch(e: NullPointerException){
                Log.e("LOCATION_ERROR", e.toString())
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ActivityCompat.finishAffinity(this)
                }else{
                    ActivityCompat.finishAffinity(this)
                }
            }
        }else{
            Toast.makeText(this, "위치 권한이 없습니다.", Toast.LENGTH_SHORT).show()
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
    }
}