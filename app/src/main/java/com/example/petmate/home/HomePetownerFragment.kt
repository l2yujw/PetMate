package com.example.petmate.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.petmate.R

class HomePetownerFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var rootView = inflater.inflate(R.layout.fragment_home_petowner, container, false)

        val rv_board_petlist = rootView.findViewById<ViewPager2>(R.id.viewpager_havingpet_petlist)
        val rv_board_schedule = rootView.findViewById<RecyclerView>(R.id.rcv_havepet_schedule)
        val rv_board_weather = rootView.findViewById<RecyclerView>(R.id.rcv_havepet_weather)

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
        rv_board_schedule.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

        rv_board_weather.adapter = boardAdapterWeatherList
        rv_board_weather.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        return rootView
    }
}