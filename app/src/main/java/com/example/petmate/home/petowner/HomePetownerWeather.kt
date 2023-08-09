package com.example.petmate.home.petowner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.R

class HomePetownerWeather(val itemList: ArrayList<HomePetownerWeatherData>) : RecyclerView.Adapter<HomePetownerWeather.HavepetWeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HavepetWeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_home_petowner_weather_layout, parent, false)
        return HavepetWeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: HavepetWeatherViewHolder, position: Int) {
        holder.weather_tempeature.text = itemList[position].weather_tempeature
        holder.weather_time.text = itemList[position].weather_time
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }


    inner class HavepetWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val weather_tempeature = itemView.findViewById<TextView>(R.id.havepet_weather_temperature)
        val weather_time = itemView.findViewById<TextView>(R.id.havepet_weather_time)
    }
}