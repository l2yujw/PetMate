package com.example.petmate.ui.home.petowner.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Point
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.petmate.core.util.GlobalPetIdxList
import com.example.petmate.core.util.GlobalUserIdx
import com.example.petmate.core.decorator.HorizontalItemDecorator
import com.example.petmate.R
import com.example.petmate.core.network.Tool
import com.example.petmate.databinding.FragmentHomePetownerBinding
import com.example.petmate.home.petowner.weather.HomePetownerWeatherAdapter
import com.example.petmate.home.petowner.weather.HomePetownerWeatherCommon
import com.example.petmate.home.petowner.weather.HomePetownerWeatherData
import com.example.petmate.home.petowner.weather.HomePetownerWeatherObject
import com.example.petmate.home.petowner.weather.ITEM
import com.example.petmate.home.petowner.weather.WEATHER
import com.example.petmate.remote.api.home.petowner.HomePetownerInterface
import com.example.petmate.remote.response.home.petowner.HomePetownerInterfaceResponse
import com.example.petmate.remote.response.home.petowner.PetScheduleInterfaceResponse
import com.example.petmate.ui.home.petowner.adapter.HomePetownerPetlistAdapter
import com.example.petmate.ui.home.petowner.adapter.HomePetownerScheduleAdapter
import com.example.petmate.ui.home.petowner.data.HomePetownerPetlistData
import com.example.petmate.ui.home.petowner.data.HomePetownerScheduleData
import com.example.petmate.ui.walk.activity.WalkActivity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import me.relex.circleindicator.CircleIndicator3
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date
import java.util.Calendar
import java.util.Locale
import kotlin.random.Random


class HomePetownerFragment : Fragment() {

    private val TAG = "HomePetownerFragment123"
    private var weatherBaseDate = "20230816"  // 발표 일자
    private var weatherBaseTime = "0500"      // 발표 시각
    private var curPoint: Point? = null    // 현재 위치의 격자 좌표를 저장할 포인트
    lateinit var binding: FragmentHomePetownerBinding
    lateinit var indicator: CircleIndicator3

    private var viewpagerIdx = 0
    var telArray = arrayOf<String>("02-364-3517", "02-393-7577", "02-393-3588", "02-701-7580", "02-333-7750")


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



        binding = FragmentHomePetownerBinding.inflate(inflater)
        indicator = binding.circleindicatorPetownerPetlist
        indicator.setViewPager(binding.viewpagerPetownerPetlist)

        val userIdx: Int = GlobalUserIdx.getUserIdx()


        requestPetList(userIdx)


        requestScheduleList(userIdx)

        binding.tvHavepetComment.text = "날씨 정보를 불러올 수 없어요"

        //TODO 날씨. 위치 정보를 기반으로 날씨 정보 요청
        requestLocation()

        binding.btnWalk.setOnClickListener {
            val intent = Intent(requireContext(), WalkActivity::class.java)
            intent.putExtra("petIdx", GlobalPetIdxList.getlist()[viewpagerIdx])
            startActivity(intent)
        }

        binding.btnEmergency.setOnClickListener { view ->
            showPoppup(binding.btnEmergency)
            //TODO telArray 값에 따라 팝업 메뉴 생성. DB에 긴급연락처 저장한다면 telArray 값 수정 필요.
        }

        binding.btnPetseeker.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("isUser","isUser")
            findNavController().navigate(R.id.action_homePetownerFragment_to_homePetseekerFragment, bundle)
        }

        return binding.root
    }


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
        Log.d("WEATHER TEST", "날씨 정보$weatherBaseDate+$weatherBaseTime+$nx+$ny")

        // 비동기적으로 실행하기
        call.enqueue(object : Callback<WEATHER> {
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
                            HomePetownerWeatherData()
                        )
                    val weather8h = Array<String>(8) { "" }
                    /*for (k in 1..23) {
                        weatherArr.plus(HomePetownerWeatherData())
                    }*/

                    // 배열 채우기
                    var index: Int
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
                                if (index < 8)
                                    weather8h[index] = it[i].fcstValue
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

                    var rainStack = 0
                    for (i in 0..7) {
                        Log.d("weather raintype", i.toString() + "번째 날씨: " + weather8h[i])
                        when (weather8h[i]) {
                            "1", "2", "3", "4" -> rainStack++
                        }
                    }
                    when (rainStack) {
                        0 -> binding.tvHavepetComment.text = "오늘은 하늘이 맑네요! 가볍게 산책 어떠신가요?"
                        1, 2 -> binding.tvHavepetComment.text = "비 또는 눈이 조금 올 예정이에요. 산책에 참고하세요!"
                        else -> binding.tvHavepetComment.text = "비 또는 눈이 많이 오네요. 오늘 산책은 조금 힘들 수도 있겠어요"
                    }

//                    weatherArr[0].fcstTime = "지금"
                    // 각 날짜 배열 시간 설정
//                    for (i in 0..23) weatherArr[i].fcstTime = it[i].fcstTime

                    Log.d("WEATHER TEST", it[0].fcstDate + ", " + it[0].fcstTime + "의 날씨 정보")

                    // 리사이클러 뷰에 데이터 연결
                    binding.rcvHavepetWeather.adapter = HomePetownerWeatherAdapter(weatherArr)
                    Log.d("WEATHER TEST", "날씨 정보 리사이클러뷰 성공")

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
    }


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
    }

    private fun requestPetList(userIdx: Int) {

        val retrofit = Tool.getRetrofit()

        val service = retrofit.create(HomePetownerInterface::class.java)
        Log.d(TAG, "requestPetList: $userIdx")
        service.getPetList(userIdx).enqueue(object : Callback<HomePetownerInterfaceResponse> {

            override fun onResponse(call: Call<HomePetownerInterfaceResponse>, response: Response<HomePetownerInterfaceResponse>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    val result: HomePetownerInterfaceResponse? = response.body()
                    Log.d(TAG, "onResponse 성공: " + result?.toString())

                    when (result?.code) {
                        200 -> {
                            val petIdxList = GlobalPetIdxList
                            petIdxList.clearlist()
                            val petList = ArrayList<HomePetownerPetlistData>()
                            for (item in result.result) {
                                petIdxList.addlist(item.petIdx)
                                Log.d(TAG, "onResponse: $item")

                                val randomtext = ArrayList<String>()

                                randomtext.add("오늘은 산책하기 좋은 날씨에요!")
                                randomtext.add("오늘은 집에서 쉬고 싶어요~")
                                randomtext.add("오늘은 다른 친구들과 함께 놀고 싶어요!")
                                randomtext.add("간식이 먹고 싶어요~")

                                petList.add(HomePetownerPetlistData(randomtext.get(Random.nextInt(0, randomtext.size)), item.image))
                            }
                            val boardAdapterPetList = HomePetownerPetlistAdapter(petList)
                            boardAdapterPetList.notifyDataSetChanged()

                            indicator.createIndicators(petList.size, 0)

                            binding.viewpagerPetownerPetlist.adapter = HomePetownerPetlistAdapter(petList)
                            binding.viewpagerPetownerPetlist.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                            binding.viewpagerPetownerPetlist.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                                override fun onPageSelected(position: Int) {
                                    super.onPageSelected(position)
                                    indicator.animatePageSelected(position)
                                    //Toast.makeText(requireContext(), "${position + 1} 페이지 선택됨", Toast.LENGTH_SHORT).show()
                                }
                            })
                        }

                        else -> {
                            Log.d(TAG, "onResponse: ㅈ버그발생 보내는 데이터가 문제임 ")
                        }
                    }

                } else {
                    // 통신이 실패한 경우(응답 코드 3xx, 4xx 등)
                    Log.d(TAG, "onResponse 실패" + response.code())
                }
            }

            override fun onFailure(call: Call<HomePetownerInterfaceResponse>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d(TAG, "onFailure 에러: " + t.message.toString())
            }
        })

    }

    private fun requestScheduleList(userIdx: Int) {

        val retrofit = Tool.getRetrofit()

        Log.d(TAG, "requestScheduleList: ENTER")
        val now = System.currentTimeMillis()
        val date = Date(now)
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        //val getDate = sdf.format(date)
        val getDate = "2023-09-10"
        //val sendDate = Date(getDate)
        val service = retrofit.create(HomePetownerInterface::class.java)
        service.getPetScheduleList(userIdx, getDate).enqueue(object : Callback<PetScheduleInterfaceResponse> {

            override fun onResponse(call: Call<PetScheduleInterfaceResponse>, response: Response<PetScheduleInterfaceResponse>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    val result: PetScheduleInterfaceResponse? = response.body()
                    Log.d(TAG, "onResponse 성공: " + result?.toString())

                    when (result?.code) {
                        200 -> {
                            val Schedulelist = ArrayList<HomePetownerScheduleData>()
                            for (item in result.result) {
                                val time = item.time.split(":")[0] + ":" + item.time.split(":")[1]
                                Schedulelist.add(HomePetownerScheduleData(time, ("${item.name}:${item.schedulename}"), item.detail))
                            }

                            val boardAdapterScheduleList = HomePetownerScheduleAdapter(Schedulelist)
                            boardAdapterScheduleList.notifyDataSetChanged()
                            //스케쥴 눌렀을 때 나오는 화면 구성해야 할듯
                            binding.rcvHavepetSchedule.adapter = boardAdapterScheduleList
                            binding.rcvHavepetSchedule.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

                            binding.rcvHavepetWeather.addItemDecoration(HorizontalItemDecorator(10))
                        }

                        else -> {
                            Log.d(TAG, "onResponse: ㅈ버그발생 보내는 데이터가 문제임 ")
                        }
                    }

                } else {
                    // 통신이 실패한 경우(응답 코드 3xx, 4xx 등)
                    Log.d(TAG, "onResponse 실패" + response.code())
                }
            }

            override fun onFailure(call: Call<PetScheduleInterfaceResponse>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d(TAG, "onFailure 에러: " + t.message.toString())
            }
        })
    }

    private fun showPoppup(v: View) {
        val popup = PopupMenu(activity?.applicationContext, v)
        for (i in telArray.indices) {
            popup.menu.add(Menu.NONE, i, Menu.NONE, telArray[i])
        }

        popup.setOnMenuItemClickListener { item ->
            val intent: Intent
            when (item?.itemId) {
                item.itemId -> {
                    item.title = telArray[item.itemId]
                    intent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + telArray[item.itemId]))
                    startActivity(intent)
                }
            }
            false
        }
        popup.show()
    }

    override fun onResume() {
        super.onResume()
//        (context as FragmentActivity).supportFragmentManager.beginTransaction().detach(this).commit()
//        (context as FragmentActivity).supportFragmentManager.beginTransaction().attach(this).commit()
    }
}