package com.example.petmate.home.petowner

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.petmate.R
import com.example.petmate.home.petowner.weather.HomePetownerWeatherAdapter
import com.example.petmate.home.petowner.weather.HomePetownerWeatherCommon
import com.example.petmate.home.petowner.weather.HomePetownerWeatherData
import com.example.petmate.home.petowner.weather.HomePetownerWeatherObject
import com.example.petmate.home.petowner.weather.WEATHER
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import retrofit2.Call
import retrofit2.Response
import java.util.*
import android.util.Log
import android.os.Looper
import com.example.petmate.HorizontalItemDecorator
import com.example.petmate.databinding.FragmentHomePetownerBinding
import com.example.petmate.home.petowner.weather.ITEM
import com.example.petmate.login.Login10Activity
import com.example.petmate.walk.WalkActivity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class HomePetownerFragment : Fragment() {

    private var weatherBaseDate = "20230816"  // 발표 일자
    private var weatherBaseTime = "0500"      // 발표 시각
    private var curPoint: Point? = null    // 현재 위치의 격자 좌표를 저장할 포인트
    lateinit var binding: FragmentHomePetownerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    @SuppressLint("SetTextI18n", "MissingPermission")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Get permission
        val permissionList = arrayOf<String>(
            // 위치 권한
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )

        // 권한 요청
        requestPermissions(permissionList, 1)

        // 날씨. 위치 정보를 기반으로 날씨 정보 요청
//        requestLocation()

        /*// <새로고침> 버튼 누를 때 위치 정보 & 날씨 정보 다시 가져오기
        binding.btnRefresh.setOnClickListener {
            requestLocation()
        }*/

//        var rootView = inflater.inflate(R.layout.fragment_home_petowner, container, false)
        binding = FragmentHomePetownerBinding.inflate(inflater)


        val petTextList = ArrayList<HomePetownerPetlistData>()
        val scheduleList = ArrayList<HomePetownerScheduleData>()

        petTextList.add(HomePetownerPetlistData("aaaaaa", "https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        petTextList.add(HomePetownerPetlistData("bbbbbb", "https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        petTextList.add(HomePetownerPetlistData("ccccc", "https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))

        scheduleList.add(HomePetownerScheduleData("08:00 am", "MainText", "SubText"))
        scheduleList.add(HomePetownerScheduleData("08:00 am", "MainText", "SubText"))
        scheduleList.add(HomePetownerScheduleData("08:00 am", "MainText", "SubText"))

        val boardAdapterPetList = HomePetownerPetlistAdapter(/*getPetImageList(), */petTextList)
        boardAdapterPetList.notifyDataSetChanged()
        val boardAdapterScheduleList = HomePetownerScheduleAdapter(scheduleList)
        boardAdapterScheduleList.notifyDataSetChanged()

        val indicator = binding.circleindicatorPetownerPetlist

        binding.viewpagerPetownerPetlist.adapter = HomePetownerPetlistAdapter(/*getPetImageList(), */petTextList)
        binding.viewpagerPetownerPetlist.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        indicator.setViewPager(binding.viewpagerPetownerPetlist)
        indicator.createIndicators(petTextList.size, 0)

        //현재 page 받아옴 (fragment 혹은 image 어떤걸로 할지 고려중)
        binding.viewpagerPetownerPetlist.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                indicator.animatePageSelected(position)
                Toast.makeText(requireContext(), "${position + 1} 페이지 선택됨", Toast.LENGTH_SHORT).show()
            }
        })

        binding.rcvHavepetSchedule.adapter = boardAdapterScheduleList
        binding.rcvHavepetSchedule.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.rcvHavepetWeather.addItemDecoration(HorizontalItemDecorator(15))

        binding.btnWalk.setOnClickListener{
            var intent = Intent(requireContext(), WalkActivity::class.java)
            startActivity(intent)
        }

        return binding.getRoot()
    }

/*
    // 날씨 가져와서 설정하기
    private fun setWeather(nx: Int, ny: Int) {
        // 준비 단계 : base_date(발표 일자), base_time(발표 시각)
        // 현재 날짜, 시간 정보 가져오기
        val cal = Calendar.getInstance()
        weatherBaseDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time) // 현재 날짜
        val timeH = SimpleDateFormat("HH", Locale.getDefault()).format(cal.time) // 현재 시각
//        val timeM = SimpleDateFormat("mm", Locale.getDefault()).format(cal.time) // 현재 분
        // API 가져오기 적당하게 변환
        weatherBaseTime = HomePetownerWeatherCommon().getBaseTime(timeH)
        // 현재 시각이 00시이고 45분 이하여서 baseTime이 2330이면 어제 정보 받아오기
        if (timeH == "00" || timeH == "01") {
            cal.add(Calendar.DATE, -1).toString()
            weatherBaseDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time)
        }

        // 날씨 정보 가져오기
        // (페이지 번호 = 1, 한 페이지 결과 수 = 60, 응답 자료 형식-"JSON", 발표 날싸, 발표 시각, 예보지점 좌표)
        val call = HomePetownerWeatherObject.getRetrofitService().getWeather(1, 300, "JSON", weatherBaseDate, weatherBaseTime, nx, ny)
        Log.d("WEATHER TEST", "날씨 정보 가져오기")
        Log.d("WEATHER TEST", "날씨 정보" + weatherBaseDate + "+" + weatherBaseTime + "+" + nx + "+" + ny)

        // 비동기적으로 실행하기
        call.enqueue(object : retrofit2.Callback<WEATHER> {
            // 응답 성공 시
            override fun onResponse(call: Call<WEATHER>, response: Response<WEATHER>) {
                Log.d("WEATHER TEST", "날씨 정보 응답")
                if (response.isSuccessful) {
                    // 날씨 정보 가져오기
                    //TODO 가져온 정보에 바디가 없을 경우 에러
                    val it: List<ITEM> = response.body()!!.response.body.items.item     // 바디 없으면 에러. 수정 필요

                    // 현재 시각부터 1시간 뒤의 날씨 6개를 담을 배열
//                    val weatherArr = arrayOf(HomePetownerWeatherData())
                    val weatherArr =
                        arrayOf(
                            HomePetownerWeatherData(),
                            HomePetownerWeatherData(),
                            HomePetownerWeatherData(),
                            HomePetownerWeatherData(),
                            HomePetownerWeatherData(),
                            HomePetownerWeatherData(),
                            HomePetownerWeatherData(),
                            HomePetownerWeatherData(),
                            HomePetownerWeatherData(),
                            HomePetownerWeatherData(),
                            HomePetownerWeatherData(),
                            HomePetownerWeatherData(),
                            HomePetownerWeatherData(),
                            HomePetownerWeatherData(),
                            HomePetownerWeatherData(),
                            HomePetownerWeatherData(),
                            HomePetownerWeatherData(),
                            HomePetownerWeatherData(),
                            HomePetownerWeatherData(),
                            HomePetownerWeatherData(),
                            HomePetownerWeatherData(),
                            HomePetownerWeatherData(),
                            HomePetownerWeatherData(),
                            HomePetownerWeatherData()
                        )
                    /*for (k in 1..23) {
                        weatherArr.plus(HomePetownerWeatherData())
                    }*/

                    // 배열 채우기
                    var index = 0
                    var subIndex = -1
//                    val totalCount = response.body()!!.response.body.totalCount - 1
                    val totalCount = it.size - 1
                    for (i in 0..totalCount) {
                        subIndex++
                        index = subIndex / 12
                        if ((weatherBaseDate != it[i].fcstDate) && (weatherBaseTime == it[i].fcstTime))
                            break
                        when (it[i].category) {
                            "PTY" -> {
                                weatherArr[index].rainType = it[i].fcstValue     // 강수 형태
                                continue
                            }

                            "SKY" -> {
                                weatherArr[index].sky = it[i].fcstValue          // 하늘 상태
                                continue
                            }

                            "TMP" -> weatherArr[index].temp = it[i].fcstValue         // 기온

                            "UUU", "VVV", "VEC", "WSD", "POP", "WAV", "PCP", "REH", "SNO" -> continue
                            else -> {   // TMX, TMN
                                subIndex -= 1
                                continue
                            }
                        }
                        weatherArr[index].fcstTime = it[i].fcstTime
                    }

//                    weatherArr[0].fcstTime = "지금"
                    // 각 날짜 배열 시간 설정
//                    for (i in 0..23) weatherArr[i].fcstTime = it[i].fcstTime

                    //TODO 추후에 토스트 메시지 제거
                    //토스트 띄우기
                    Toast.makeText(requireContext(), it[0].fcstDate + ", " + it[0].fcstTime + "의 날씨 정보입니다.", Toast.LENGTH_SHORT).show()
                    Log.d("WEATHER TEST", "날씨 정보 성공")

                    // 리사이클러 뷰에 데이터 연결
                    binding.rcvHavepetWeather.adapter = HomePetownerWeatherAdapter(weatherArr)
                    Log.d("WEATHER TEST", "날씨 정보 리사이클러뷰 성공")
                    //TODO 기준시간 - 3시간까지 넘어가면 크래시

                } else {
                    Log.d("WEATHER TEST", "날씨 정보 실패")
                }
            }

            // 응답 실패 시
            override fun onFailure(call: Call<WEATHER>, t: Throwable) {
                Log.d("WEATHER TEST", "날씨 정보 응답 실패")
                Log.d("api fail", t.message.toString())
            }
        })
    }*/

/*
    // 내 현재 위치의 위경도를 격자 좌표로 변환하여 해당 위치의 날씨정보 설정
    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        val locationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        try {
            // 나의 현재 위치 요청
            val locationRequest = LocationRequest.create()
            locationRequest.run {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 60 * 1000    // 요청 간격(1초)
            }
            val locationCallback = object : LocationCallback() {
                // 요청 결과
                override fun onLocationResult(p0: LocationResult) {
                    p0.let {
                        for (location in it.locations) {


                            // 현재 위치의 위경도를 격자 좌표로 변환
                            curPoint = HomePetownerWeatherCommon().dfsXyConv(location.latitude, location.longitude)

                            // nx, ny지점 날씨를 가져와서 설정
                            setWeather(curPoint!!.x, curPoint!!.y)
                        }
                    }
                }
            }

            // 위치 설정
            Looper.myLooper()?.let {
                locationClient.requestLocationUpdates(locationRequest, locationCallback, it)
            }


        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }*/

    private fun getPetImageList(): ArrayList<Int> {
        return arrayListOf<Int>(R.drawable.cat1_temp, R.drawable.cat2_temp, R.drawable.cat1_temp)
    }

}