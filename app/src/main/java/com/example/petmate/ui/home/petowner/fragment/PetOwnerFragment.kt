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
import com.example.petmate.R
import com.example.petmate.core.decorator.HorizontalDividerItemDecorator
import com.example.petmate.core.network.NetworkClient
import com.example.petmate.core.util.GlobalUserId
import com.example.petmate.core.util.HomePetownerWeatherCommon
import com.example.petmate.core.util.PetIndexList
import com.example.petmate.databinding.FragmentHomePetownerBinding
import com.example.petmate.remote.api.home.petowner.PetOwnerService
import com.example.petmate.remote.api.weather.WeatherServiceFactory
import com.example.petmate.remote.response.home.petowner.PetOwnerResponse
import com.example.petmate.remote.response.home.petowner.PetScheduleResponse
import com.example.petmate.ui.home.petowner.adapter.PetOwnerPetListAdapter
import com.example.petmate.ui.home.petowner.adapter.PetOwnerScheduleAdapter
import com.example.petmate.ui.home.petowner.data.PetOwnerPetListData
import com.example.petmate.ui.home.petowner.data.PetOwnerScheduleData
import com.example.petmate.ui.home.petowner.weather.adapter.PetOwnerWeatherAdapter
import com.example.petmate.ui.home.petowner.weather.data.HomePetOwnerWeatherData
import com.example.petmate.ui.home.petowner.weather.data.Weather
import com.example.petmate.ui.walk.activity.WalkActivity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import me.relex.circleindicator.CircleIndicator3
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class PetOwnerFragment : Fragment() {

    private val TAG = "HomePetOwnerFragment"
    private lateinit var binding: FragmentHomePetownerBinding
    private lateinit var indicator: CircleIndicator3
    private var currentGridPoint: Point? = null
    private var viewpagerId = 0
    private val telArray = arrayOf("02-364-3517", "02-393-7577", "02-393-3588", "02-701-7580", "02-333-7750")

    @SuppressLint("SetTextI18n", "MissingPermission")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomePetownerBinding.inflate(inflater, container, false)
        indicator = binding.ciPetList.apply { setViewPager(binding.vpPetList) }

        requestPermissions()
        setupViewPager()
        fetchPetList(GlobalUserId.getUserId())
        fetchScheduleList(GlobalUserId.getUserId())
        fetchLocationData()

        setupClickListeners()

        return binding.root
    }

    private fun requestPermissions() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
            1
        )
    }

    private fun setupViewPager() {
        binding.vpPetList.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.vpPetList.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                indicator.animatePageSelected(position)
                viewpagerId = position
            }
        })
    }

    private fun setupClickListeners() {
        binding.btnStartWalk.setOnClickListener {
            Intent(requireContext(), WalkActivity::class.java).apply {
                putExtra("petIdx", PetIndexList.get()[viewpagerId])
                startActivity(this)
            }
        }

        binding.btnCallEmergency.setOnClickListener { showPopupMenu(it) }

        binding.btnViewPetSeeker.setOnClickListener {
            val bundle = Bundle().apply { putString("isUser", "isUser") }
            findNavController().navigate(R.id.action_homePetownerFragment_to_homePetseekerFragment, bundle)
        }
    }

    private fun fetchLocationData() {
        val locationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 60 * 1000
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.locations.firstOrNull()?.let { location ->
                    currentGridPoint = HomePetownerWeatherCommon().dfsXyConv(location.latitude, location.longitude)
                    currentGridPoint?.let { fetchWeatherData(it.x, it.y) }
                }
            }
        }

        try {
            Looper.myLooper()?.let {
                locationClient.requestLocationUpdates(locationRequest, locationCallback, it)
            }
        } catch (e: SecurityException) {
            Log.e(TAG, "Location permission error: ${e.message}")
        }
    }

    private fun fetchWeatherData(nx: Int, ny: Int) {
        val currentTime = Calendar.getInstance()
        val weatherBaseDate = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(currentTime.time)
        val weatherBaseTime = HomePetownerWeatherCommon().getBaseTime(SimpleDateFormat("HH", Locale.getDefault()).format(currentTime.time))

        WeatherServiceFactory.createWeatherService().getWeather(1, 300, "JSON", weatherBaseDate, weatherBaseTime, nx, ny)
            .enqueue(object : Callback<Weather> {
                override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            val weatherArr = parseWeatherData()
                            updateWeatherUI(weatherArr)
                        }
                    } else {
                        Log.e(TAG, "Weather API response failed")
                    }
                }

                override fun onFailure(call: Call<Weather>, t: Throwable) {
                    Log.e(TAG, "Weather API call failed: ${t.message}")
                }
            })
    }

    private fun parseWeatherData(): Array<HomePetOwnerWeatherData> {
        return Array(8) { HomePetOwnerWeatherData() }
    }

    private fun updateWeatherUI(weatherArr: Array<HomePetOwnerWeatherData>) {
        binding.rvWeatherForecast.adapter = PetOwnerWeatherAdapter(weatherArr)
    }

    private fun fetchPetList(userIdx: Int) {
        val service = NetworkClient.getRetrofit().create(PetOwnerService::class.java)
        service.getPetList(userIdx).enqueue(object : Callback<PetOwnerResponse> {
            override fun onResponse(call: Call<PetOwnerResponse>, response: Response<PetOwnerResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { petResponse ->
                        val petList = petResponse.result.map {
                            PetOwnerPetListData(generateRandomText(), it.image)
                        }
                        setupPetList(petList)
                    }
                }
            }

            override fun onFailure(call: Call<PetOwnerResponse>, t: Throwable) {
                Log.e(TAG, "Pet List API call failed: ${t.message}")
            }
        })
    }

    private fun generateRandomText(): String {
        return listOf(
            "오늘은 산책하기 좋은 날씨에요!",
            "오늘은 집에서 쉬고 싶어요~",
            "오늘은 다른 친구들과 함께 놀고 싶어요!",
            "간식이 먹고 싶어요~"
        ).random()
    }

    private fun setupPetList(petList: List<PetOwnerPetListData>) {
        val petListAdapter = PetOwnerPetListAdapter(ArrayList(petList))
        binding.vpPetList.adapter = petListAdapter
        indicator.createIndicators(petList.size, 0)
    }

    private fun fetchScheduleList(userIdx: Int) {
        val service = NetworkClient.getRetrofit().create(PetOwnerService::class.java)
        val getDate = "2023-09-10"

        service.getPetScheduleList(userIdx, getDate).enqueue(object : Callback<PetScheduleResponse> {
            override fun onResponse(call: Call<PetScheduleResponse>, response: Response<PetScheduleResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { scheduleResponse ->
                        val scheduleList = scheduleResponse.result.map {
                            PetOwnerScheduleData(it.time, "${it.name}:${it.schedulename}", it.detail)
                        }
                        setupScheduleList(scheduleList)
                    }
                }
            }

            override fun onFailure(call: Call<PetScheduleResponse>, t: Throwable) {
                Log.e(TAG, "Schedule List API call failed: ${t.message}")
            }
        })
    }

    private fun setupScheduleList(scheduleList: List<PetOwnerScheduleData>) {
        val scheduleListAdapter = PetOwnerScheduleAdapter(ArrayList(scheduleList))
        binding.rvPetSchedule.adapter = scheduleListAdapter
        binding.rvPetSchedule.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPetSchedule.addItemDecoration(HorizontalDividerItemDecorator(10))
    }

    private fun showPopupMenu(view: View) {
        PopupMenu(requireContext(), view).apply {
            telArray.forEachIndexed { index, number ->
                menu.add(Menu.NONE, index, Menu.NONE, number)
            }
            setOnMenuItemClickListener { item ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:${telArray[item.itemId]}"))
                startActivity(intent)
                true
            }
            show()
        }
    }
}
