package com.example.petmate.home.petseeker

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.viewpager2.widget.ViewPager2
import com.example.petmate.Classifier
import com.example.petmate.GlobalUserIdx
import com.example.petmate.Tool
import com.example.petmate.databinding.FragmentHomePetseekerBinding
import retrofit2.Call
import retrofit2.Callback
import java.io.IOException
import java.util.Locale

class HomePetseekerFragment : Fragment() {

    lateinit var binding: FragmentHomePetseekerBinding
    private val TAG = "HomePetseekerFragment123"
    private lateinit var classifier: Classifier
    var telArray = arrayOf<String>("031-5189-7099", "031-228-3328")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = arguments
        val obj = bundle?.getString("isUser")
        Log.d("dddd", "petseekerobj$obj")
    }

    @SuppressLint("SetTextI18n", "MissingPermission")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomePetseekerBinding.inflate(inflater)

        val userIdx: Int = GlobalUserIdx.getUserIdx()

        binding.btnHomePetseekerAll.setOnClickListener { requestRecentPetList("%") }
        binding.btnHomePetseekerCat.setOnClickListener { requestRecentPetList("고양이") }
        binding.btnHomePetseekerDog.setOnClickListener { requestRecentPetList("개") }
        binding.btnHomePetseekerEtc.setOnClickListener { requestRecentPetList("기타축종") }


        val SpinnerItems = arrayOf("전체","남양동물보호센터", "수원시 동물보호센터")
        val adapterSpinner = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, SpinnerItems)
        binding.spinnerPetseeker.adapter = adapterSpinner
        binding.spinnerPetseeker.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작하게 됩니다.
                when (position) {
                    0 -> {
                        requestCareNmList(" ")
                    }

                    1 -> {
                        requestCareNmList(binding.spinnerPetseeker.selectedItem.toString())
                    }
                    2->{
                        requestCareNmList(binding.spinnerPetseeker.selectedItem.toString())
                    }
                    //...
                    else -> {
                        requestCareNmList(binding.spinnerPetseeker.selectedItem.toString())
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        binding.btnCenterNumber.setOnClickListener{
            //showPoppup(binding.btnCenterNumber)

            val intent: Intent
            val item = binding.spinnerPetseeker.selectedItemId
            if(item >0) {
                intent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + telArray[item.toInt()-1]))
                startActivity(intent)
            }else{
                Toast.makeText(context, "보호소를 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        requestPetRecommendList(userIdx)
        requestRecentPetList("%")

        return binding.root
    }

    private fun requestCareNmList(careNm:String){

        val retrofit = Tool.getRetrofit()

        val service = retrofit.create(HomePetseekerInterface::class.java)
        service.getCareNmList(careNm).enqueue(object : Callback<HomePetseekerRecommendPetListInterfaceResponse> {

            override fun onResponse(call: Call<HomePetseekerRecommendPetListInterfaceResponse>, response: retrofit2.Response<HomePetseekerRecommendPetListInterfaceResponse>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    val result: HomePetseekerRecommendPetListInterfaceResponse? = response.body()
                    Log.d(TAG, "onResponse 성공: " + result?.toString())

                    when (result?.code) {
                        200 -> {
                            val list = result.result
                            val bundle = arguments
                            val obj = bundle?.getString("isUser")

                            val boardAdapterPetList = HomePetseekerListAdapter(list, obj)
                            boardAdapterPetList.notifyDataSetChanged()

                            val indicatorList = binding.circleindicatorPetseekerPetlist

                            indicatorList.setViewPager(binding.viewpagerPetseekerList)
                            indicatorList.createIndicators(list.size / 3, 0)

                            binding.viewpagerPetseekerList.adapter = boardAdapterPetList
                            binding.viewpagerPetseekerList.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                            binding.viewpagerPetseekerList.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                                override fun onPageSelected(position: Int) {
                                    super.onPageSelected(position)
                                    indicatorList.animatePageSelected(position)
                                    //Toast.makeText(requireContext(), "${position + 1} 페이지 선택됨", Toast.LENGTH_SHORT).show()
                                }
                            })

                        }

                        else -> {
                            Log.d(TAG, "onResponse: ㅈ버그발생 보내는 데이터가 문제임 ")
                        }
                    }

                } else {
                    // 통신이 실패한 경우(응답 코드 3xx, 4xx 등)
                    Log.d(TAG, "onResponse 실패" + response.code())
                }
            }

            override fun onFailure(call: Call<HomePetseekerRecommendPetListInterfaceResponse>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d(TAG, "onFailure 에러: " + t.message.toString())
            }
        })
    }

    private fun requestPetRecommendList(userIdx: Int) {

        val retrofit = Tool.getRetrofit()

        val service = retrofit.create(HomePetseekerInterface::class.java)
        Log.d(TAG, "requestPetList: $userIdx")
        service.getRecommend(userIdx).enqueue(object : Callback<HomePetseekerRecommendInterfaceResponse> {

            override fun onResponse(call: Call<HomePetseekerRecommendInterfaceResponse>, response: retrofit2.Response<HomePetseekerRecommendInterfaceResponse>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    val result: HomePetseekerRecommendInterfaceResponse? = response.body()
                    Log.d(TAG, "onResponse 성공: " + result?.toString())

                    when (result?.code) {
                        200 -> {
                            val item = result.result[0]
                            if(item.recommend.isNotEmpty()) {
                                requestRecommendPetList(item.recommend)
                            }else{
                                requestRecommendPetList("@")
                            }
                        }

                        else -> {
                            Log.d(TAG, "getRecommend onResponse: ㅈ버그발생 보내는 데이터가 문제임 ")
                            requestRecommendPetList("@")
                        }
                    }

                } else {
                    // 통신이 실패한 경우(응답 코드 3xx, 4xx 등)
                    Log.d(TAG, "onResponse 실패" + response.code())
                }
            }

            override fun onFailure(call: Call<HomePetseekerRecommendInterfaceResponse>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d(TAG, "onFailure 에러: " + t.message.toString())
            }
        })
    }

    private fun requestRecentPetList(category:String) {

        val retrofit = Tool.getRetrofit()

        val service = retrofit.create(HomePetseekerInterface::class.java)
        service.getCategoryPetList(category).enqueue(object : Callback<HomePetseekerRecommendPetListInterfaceResponse> {

            override fun onResponse(call: Call<HomePetseekerRecommendPetListInterfaceResponse>, response: retrofit2.Response<HomePetseekerRecommendPetListInterfaceResponse>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    val result: HomePetseekerRecommendPetListInterfaceResponse? = response.body()
                    Log.d(TAG, "onResponse 성공: " + result?.toString())

                    when (result?.code) {
                        200 -> {
                            val list = result.result
                            val bundle = arguments
                            val obj = bundle?.getString("isUser")

                            val boardAdapterPetList = HomePetseekerListAdapter(list, obj)
                            boardAdapterPetList.notifyDataSetChanged()

                            val indicatorList = binding.circleindicatorPetseekerPetlist

                            indicatorList.setViewPager(binding.viewpagerPetseekerList)
                            indicatorList.createIndicators(list.size / 3, 0)

                            binding.viewpagerPetseekerList.adapter = boardAdapterPetList
                            binding.viewpagerPetseekerList.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                            binding.viewpagerPetseekerList.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                                override fun onPageSelected(position: Int) {
                                    super.onPageSelected(position)
                                    indicatorList.animatePageSelected(position)
                                    //Toast.makeText(requireContext(), "${position + 1} 페이지 선택됨", Toast.LENGTH_SHORT).show()
                                }
                            })
                        }
                        else -> {
                            Log.d(TAG, "onResponse: ㅈ버그발생 보내는 데이터가 문제임 ")
                        }
                    }
                } else {
                    // 통신이 실패한 경우(응답 코드 3xx, 4xx 등)
                    Log.d(TAG, "onResponse 실패" + response.code())
                }
            }

            override fun onFailure(call: Call<HomePetseekerRecommendPetListInterfaceResponse>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d(TAG, "onFailure 에러: " + t.message.toString())
            }
        })
    }

    private fun requestRecommendPetList(recommend: String) {

        val retrofit = Tool.getRetrofit()

        Log.d(TAG, "recommend : $recommend")
        val service = retrofit.create(HomePetseekerInterface::class.java)
        service.getRecommendPetList(recommend).enqueue(object : Callback<HomePetseekerRecommendPetListInterfaceResponse> {

            override fun onResponse(call: Call<HomePetseekerRecommendPetListInterfaceResponse>, response: retrofit2.Response<HomePetseekerRecommendPetListInterfaceResponse>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    val result: HomePetseekerRecommendPetListInterfaceResponse? = response.body()
                    Log.d(TAG, "getRecommendPetList onResponse 성공: " + result?.toString())

                    when (result?.code) {
                        200 -> {
                            val list = result.result
                            val bundle = arguments
                            val obj = bundle?.getString("isUser")
                            Log.d("dddd", "petseekrrecommendobj$obj")
                            val item = list[0]

                            var bitmap: Bitmap? = null
                            try {
                                bitmap = if (Build.VERSION.SDK_INT >= 28) {
                                    val src = ImageDecoder.createSource(requireContext().contentResolver, item.imageUrl.toUri())
                                    ImageDecoder.decodeBitmap(src)
                                } else {
                                    MediaStore.Images.Media.getBitmap(requireContext().contentResolver, item.imageUrl.toUri())
                                }
                            } catch (exception: IOException) {
                            }
                            bitmap?.let {
                                val output = classifier.classify(bitmap)
                                val resultStr =
                                    String.format(Locale.ENGLISH, "class : %s, prob : %.2f%%", output.first, output.second * 100)
                                binding.run {
                                    Log.d("dddd",output.first)
                                }
                            }


                            val boardAdapterRecommend = HomePetseekerRecommendAdapter(list, obj)

                            val indicatorReccomend = binding.circleindicatorPetseekerRecommend
                            boardAdapterRecommend.notifyDataSetChanged()

                            indicatorReccomend.setViewPager(binding.viewpagerPetseekerRecommend)
                            indicatorReccomend.createIndicators(list.size, 0)

                            binding.viewpagerPetseekerRecommend.adapter = boardAdapterRecommend
                            binding.viewpagerPetseekerRecommend.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                            binding.viewpagerPetseekerRecommend.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                                override fun onPageSelected(position: Int) {
                                    super.onPageSelected(position)
                                    indicatorReccomend.animatePageSelected(position)
                                    //Toast.makeText(requireContext(), "${position + 1} 페이지 선택됨", Toast.LENGTH_SHORT).show()
                                }
                            })

                        }

                        else -> {
                            Log.d(TAG, "getRecommendPetList onResponse: ㅈ버그발생 보내는 데이터가 문제임 ")
                        }
                    }

                } else {
                    // 통신이 실패한 경우(응답 코드 3xx, 4xx 등)
                    Log.d(TAG, "onResponse 실패" + response.code())
                }
            }

            override fun onFailure(call: Call<HomePetseekerRecommendPetListInterfaceResponse>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d(TAG, "onFailure 에러: " + t.message.toString())
            }
        })
    }
}