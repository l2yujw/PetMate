package com.example.petmate.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.petmate.KeepStateFragment
import com.example.petmate.home.petowner.HomePetownerFragment
import com.example.petmate.R
import com.example.petmate.community.CommunityFragment
import com.example.petmate.databinding.ActivityBottomNavBinding
import com.example.petmate.myinf.MyinfFragment
import com.example.petmate.pet.main.PetMainFragment

class BottomNavActivity : AppCompatActivity(){

    private lateinit var binding: ActivityBottomNavBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_bottom_nav)
        binding.lifecycleOwner = this

        setNavigation()
    }

    private fun setNavigation() {

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // 화면 전화 후에도 값 유지
        val navigator = KeepStateFragment(this, navHostFragment.childFragmentManager, R.id.nav_host_fragment)

        navController.navigatorProvider.addNavigator(navigator)

        // 네비게이션 시작 프래그먼트 변경
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        navGraph.setStartDestination(R.id.homePetownerFragment)

        navController.setGraph(navGraph, null)

        // MainActivity의 main_navi와 navController 연결
        binding.mainNavi.setupWithNavController(navController)

    }
}
