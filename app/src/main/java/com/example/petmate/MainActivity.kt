package com.example.petmate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bnv_main = findViewById<BottomNavigationView>(R.id.bnv_main)

        bnv_main.setOnItemSelectedListener { item ->
            changeFragment(
                when (item.itemId) {
                    R.id.tab_home -> {
                        FragmentOneTest()
                        // Respond to navigation item 1 click
                    }
                    R.id.tab_pet -> {
                        FragmentTwoTest()
                        // Respond to navigation item 2 click
                    }
                    R.id.tab_community -> {
                        FragmentOneTest()
                        // Respond to navigation item 3 click
                    }
                    else -> {
                        FragmentTwoTest()
                    }
                }
            )
            true
        }

//        setContentView(R.layout.activity_main_home_havepet)
//
//        val rv_board_petlist = findViewById<ViewPager2>(R.id.havepet_petlist_viewpager)
//        val rv_board_schedule = findViewById<RecyclerView>(R.id.havepet_schedule)
//        val rv_board_weather = findViewById<RecyclerView>(R.id.havepet_weather)
//
//        val PetList = ArrayList<MainHomeHavepetPetlistData>()
//        val ScheduleList = ArrayList<MainHomeHavepetScheduleData>()
//        val WeatherList = ArrayList<MainHomeHavepetWeatherData>()
//
//        PetList.add(MainHomeHavepetPetlistData("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"))
//        PetList.add(MainHomeHavepetPetlistData("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"))
//        PetList.add(MainHomeHavepetPetlistData("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"))
//
//        ScheduleList.add(MainHomeHavepetScheduleData("08:00 am", "MainText", "SubText"))
//        ScheduleList.add(MainHomeHavepetScheduleData("08:00 am", "MainText", "SubText"))
//        ScheduleList.add(MainHomeHavepetScheduleData("08:00 am", "MainText", "SubText"))
//
//        WeatherList.add(MainHomeHavepetWeatherData("28℃", "17시"))
//        WeatherList.add(MainHomeHavepetWeatherData("27℃", "18시"))
//        WeatherList.add(MainHomeHavepetWeatherData("26℃", "19시"))
//
//        val boardAdapterPetList = MainHomeHavepetPetlist(PetList)
//        boardAdapterPetList.notifyDataSetChanged()
//        val boardAdapterScheduleList = MainHomeHavepetSchedule(ScheduleList)
//        boardAdapterScheduleList.notifyDataSetChanged()
//        val boardAdapterWeatherList = MainHomeHavepetWeather(WeatherList)
//        boardAdapterWeatherList.notifyDataSetChanged()
//
////        rv_board_petlist.adapter = boardAdapterPetList
//        rv_board_petlist.orientation = ViewPager2.ORIENTATION_HORIZONTAL
//
//        rv_board_schedule.adapter = boardAdapterScheduleList
//        rv_board_schedule.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//
//        rv_board_weather.adapter = boardAdapterWeatherList
//        rv_board_weather.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }
    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_container, fragment)
            .commit()
    }
}