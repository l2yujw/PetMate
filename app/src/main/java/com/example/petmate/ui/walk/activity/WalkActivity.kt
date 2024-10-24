package com.example.petmate.ui.walk.activity

import android.Manifest
import android.R
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.petmate.databinding.ActivityWalkBinding
import com.example.petmate.remote.api.walk.WalkKakaoAPI
import com.example.petmate.remote.response.walk.WalkNearPlaceKeywordSearchResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapPolyline
import net.daum.mf.map.api.MapView
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Math.round
import java.time.Duration
import java.time.LocalTime


class WalkActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWalkBinding
    private lateinit var mapView: MapView
    private lateinit var mapPolyline: MapPolyline
//    private lateinit var polylineService: PolylineService

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

    //카카오맵 api 주변 검색을 위한 url
    private val BASE_URL = "https://dapi.kakao.com/"

    private val spinnerSearchCategoryList = listOf("동물병원", "카페#반려동물", "공원#반려동물")


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
        mapView.removeAllPolylines()
        //경로를 그리기 위한 PolylineService 생성
//        polylineService = PolylineService(mapView, mapPolyline, lm, applicationContext)

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

        val myAdapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, spinnerSearchCategoryList)
        binding.walkCategorySearchSpinner.adapter = myAdapter
        binding.walkCategorySearchSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작하게 됩니다.
                val currentLocation = getCurrentLocation(lm)
                when(position) {
                    0   ->  {
                        lifecycleScope.launch {
                            searchKeyword(currentLocation!!.longitude.toString(), currentLocation.latitude.toString(), "5000", spinnerSearchCategoryList[0])
                            Log.d("test_search", "동물병원 선택")
                        }
                    }
                    1   ->  {
                        lifecycleScope.launch {
                            searchKeyword(currentLocation!!.longitude.toString(), currentLocation.latitude.toString(), "5000", spinnerSearchCategoryList[1])
                            Log.d("test_search", "동물병원 선택")
                        }
                    }
                    2 -> {
                        lifecycleScope.launch {
                            searchKeyword(currentLocation!!.longitude.toString(), currentLocation.latitude.toString(), "5000", spinnerSearchCategoryList[2])
                            Log.d("test_search", "동물병원 선택")
                        }
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {

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
            mapPolyline = MapPolyline()
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

    private fun searchKeyword(longitude: String, latitude: String, radius: String, keyword: String) {
        //OkHttp 라이브러리를 이용해서 더 자세한 로그를 확인하여 오류를 더 쉽게 해결할 수 있도록
        val clientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(loggingInterceptor)

        val retrofit = Retrofit.Builder() // Retrofit 구성
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientBuilder.build())
            .build()


        val api = retrofit.create(WalkKakaoAPI::class.java) // 통신 인터페이스를 객체로 생성
        val call = api.getSearchKeyword(longitude, latitude, radius, keyword) // 검색 조건

        // API 서버에 요청
        call.enqueue(object: Callback<WalkNearPlaceKeywordSearchResult> {

            override fun onResponse(
                call: Call<WalkNearPlaceKeywordSearchResult>,
                response: Response<WalkNearPlaceKeywordSearchResult>
            ) {
                mapView.removeAllPOIItems()
                // 통신 성공 (검색 결과는 response.body()에 담겨있음)
                Log.d("test_search", "Raw: ${response.raw()}")
                Log.d("test_search", "Body: ${response.body()}")

                val result = response.body()!!.documents
                for (i in result){
                    Log.d("test_search", "${i.x}, ${i.y}")
                    val marker = MapPOIItem()
                    Log.d("test_search", "marker 설정 들어옴 ${i.x}, ${i.y}")
                    val mapPoint = MapPoint.mapPointWithGeoCoord(i.y.toDouble(), i.x.toDouble())
                    marker.itemName = i.place_name
                    marker.mapPoint = mapPoint
                    marker.tag = 0;
                    marker.markerType = MapPOIItem.MarkerType.BluePin
                    marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin
                    mapView.addPOIItem(marker)
                }
            }

            override fun onFailure(call: Call<WalkNearPlaceKeywordSearchResult>, t: Throwable) {
                // 통신 실패
                Log.w("test_search", "통신 실패: ${t.message}")
            }
        })
    }
}