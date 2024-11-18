package com.example.petmate.ui.navigation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.petmate.core.navigator.KeepStateFragmentNavigator
import com.example.petmate.R
import com.example.petmate.databinding.ActivityBottomNavBinding
import com.example.petmate.core.util.GlobalUserId
import com.example.petmate.core.network.NetworkClient
import com.example.petmate.remote.api.navigation.CheckUserPetService
import com.example.petmate.remote.response.navigation.PetRelationshipResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BottomNavActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavBinding
    private val TAG = "BottomNavActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bottom_nav)
        binding.lifecycleOwner = this

        setNavigation()
    }

    private fun setNavigation() {
        val userIdx = GlobalUserId.getUserId()

        if (userIdx != 0) {
            getPetRelationship(userIdx)
        } else {
            setupSeekerNavigation()
        }
    }

    private fun getPetRelationship(userIdx: Int) {
        val retrofit = NetworkClient.getRetrofit()
        val service = retrofit.create(CheckUserPetService::class.java)

        service.getReadByUserIdx(userIdx).enqueue(object : Callback<PetRelationshipResponse> {
            override fun onResponse(call: Call<PetRelationshipResponse>, response: Response<PetRelationshipResponse>) {
                if (response.isSuccessful) {
                    val result: PetRelationshipResponse? = response.body()
                    Log.d(TAG, "onResponse 성공: $result")

                    when (result?.code) {
                        200 -> {
                            Log.d(TAG, "onResponse: Success and have pet")
                            setupOwnerNavigation(userIdx)
                        }
                        201 -> {
                            Log.d(TAG, "onResponse: Success but don't have pet")
                            setupSeekerNavigation()
                        }
                        else -> {
                            showErrorAndFallback("Unknown error: ${result?.message}")
                        }
                    }
                } else {
                    showErrorAndFallback("Code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<PetRelationshipResponse>, t: Throwable) {
                showErrorAndFallback("통신 실패: ${t.message}")
            }
        })
    }

    private fun showErrorAndFallback(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        setupSeekerNavigation()
    }

    private fun setupOwnerNavigation(userIdx: Int) {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_nav_host) as NavHostFragment
        val navController = navHostFragment.navController

        val bundle = Bundle().apply {
            putInt("userIdx", userIdx)
        }
        navHostFragment.arguments = bundle

        val navigator = KeepStateFragmentNavigator(this, navHostFragment.childFragmentManager, R.id.fragment_nav_host)
        navController.navigatorProvider.addNavigator(navigator)

        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph).apply {
            setStartDestination(R.id.homePetownerFragment)
        }
        navController.setGraph(navGraph, null)

        binding.bottomNavigationView.setupWithNavController(navController)
    }

    @SuppressLint("ResourceType")
    private fun setupSeekerNavigation() {
        binding.bottomNavigationView.menu.clear()
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_nav_host) as NavHostFragment
        val navController = navHostFragment.navController

        val navigator = KeepStateFragmentNavigator(this, navHostFragment.childFragmentManager, R.id.fragment_nav_host)
        navController.navigatorProvider.addNavigator(navigator)

        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        navController.setGraph(navGraph, null)

        binding.bottomNavigationView.inflateMenu(R.menu.btm_navi_annoy_menu)
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}
