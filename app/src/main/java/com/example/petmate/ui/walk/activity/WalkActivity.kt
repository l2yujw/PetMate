package com.example.petmate.ui.walk.activity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.petmate.databinding.ActivityWalkBinding
import com.example.petmate.remote.api.walk.WalkKakaoService
import com.example.petmate.remote.response.walk.WalkNearPlaceKeywordSearchResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapPolyline
import net.daum.mf.map.api.MapView
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Math.round
import java.time.Duration
import java.time.LocalTime

class WalkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWalkBinding
    private lateinit var mapView: MapView
    private lateinit var mapPolyline: MapPolyline
    private lateinit var locationManager: LocationManager

    private lateinit var startPoint: Location
    private lateinit var endPoint: Location
    private lateinit var startTime: LocalTime
    private lateinit var endTime: LocalTime
    private lateinit var trainingTime: Duration
    private var trainingDistance: Int = 0
    private var isTracking = false

    private val searchCategories = listOf("동물병원", "카페#반려동물", "공원#반려동물")
    private val baseUrl = "https://dapi.kakao.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeComponents()
    }

    private fun initializeComponents() {
        initializeMap()
        setupCategorySpinner()
        setupClickListeners()
    }

    private fun initializeMap() {
        mapView = MapView(this)
        mapPolyline = MapPolyline()
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        binding.rlKakaoMap.addView(mapView)
    }

    private fun setupCategorySpinner() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, searchCategories)
        binding.spinnerWalkCategory.adapter = adapter

        binding.spinnerWalkCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                getCurrentLocation()?.let { location ->
                    val category = searchCategories[position]
                    lifecycleScope.launch {
                        searchNearbyPlaces(location.longitude.toString(), location.latitude.toString(), "5000", category)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setupClickListeners() {
        binding.btnWalkStart.setOnClickListener {
            if (!isTracking) {
                startTracking()
            } else {
                showToast("이미 경로 추적 중입니다.")
            }
        }

        binding.btnWalkFinish.setOnClickListener {
            if (isTracking) {
                stopTracking()
            } else {
                showToast("경로를 추적하고 있지 않습니다.")
            }
        }
    }

    private fun startTracking() {
        getCurrentLocation()?.let { location ->
            mapPolyline = MapPolyline()
            mapView.removeAllPolylines()
            startPoint = location
            startTime = LocalTime.now()
            isTracking = true

            lifecycleScope.launch {
                trackRoute()
            }
        } ?: showToast("위치 정보를 가져올 수 없습니다.")
    }

    private fun stopTracking() {
        getCurrentLocation()?.let { location ->
            endPoint = location
            trainingDistance = round(startPoint.distanceTo(endPoint))
            endTime = LocalTime.now()
            trainingTime = Duration.between(startTime, endTime)
            isTracking = false

            showToast("${trainingTime.toHours()}시간 ${trainingTime.toMinutes()}분 ${trainingTime.seconds}초\n${trainingDistance}m")
        }
    }

    private suspend fun trackRoute() {
        while (isTracking) {
            getCurrentLocation()?.let { location ->
                updateRouteOnMap(location)
            }
            delay(1000)
        }
    }

    private fun updateRouteOnMap(location: Location) {
        val mapPoint = MapPoint.mapPointWithGeoCoord(location.latitude, location.longitude)
        mapPolyline.addPoint(mapPoint)
        mapView.addPolyline(mapPolyline)
        mapView.setMapCenterPoint(mapPoint, true)
    }

    private fun getCurrentLocation(): Location? {
        val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        return if (permission == PackageManager.PERMISSION_GRANTED) {
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        } else {
            requestLocationPermission()
            null
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
    }

    private fun searchNearbyPlaces(longitude: String, latitude: String, radius: String, keyword: String) {
        val client = OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        val api = retrofit.create(WalkKakaoService::class.java)
        api.getSearchKeyword(longitude, latitude, radius, keyword).enqueue(object : Callback<WalkNearPlaceKeywordSearchResult> {
            override fun onResponse(call: Call<WalkNearPlaceKeywordSearchResult>, response: Response<WalkNearPlaceKeywordSearchResult>) {
                response.body()?.let {
                    updateMapWithSearchResults(it.documents)
                }
            }

            override fun onFailure(call: Call<WalkNearPlaceKeywordSearchResult>, t: Throwable) {
                Log.e("WalkActivity", "Keyword search failed: ${t.message}")
            }
        })
    }

    private fun updateMapWithSearchResults(documents: List<WalkNearPlaceKeywordSearchResult.WalkNearPlace>) {
        mapView.removeAllPOIItems()
        documents.forEach { doc ->
            val mapPoint = MapPoint.mapPointWithGeoCoord(doc.latitude.toDouble(), doc.longitude.toDouble())
            val marker = MapPOIItem().apply {
                itemName = doc.name
                this.mapPoint = mapPoint
                markerType = MapPOIItem.MarkerType.BluePin
                selectedMarkerType = MapPOIItem.MarkerType.RedPin
            }
            mapView.addPOIItem(marker)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
