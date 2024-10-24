package com.example.petmate.ui.navigation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.petmate.core.navigator.KeepStateFragment
import com.example.petmate.R
import com.example.petmate.databinding.ActivityBottomNavBinding
import com.example.petmate.core.util.GlobalUserIdx
import com.example.petmate.core.network.Tool
import com.example.petmate.remote.api.navigation.CheckUserPetInterface
import com.example.petmate.remote.response.navigation.PetRelationshipResponse
import retrofit2.Call
import retrofit2.Callback

class BottomNavActivity : AppCompatActivity(){

    private lateinit var binding: ActivityBottomNavBinding
    private val TAG = "BottomNavActivity123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: CREATE")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bottom_nav)
        binding.lifecycleOwner = this

        setNavigation()
    }

    private fun setNavigation() {
        val userIdx = GlobalUserIdx.getUserIdx()

        if(userIdx != 0){

            val retrofit = Tool.getRetrofit()

            val service = retrofit.create(CheckUserPetInterface::class.java)

            service.getReadByUserIdx(userIdx).enqueue(object : Callback<PetRelationshipResponse> {

                override fun onResponse(call: Call<PetRelationshipResponse>, response: retrofit2.Response<PetRelationshipResponse>) {
                    if(response.isSuccessful){
                        // 정상적으로 통신이 성고된 경우
                        val result: PetRelationshipResponse? = response.body()
                        Log.d(TAG, "onResponse 성공: " + result?.toString())

                        when (result?.code) {
                            200 -> {
                                Log.d(TAG, "onResponse: Success And have pet")
                                setOwner(userIdx)
//                                setSeeker()
                            }
                            201 -> {
                                Log.d(TAG, "onResponse: Success But don't have pet")
                                setSeeker()
                            }
                            else -> {
                                Toast.makeText(applicationContext, "ㅈ버그발생 보내는 데이터가 문제임", Toast.LENGTH_SHORT).show()
                                setSeeker()

                            }
                        }

                    }else{
                        // 통신이 실패한 경우(응답 코드 3xx, 4xx 등)
                        Toast.makeText(applicationContext, "Code: "+response.code(), Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "onResponse 실패")

                        setSeeker()

                    }
                }

                override fun onFailure(call: Call<PetRelationshipResponse>, t: Throwable) {
                    // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                    Toast.makeText(applicationContext, "통신 실패", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "onFailure 에러: " + t.message.toString())
                    setSeeker()
                }
            })
        }else{
            setSeeker()
        }
    }

    private fun setOwner(userIdx:Int) {

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bundle = Bundle()
        bundle.putInt("userIdx",userIdx)
        Log.d(TAG, "setOwner: $userIdx")
        navHostFragment.arguments = bundle

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

    @SuppressLint("ResourceType")
    private fun setSeeker() {

        binding.mainNavi.getMenu().clear()
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // 화면 전화 후에도 값 유지
        val navigator = KeepStateFragment(this, navHostFragment.childFragmentManager, R.id.nav_host_fragment)

        navController.navigatorProvider.addNavigator(navigator)

        // 네비게이션 시작 프래그먼트 변경
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)

        navController.setGraph(navGraph, null)

        binding.mainNavi.inflateMenu(R.menu.btm_navi_annoy_menu)

        // MainActivity의 main_navi와 navController 연결
        binding.mainNavi.setupWithNavController(navController)

    }
}
