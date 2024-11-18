package com.example.petmate.ui.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.petmate.core.navigator.KeepStateFragmentNavigator
import com.example.petmate.R
import com.example.petmate.databinding.ActivityBottomNavAnonyBinding

class BottomNavAnonyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavAnonyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bottom_nav_anony)
        binding.lifecycleOwner = this

        setupNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment = setupNavController()
        configureNavGraph(navHostFragment)
    }

    private fun setupNavController(): NavHostFragment {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_sub_nav_host) as NavHostFragment
        val navController = navHostFragment.navController

        val navigator = KeepStateFragmentNavigator(this, navHostFragment.childFragmentManager, R.id.fragment_sub_nav_host)
        navController.navigatorProvider.addNavigator(navigator)

        binding.secondaryBottomNavigation.setupWithNavController(navController)
        return navHostFragment
    }

    private fun configureNavGraph(navHostFragment: NavHostFragment) {
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph).apply {
            setStartDestination(R.id.homePetseekerFragment)
        }
        navController.setGraph(navGraph, null)
    }
}
