package com.example.petmate.ui.home.petowner.weather.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.R
import com.example.petmate.ui.home.petowner.weather.data.HomePetOwnerWeatherData

class PetOwnerWeatherAdapter(private val itemList: Array<HomePetOwnerWeatherData>) : RecyclerView.Adapter<PetOwnerWeatherAdapter.WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_petowner_weather2, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = itemList.size

    inner class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val weatherImage: ImageView = itemView.findViewById(R.id.petowner_weather_image)
        private val weatherTime: TextView = itemView.findViewById(R.id.petowner_weather_time)
        private val weatherTemp: TextView = itemView.findViewById(R.id.petowner_weather_temperature)

        @SuppressLint("SetTextI18n")
        fun bind(item: HomePetOwnerWeatherData) {
            weatherImage.setImageResource(getRainImageResource(item.rainType, item.sky))
            weatherTime.text = formatTime(item.fcstTime ?: "지금")
            weatherTemp.text = "${item.temp}℃"
        }
    }

    // 시간 포맷팅 메서드
    private fun formatTime(fcstTime: String): String {
        if (fcstTime == "지금") return fcstTime

        val hour = fcstTime.toIntOrNull() ?: return fcstTime
        val isAfternoon = hour >= 1200
        val formattedHour = when {
            hour == 0 -> 12
            hour > 1200 -> hour - 1200
            else -> hour / 100
        }

        val period = if (isAfternoon) "오후" else "오전"
        return "$period $formattedHour 시"
    }

    // 강수 상태에 따른 이미지 리소스 반환
    private fun getRainImageResource(rainType: String?, sky: String?): Int {
        return when (rainType) {
            "0" -> getSkyImageResource(sky)
            "1" -> R.drawable.weather_rainy
            "2" -> R.drawable.weather_rain_or_snow
            "3" -> R.drawable.weather_snow
            "4" -> R.drawable.weather_brash
            else -> getSkyImageResource(sky)
        }
    }

    // 하늘 상태에 따른 이미지 리소스 반환
    private fun getSkyImageResource(sky: String?): Int {
        return when (sky) {
            "1" -> R.drawable.weather_sunny
            "3" -> R.drawable.weather_little_cloudy
            "4" -> R.drawable.weather_cloudy
            else -> R.drawable.ic_launcher_foreground // 기본 오류 이미지
        }
    }
}
