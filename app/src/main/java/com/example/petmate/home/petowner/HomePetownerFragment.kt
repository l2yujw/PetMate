package com.example.petmate.home.petowner

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.petmate.R
import com.example.petmate.VerticalItemDecorator

class HomePetownerFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var rootView = inflater.inflate(R.layout.fragment_home_petowner, container, false)


        val petTextList = ArrayList<HomePetownerPetlistData>()
        val scheduleList = ArrayList<HomePetownerScheduleData>()
        val weatherList = ArrayList<HomePetownerWeatherData>()

        petTextList.add(HomePetownerPetlistData("aaaaaa"))
        petTextList.add(HomePetownerPetlistData("bbbbbb"))
        petTextList.add(HomePetownerPetlistData("ccccc"))

        scheduleList.add(HomePetownerScheduleData("08:00 am", "MainText", "SubText"))
        scheduleList.add(HomePetownerScheduleData("08:00 am", "MainText", "SubText"))
        scheduleList.add(HomePetownerScheduleData("08:00 am", "MainText", "SubText"))

        weatherList.add(HomePetownerWeatherData("28℃", "17시"))
        weatherList.add(HomePetownerWeatherData("27℃", "18시"))
        weatherList.add(HomePetownerWeatherData("26℃", "19시"))

        val boardAdapterPetList = HomePetownerPetlistAdapter(getPetImageList(), petTextList)
        boardAdapterPetList.notifyDataSetChanged()
        val boardAdapterScheduleList = HomePetownerScheduleAdapter(scheduleList)
        boardAdapterScheduleList.notifyDataSetChanged()
        val boardAdapterWeatherList = HomePetownerWeatherAdapter(weatherList)
        boardAdapterWeatherList.notifyDataSetChanged()


        val viewPager_petlist = rootView.findViewById<ViewPager2>(R.id.viewpager_havingpet_petlist)
        val rv_board_schedule = rootView.findViewById<RecyclerView>(R.id.rcv_havepet_schedule)
        val rv_board_weather = rootView.findViewById<RecyclerView>(R.id.rcv_havepet_weather)

        viewPager_petlist.adapter = HomePetownerPetlistAdapter(getPetImageList(), petTextList)
        viewPager_petlist.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        rv_board_schedule.adapter = boardAdapterScheduleList
        rv_board_schedule.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

        rv_board_weather.adapter = boardAdapterWeatherList
        rv_board_weather.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rv_board_weather.addItemDecoration(VerticalItemDecorator(10))


        return rootView
    }

    private fun getPetImageList(): ArrayList<Int>{
        return arrayListOf<Int>(R.drawable.cat1_temp, R.drawable.cat2_temp, R.drawable.cat1_temp)
    }


}