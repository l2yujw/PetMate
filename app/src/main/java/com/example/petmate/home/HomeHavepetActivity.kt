package com.example.petmate.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.petmate.R

class HomeHavepetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_havingpet)

        val rv_board_petlist = findViewById<ViewPager2>(R.id.viewpager_havingpet_petlist)
        val rv_board_schedule = findViewById<RecyclerView>(R.id.rcv_havepet_schedule)
        val rv_board_weather = findViewById<RecyclerView>(R.id.rcv_havepet_weather)

        val PetList = ArrayList<HomeHavepetPetlistData>()
        val ScheduleList = ArrayList<HomeHavepetScheduleData>()
        val WeatherList = ArrayList<HomeHavepetWeatherData>()

        PetList.add(HomeHavepetPetlistData("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"))
        PetList.add(HomeHavepetPetlistData("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"))
        PetList.add(HomeHavepetPetlistData("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"))

        ScheduleList.add(HomeHavepetScheduleData("08:00 am", "MainText", "SubText"))
        ScheduleList.add(HomeHavepetScheduleData("08:00 am", "MainText", "SubText"))
        ScheduleList.add(HomeHavepetScheduleData("08:00 am", "MainText", "SubText"))

        WeatherList.add(HomeHavepetWeatherData("28℃", "17시"))
        WeatherList.add(HomeHavepetWeatherData("27℃", "18시"))
        WeatherList.add(HomeHavepetWeatherData("26℃", "19시"))

        val boardAdapterPetList = HomeHavepetPetlist(PetList)
        boardAdapterPetList.notifyDataSetChanged()
        val boardAdapterScheduleList = HomeHavepetSchedule(ScheduleList)
        boardAdapterScheduleList.notifyDataSetChanged()
        val boardAdapterWeatherList = HomeHavepetWeather(WeatherList)
        boardAdapterWeatherList.notifyDataSetChanged()

//        rv_board_petlist.adapter = boardAdapterPetList
        rv_board_petlist.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        rv_board_schedule.adapter = boardAdapterScheduleList
        rv_board_schedule.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        rv_board_weather.adapter = boardAdapterWeatherList
        rv_board_weather.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }
}