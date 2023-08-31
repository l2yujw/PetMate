package com.example.petmate.walk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.petmate.databinding.ActivityWalkBinding
import net.daum.mf.map.api.MapView

class WalkActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWalkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapView = MapView(this)
        binding.KakaoMapView.addView(mapView)
    }
}