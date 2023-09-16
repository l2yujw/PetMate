package com.example.petmate.home.petseeker

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.viewpager2.widget.ViewPager2
import com.example.petmate.GlobalUserIdx
import com.example.petmate.databinding.FragmentHomePetseekerBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import kotlin.concurrent.thread

class HomePetseekerFragment : Fragment() {

    lateinit var binding: FragmentHomePetseekerBinding
    private val TAG = "HomePetseekerFragment123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n", "MissingPermission")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomePetseekerBinding.inflate(inflater)

        val userIdx: Int = GlobalUserIdx.getUserIdx()

        binding.btnHomePetseekerAll.setOnClickListener { requestRecentPetList("%") }
        binding.btnHomePetseekerCat.setOnClickListener { requestRecentPetList("고양이") }
        binding.btnHomePetseekerDog.setOnClickListener { requestRecentPetList("개") }
        binding.btnHomePetseekerEtc.setOnClickListener { requestRecentPetList("기타축종") }


        val SpinnerItems = arrayOf("센터1", "센터2", "센터3")
        val adapterSpinner = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, SpinnerItems)
        binding.spinnerPetseeker.adapter = adapterSpinner
        binding.spinnerPetseeker.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작하게 됩니다.
                when (position) {
                    0 -> {
                    }

                    1 -> {
                    }
                    //...
                    else -> {
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        binding.btnCenterNumber.setOnClickListener{

        }

        requestPetRecommendList(userIdx)
        requestRecentPetList("%")

        return binding.getRoot()
    }

    private fun requestPetRecommendList(userIdx: Int) {
        //고정
        val retrofit = Retrofit.Builder()
            .baseUrl("http://13.124.16.204:3000/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        //
        val service = retrofit.create(HomePetseekerInterface::class.java);
        Log.d(TAG, "requestPetList: ${userIdx}")
        service.getRecommend(userIdx).enqueue(object : Callback<HomePetseekerRecommendInterfaceResponse> {

            override fun onResponse(call: Call<HomePetseekerRecommendInterfaceResponse>, response: retrofit2.Response<HomePetseekerRecommendInterfaceResponse>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    val result: HomePetseekerRecommendInterfaceResponse? = response.body()
                    Log.d(TAG, "onResponse 성공: " + result?.toString());

                    when (result?.code) {
                        200 -> {
                            val item = result.result[0]
                            requestRecommendPetList(item.recommend)
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

            override fun onFailure(call: Call<HomePetseekerRecommendInterfaceResponse>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d(TAG, "onFailure 에러: " + t.message.toString());
            }
        })
    }

    private fun requestRecentPetList(category:String) {
        //고정
        val retrofit = Retrofit.Builder()
            .baseUrl("http://13.124.16.204:3000/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        //
        val service = retrofit.create(HomePetseekerInterface::class.java);
        service.getCategoryPetList(category).enqueue(object : Callback<HomePetseekerRecommendPetListInterfaceResponse> {

            override fun onResponse(call: Call<HomePetseekerRecommendPetListInterfaceResponse>, response: retrofit2.Response<HomePetseekerRecommendPetListInterfaceResponse>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    val result: HomePetseekerRecommendPetListInterfaceResponse? = response.body()
                    Log.d(TAG, "onResponse 성공: " + result?.toString());

                    when (result?.code) {
                        200 -> {
                            val list = result.result

                            val boardAdapterPetList = HomePetseekerListAdapter(list)
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
                Log.d(TAG, "onFailure 에러: " + t.message.toString());
            }
        })
    }

    private fun requestRecommendPetList(recommend: String) {
        //고정
        val retrofit = Retrofit.Builder()
            .baseUrl("http://13.124.16.204:3000/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        //
        Log.d(TAG, "recommend : ${recommend}")
        val service = retrofit.create(HomePetseekerInterface::class.java);
        service.getRecommendPetList(recommend).enqueue(object : Callback<HomePetseekerRecommendPetListInterfaceResponse> {

            override fun onResponse(call: Call<HomePetseekerRecommendPetListInterfaceResponse>, response: retrofit2.Response<HomePetseekerRecommendPetListInterfaceResponse>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    val result: HomePetseekerRecommendPetListInterfaceResponse? = response.body()
                    Log.d(TAG, "onResponse 성공: " + result?.toString());

                    when (result?.code) {
                        200 -> {
                            val list = result.result

                            val boardAdapterRecommend = HomePetseekerRecommendAdapter(list)

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
                Log.d(TAG, "onFailure 에러: " + t.message.toString());
            }
        })
    }

    private fun getPetList(): ArrayList<HomePetseekerListData> {
        val petList = ArrayList<HomePetseekerListData>()

        petList.add(
            HomePetseekerListData(
                "https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg", "a", "수컷", "흰색, 갈색", "a",
            )
        )
        petList.add(
            HomePetseekerListData(
                "https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg", "믹스견", "암컷", "흰색", "b",
            )
        )
        petList.add(
            HomePetseekerListData(
                "https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg", "믹스견", "수컷", "갈색", "c"
            )
        )
        petList.add(
            HomePetseekerListData(
                "https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg", "믹스견", "수컷", "흰색, 갈색", "d",
            )
        )
        petList.add(
            HomePetseekerListData(
                "https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg", "믹스견", "암컷", "흰색", "e",
            )
        )
        petList.add(
            HomePetseekerListData(
                "https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg", "믹스견", "수컷", "갈색", "f"
            )
        )
        return petList
    }

    private fun getRecommendList(): ArrayList<HomePetseekerRecommendData> {
        val recommendList = ArrayList<HomePetseekerRecommendData>()

        //recommendList.add(HomePetseekerRecommendData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg", "한국 고양이", "수컷", "흰색, 갈색", "사람을 좋아하고 얌전함"))
        //recommendList.add(HomePetseekerRecommendData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg", "한국 고양이", "수컷", "흰색, 갈색", "사람을 좋아하고 얌전함"))

        return recommendList
    }
}