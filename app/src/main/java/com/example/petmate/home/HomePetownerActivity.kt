package com.example.petmate.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.petmate.R

class HomePetownerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_petowner)

        val rv_board_petlist = findViewById<ViewPager2>(R.id.viewpager_havingpet_petlist)
        val rv_board_schedule = findViewById<RecyclerView>(R.id.rcv_havepet_schedule)
        val rv_board_weather = findViewById<RecyclerView>(R.id.rcv_havepet_weather)

        val PetList = ArrayList<HomePetownerPetlistData>()
        val ScheduleList = ArrayList<HomePetownerScheduleData>()
        val WeatherList = ArrayList<HomePetownerWeatherData>()

        PetList.add(HomePetownerPetlistData("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"))
        PetList.add(HomePetownerPetlistData("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"))
        PetList.add(HomePetownerPetlistData("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"))

        ScheduleList.add(HomePetownerScheduleData("08:00 am", "MainText", "SubText"))
        ScheduleList.add(HomePetownerScheduleData("08:00 am", "MainText", "SubText"))
        ScheduleList.add(HomePetownerScheduleData("08:00 am", "MainText", "SubText"))

        WeatherList.add(HomePetownerWeatherData("28℃", "17시"))
        WeatherList.add(HomePetownerWeatherData("27℃", "18시"))
        WeatherList.add(HomePetownerWeatherData("26℃", "19시"))

        val boardAdapterPetList = HomePetownerPetlist(PetList)
        boardAdapterPetList.notifyDataSetChanged()
        val boardAdapterScheduleList = HomePetownerSchedule(ScheduleList)
        boardAdapterScheduleList.notifyDataSetChanged()
        val boardAdapterWeatherList = HomePetownerWeather(WeatherList)
        boardAdapterWeatherList.notifyDataSetChanged()

//        rv_board_petlist.adapter = boardAdapterPetList
        rv_board_petlist.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        rv_board_schedule.adapter = boardAdapterScheduleList
        rv_board_schedule.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        rv_board_weather.adapter = boardAdapterWeatherList
        rv_board_weather.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }
}