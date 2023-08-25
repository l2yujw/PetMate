package com.example.petmate.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.petmate.KeepStateFragment
import com.example.petmate.R
import com.example.petmate.databinding.ActivityBottomNavAnonyBinding
import com.example.petmate.databinding.ActivityBottomNavBinding

class BottomNavAnonyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavAnonyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_bottom_nav_anony)
        binding.lifecycleOwner = this

        setNavigation()
    }

    private fun setNavigation() {

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_annoy_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // 화면 전화 후에도 값 유지
        val navigator = KeepStateFragment(this, navHostFragment.childFragmentManager, R.id.nav_host_annoy_fragment)

        navController.navigatorProvider.addNavigator(navigator)

        // 네비게이션 시작 프래그먼트 변경
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        navGraph.setStartDestination(R.id.homePetseekerFragment)

        navController.setGraph(navGraph, null)

        // MainActivity의 main_navi와 navController 연결
        binding.subNavi.setupWithNavController(navController)
    }
}