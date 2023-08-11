package com.example.petmate.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
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
    private val fragmentManager = supportFragmentManager

    private lateinit var binding: ActivityBottomNavBinding

    private var homePetownerFragment: HomePetownerFragment? = null
    private var petMainFragment: PetMainFragment? = null
    private var communityFragment: CommunityFragment? = null
    private var myinfFragment: MyinfFragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_bottom_nav)
        binding.lifecycleOwner = this

//        initBottomNavigation()

        setNavigation()
    }

    private fun setNavigation() {

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val navigator = KeepStateFragment(this, navHostFragment.childFragmentManager, R.id.nav_host_fragment)

        navController.navigatorProvider.addNavigator(navigator)

        navController.setGraph(R.navigation.nav_graph)

        // MainActivity의 main_navi와 navController 연결
        binding.mainNavi.setupWithNavController(navController)

    }

    private fun initBottomNavigation() {
        // 최초로 보이는 프래그먼트
        homePetownerFragment = HomePetownerFragment()
        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, homePetownerFragment!!).commit()

        binding.mainNavi.setOnItemSelectedListener {

            // 최초 선택 시 fragment add, 선택된 프래그먼트 show, 나머지 프래그먼트 hide
            when (it.itemId) {
                R.id.tab_home -> {
                    if (homePetownerFragment == null) {
                        homePetownerFragment = HomePetownerFragment()
                        fragmentManager.beginTransaction().add(R.id.nav_host_fragment, homePetownerFragment!!).commit()
                    }
                    if (homePetownerFragment != null) fragmentManager.beginTransaction().show(homePetownerFragment!!).commit()
                    if (petMainFragment != null) fragmentManager.beginTransaction().hide(petMainFragment!!).commit()
                    if (communityFragment != null) fragmentManager.beginTransaction().hide(communityFragment!!).commit()
                    if (myinfFragment != null) fragmentManager.beginTransaction().hide(myinfFragment!!).commit()

                    return@setOnItemSelectedListener true
                }

                R.id.tab_pet -> {
                    if (petMainFragment == null) {
                        petMainFragment = PetMainFragment()
                        fragmentManager.beginTransaction().add(R.id.nav_host_fragment, petMainFragment!!).commit()
                    }
                    if (homePetownerFragment != null) fragmentManager.beginTransaction().hide(homePetownerFragment!!).commit()
                    if (petMainFragment != null) fragmentManager.beginTransaction().show(petMainFragment!!).commit()
                    if (communityFragment != null) fragmentManager.beginTransaction().hide(communityFragment!!).commit()
                    if (myinfFragment != null) fragmentManager.beginTransaction().hide(myinfFragment!!).commit()

                    return@setOnItemSelectedListener true
                }

                R.id.tab_community -> {
                    if (communityFragment == null) {
                        communityFragment = CommunityFragment()
                        fragmentManager.beginTransaction().add(R.id.nav_host_fragment, communityFragment!!).commit()
                    }
                    if (homePetownerFragment != null) fragmentManager.beginTransaction().hide(homePetownerFragment!!).commit()
                    if (petMainFragment != null) fragmentManager.beginTransaction().hide(petMainFragment!!).commit()
                    if (communityFragment != null) fragmentManager.beginTransaction().show(communityFragment!!).commit()
                    if (myinfFragment != null) fragmentManager.beginTransaction().hide(myinfFragment!!).commit()

                    return@setOnItemSelectedListener true
                }

                R.id.tab_myInf -> {
                    if (myinfFragment == null) {
                        myinfFragment = MyinfFragment()
                        fragmentManager.beginTransaction().add(R.id.nav_host_fragment, myinfFragment!!).commit()
                    }
                    if (homePetownerFragment != null) fragmentManager.beginTransaction().hide(homePetownerFragment!!).commit()
                    if (petMainFragment != null) fragmentManager.beginTransaction().hide(petMainFragment!!).commit()
                    if (communityFragment != null) fragmentManager.beginTransaction().hide(communityFragment!!).commit()
                    if (myinfFragment != null) fragmentManager.beginTransaction().show(myinfFragment!!).commit()

                    return@setOnItemSelectedListener true
                }

                else -> {
                    return@setOnItemSelectedListener true
                }
            }
        }
    }

}
