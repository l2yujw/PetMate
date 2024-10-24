package com.example.petmate.ui.home.petowner.weather.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.R
import com.example.petmate.ui.home.petowner.weather.data.HomePetownerWeatherData

class HomePetownerWeatherAdapter(val itemList: Array<HomePetownerWeatherData>) : RecyclerView.Adapter<HomePetownerWeatherAdapter.HavepetWeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HavepetWeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_petowner_weather2, parent, false)
        return HavepetWeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: HavepetWeatherViewHolder, position: Int) {
        val item = itemList[position]
        holder.setItem(item)
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }


    inner class HavepetWeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun setItem(item: HomePetownerWeatherData) {
            val Weather_img = itemView.findViewById<ImageView>(R.id.petowner_weather_image)         // 날씨 이미지
            val weather_Time = itemView.findViewById<TextView>(R.id.petowner_weather_time)          // 시각
            val weather_Temp = itemView.findViewById<TextView>(R.id.petowner_weather_temperature)   // 온도


            Weather_img.setImageResource(getRainImage(item.rainType, item.sky))
            weather_Time.text = getTime(item.fcstTime)
            weather_Temp.text = item.temp + "℃"
        }
    }

    fun getTime(factTime: String): String {
        if (factTime != "지금") {
            var hourSystem: Int = factTime.toInt()
            var hourSystemString = ""


            if (hourSystem == 0) {
                return "오전 12시"
            } else if (hourSystem > 2100) {
                hourSystem -= 1200
                hourSystemString = hourSystem.toString()
                return "오후 ${hourSystemString[0]}${hourSystemString[1]}시"


            } else if (hourSystem == 1200) {
                return "오후 12시"
            } else if (hourSystem > 1200) {
                hourSystem -= 1200
                hourSystemString = hourSystem.toString()
                return "오후 ${hourSystemString[0]}시"

            } else if (hourSystem >= 1000) {
                hourSystemString = hourSystem.toString()

                return "오전 ${hourSystemString[0]}${hourSystemString[1]}시"
            } else {

                hourSystemString = hourSystem.toString()

                return "오전 ${hourSystemString[0]}시"

            }

        } else {
            return factTime
        }


    }

    // 강수 형태
    fun getRainImage(rainType: String, sky: String): Int {
        return when (rainType) {
            "0" -> getWeatherImage(sky)             // 없음
            "1" -> R.drawable.weather_rainy         // 비
            "2" -> R.drawable.weather_rain_or_snow  // 비/눈
            "3" -> R.drawable.weather_snow          // 눈
            "4" -> R.drawable.weather_brash         // 소나기
            else -> getWeatherImage(sky)
        }
    }

    fun getWeatherImage(sky: String): Int {
        // 하늘 상태
        return when (sky) {
            "1" -> R.drawable.weather_sunny             // 맑음
            "3" -> R.drawable.weather_little_cloudy     // 구름 많음
            "4" -> R.drawable.weather_cloudy            // 흐림
            else -> R.drawable.ic_launcher_foreground   // 오류


        }
    }
}