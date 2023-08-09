package com.example.petmate.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.petmate.home.petowner.HomePetownerFragment
import com.example.petmate.R
import com.example.petmate.community.CommunityFragment
import com.example.petmate.databinding.ActivityBottomNavBinding
import com.example.petmate.myinf.MyinfFragment
import com.example.petmate.pet.main.PetMainFragment

class BottomNavActivity : AppCompatActivity() {
    private val fragmentManager = supportFragmentManager

    private lateinit var binding : ActivityBottomNavBinding

    private var homeHavingPetFragment: HomePetownerFragment? = null
    private var petMainFragment: PetMainFragment? = null
    private var communityFragment: CommunityFragment? = null
    private var myinfFragment: MyinfFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_bottom_nav)
        binding.lifecycleOwner = this

        initBottomNavigation()
    }

    private fun initBottomNavigation(){
        // 최초로 보이는 프래그먼트
        homeHavingPetFragment = HomePetownerFragment()
        fragmentManager.beginTransaction().replace(R.id.fragmentContainerView,homeHavingPetFragment!!).commit()

        binding.bnvMain.setOnItemSelectedListener {

            // 최초 선택 시 fragment add, 선택된 프래그먼트 show, 나머지 프래그먼트 hide
            when(it.itemId){
                R.id.tab_home ->{
                    if(homeHavingPetFragment == null){
                        homeHavingPetFragment = HomePetownerFragment()
                        fragmentManager.beginTransaction().add(R.id.fragmentContainerView,homeHavingPetFragment!!).commit()
                    }
                    if(homeHavingPetFragment != null) fragmentManager.beginTransaction().show(homeHavingPetFragment!!).commit()
                    if(petMainFragment != null) fragmentManager.beginTransaction().hide(petMainFragment!!).commit()
                    if(communityFragment != null) fragmentManager.beginTransaction().hide(communityFragment!!).commit()
                    if(myinfFragment != null) fragmentManager.beginTransaction().hide(myinfFragment!!).commit()

                    return@setOnItemSelectedListener true
                }
                R.id.tab_pet ->{
                    if(petMainFragment == null){
                        petMainFragment = PetMainFragment()
                        fragmentManager.beginTransaction().add(R.id.fragmentContainerView,petMainFragment!!).commit()
                    }
                    if(homeHavingPetFragment != null) fragmentManager.beginTransaction().hide(homeHavingPetFragment!!).commit()
                    if(petMainFragment != null) fragmentManager.beginTransaction().show(petMainFragment!!).commit()
                    if(communityFragment != null) fragmentManager.beginTransaction().hide(communityFragment!!).commit()
                    if(myinfFragment != null) fragmentManager.beginTransaction().hide(myinfFragment!!).commit()

                    return@setOnItemSelectedListener true
                }
                R.id.tab_community ->{
                    if(communityFragment == null){
                        communityFragment = CommunityFragment()
                        fragmentManager.beginTransaction().add(R.id.fragmentContainerView,communityFragment!!).commit()
                    }
                    if(homeHavingPetFragment != null) fragmentManager.beginTransaction().hide(homeHavingPetFragment!!).commit()
                    if(petMainFragment != null) fragmentManager.beginTransaction().hide(petMainFragment!!).commit()
                    if(communityFragment != null) fragmentManager.beginTransaction().show(communityFragment!!).commit()
                    if(myinfFragment != null) fragmentManager.beginTransaction().hide(myinfFragment!!).commit()

                    return@setOnItemSelectedListener true
                }
                R.id.tab_myInf ->{
                    if(myinfFragment == null){
                        myinfFragment = MyinfFragment()
                        fragmentManager.beginTransaction().add(R.id.fragmentContainerView,myinfFragment!!).commit()
                    }
                    if(homeHavingPetFragment != null) fragmentManager.beginTransaction().hide(homeHavingPetFragment!!).commit()
                    if(petMainFragment != null) fragmentManager.beginTransaction().hide(petMainFragment!!).commit()
                    if(communityFragment != null) fragmentManager.beginTransaction().hide(communityFragment!!).commit()
                    if(myinfFragment != null) fragmentManager.beginTransaction().show(myinfFragment!!).commit()

                    return@setOnItemSelectedListener true
                }
                else ->{
                    return@setOnItemSelectedListener true
                }
            }
        }
    }
}