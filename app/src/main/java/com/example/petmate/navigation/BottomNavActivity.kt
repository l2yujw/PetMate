package com.example.petmate.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.petmate.FragmentOneTest
import com.example.petmate.FragmentTwoTest
import com.example.petmate.home.HomeHavingPetFragment
import com.example.petmate.R
import com.example.petmate.databinding.ActivityBottomNavBinding

class BottomNavActivity : AppCompatActivity() {
    private val fragmentManager = supportFragmentManager

    private lateinit var binding : ActivityBottomNavBinding

    private var homeHavingPetFragment: HomeHavingPetFragment? = null
    private var fragmentTwoTest: FragmentTwoTest? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_bottom_nav)
        binding.lifecycleOwner = this

        initBottomNavigation()
    }

    private fun initBottomNavigation(){
        // 최초로 보이는 프래그먼트
        homeHavingPetFragment = HomeHavingPetFragment()
        fragmentManager.beginTransaction().replace(R.id.fragmentContainerView,homeHavingPetFragment!!).commit()

        binding.bnvMain.setOnItemSelectedListener {

            // 최초 선택 시 fragment add, 선택된 프래그먼트 show, 나머지 프래그먼트 hide
            when(it.itemId){
                R.id.tab_home ->{
                    if(homeHavingPetFragment == null){
                        homeHavingPetFragment = HomeHavingPetFragment()
                        fragmentManager.beginTransaction().add(R.id.fragmentContainerView,homeHavingPetFragment!!).commit()
                    }
                    if(homeHavingPetFragment != null) fragmentManager.beginTransaction().show(homeHavingPetFragment!!).commit()
                    if(fragmentTwoTest != null) fragmentManager.beginTransaction().hide(fragmentTwoTest!!).commit()

                    return@setOnItemSelectedListener true
                }
                R.id.tab_pet ->{
                    if(fragmentTwoTest == null){
                        fragmentTwoTest = FragmentTwoTest()
                        fragmentManager.beginTransaction().add(R.id.fragmentContainerView,fragmentTwoTest!!).commit()
                    }
                    if(homeHavingPetFragment != null) fragmentManager.beginTransaction().hide(homeHavingPetFragment!!).commit()
                    if(fragmentTwoTest != null) fragmentManager.beginTransaction().show(fragmentTwoTest!!).commit()

                    return@setOnItemSelectedListener true
                }
                else ->{
                    return@setOnItemSelectedListener true
                }
            }
        }
    }
}