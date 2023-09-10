package com.example.petmate.pet.main

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.petmate.OnItemClickListener
import com.example.petmate.R
import com.example.petmate.databinding.FragmentPetMainBinding
import com.example.petmate.home.petowner.HomePetownerInterface
import com.example.petmate.home.petowner.HomePetownerInterfaceResponse
import com.example.petmate.home.petowner.HomePetownerPetlistAdapter
import com.example.petmate.home.petowner.HomePetownerPetlistData
import com.example.petmate.petIdxList
import com.example.petmate.userIdx
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class PetMainFragment : Fragment() {

    lateinit var binding: FragmentPetMainBinding
    private val TAG = "PetMainFragment123"
    private val petIdx=3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPetMainBinding.inflate(inflater)
        binding.tvMainPetName.text = "탈주닌자"
        binding.tvMainPetAge.text = "특징" + "나이"
        val userIdx1=userIdx



        val adapterNoteList = PetMainNoteAdapter(getNoteList())
        adapterNoteList.notifyDataSetChanged()

        val adapterHealthList = PetMainHealthAdapter(getHealthList())
        adapterHealthList.notifyDataSetChanged()
        val adapterCheckedTrainingList = PetMainTrainingAdapter(getCheckedTrainingList())
        adapterCheckedTrainingList.notifyDataSetChanged()
        val adapterMyPetList = PetMainMypetAdapter(getMypetList())
        adapterMyPetList.notifyDataSetChanged()

        val indicatorMypet = binding.circleindicatorPetmainMypet
        indicatorMypet.setViewPager(binding.viewpagerPetMainMyPet)
        indicatorMypet.createIndicators(getMypetList().size, 0)


        binding.rcvPetMainNote.adapter = adapterNoteList
        binding.rcvPetMainNote.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)




        val adapterHealthList = PetMainHealthAdapter(getHealthList())
        adapterHealthList.notifyDataSetChanged()
        binding.rcvPetMainHealth.adapter = adapterHealthList
        binding.rcvPetMainHealth.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        binding.viewpagerPetMainMyPet.adapter = adapterMyPetList
        binding.viewpagerPetMainMyPet.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewpagerPetMainMyPet.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                indicatorMypet.animatePageSelected(position)
                //Toast.makeText(requireContext(), "${position + 1} 페이지 선택됨", Toast.LENGTH_SHORT).show()
            }
        })


        adapterHealthList.setItemClickListener(object : OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                findNavController().navigate(R.id.action_petMainFragment_to_petHealthFragment)

            }
        })

        requestPetInfo(userIdx1.getUserIdx())





        //훈련쪽 리스트 받아와서 하면될듯?
        val adapterCheckedTrainingList = PetMainTrainingAdapter(getCheckedTrainingList())
        adapterCheckedTrainingList.notifyDataSetChanged()
        binding.rcvPetMainTraining.adapter = adapterCheckedTrainingList
        binding.rcvPetMainTraining.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        adapterCheckedTrainingList.setItemClickListener(object : OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                findNavController().navigate(R.id.action_petMainFragment_to_petTrainingFragment)
            }
        })

        return binding.getRoot()
    }

    private fun requestPetInfo(userIdx:Int) {
        //고정
        val retrofit = Retrofit.Builder()
            .baseUrl("http://13.124.16.204:3000/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        //

        val service = retrofit.create(PetMainInterface::class.java);
        service.getInfo(userIdx).enqueue(object : Callback<PetMainInterfaceResponse> {

            override fun onResponse(call: Call<PetMainInterfaceResponse>, response: retrofit2.Response<PetMainInterfaceResponse>) {
                if(response.isSuccessful){
                    // 정상적으로 통신이 성고된 경우
                    val result: PetMainInterfaceResponse? = response.body()
                    Log.d(TAG, "onResponse 성공: " + result?.toString());

                    when (result?.code) {
                        200 -> {
                            val petMainHealthData = ArrayList<PetMainHealthData>()
                            for(item in result.result) {
                                if(item.petIdx == petIdx) {
                                    binding.tvMainPetName.text = item.name
                                    binding.tvMainPetAge.text = "${item.age}살"
                                    binding.tvMainPetDescription.text = "${item.category} ${item.species}"
                                    if (item.gender == "M") {
                                        binding.walkRecordPetSex.setImageResource(R.drawable.sex_male)
                                    }

                                    var encodeByte = Base64.decode(item.image, Base64.NO_WRAP)
                                    var bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
                                    //예를 viewpager에 넣어야함 어떻게? 그건 몰루
                                    petMainHealthData.add(PetMainHealthData("접종예정일\n${item.vaccination}\n"))
                                    petMainHealthData.add(PetMainHealthData("구충제예정일\n${item.helminthic}\n"))
                                    petMainHealthData.add(PetMainHealthData("체중\n${item.weight}\n"))
                                }
                            }
                            val adapterHealthList = PetMainHealthAdapter(petMainHealthData)
                            adapterHealthList.notifyDataSetChanged()
                            binding.rcvPetMainHealth.adapter = adapterHealthList
                            binding.rcvPetMainHealth.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

                            adapterHealthList.setItemClickListener(object : OnItemClickListener {
                                override fun onClick(v: View, position: Int) {
                                    val bundle = Bundle()
                                    bundle.putInt("petIdx",petIdx)
                                    findNavController().navigate(R.id.action_petMainFragment_to_petHealthFragment,bundle)

                                }
                            })

                            val adapterCheckedTrainingList = PetMainTrainingAdapter(getCheckedTrainingList())
                            adapterCheckedTrainingList.notifyDataSetChanged()
                            binding.rcvPetMainTraining.adapter = adapterCheckedTrainingList
                            binding.rcvPetMainTraining.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

                            adapterCheckedTrainingList.setItemClickListener(object : OnItemClickListener {
                                override fun onClick(v: View, position: Int) {
                                    val bundle = Bundle()
                                    bundle.putInt("petIdx",petIdx)
                                    findNavController().navigate(R.id.action_petMainFragment_to_petTrainingFragment,bundle)
                                }
                            })
                        }
                        else -> {
                            Log.d(TAG, "onResponse: ㅈ버그발생 보내는 데이터가 문제임 ")
                        }
                    }

                }else{
                    // 통신이 실패한 경우(응답 코드 3xx, 4xx 등)
                    Log.d(TAG, "onResponse 실패"+response.code())
                }
            }

            override fun onFailure(call: Call<PetMainInterfaceResponse>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d(TAG, "onFailure 에러: " + t.message.toString());
            }
        })
    }

    private fun getNoteList(): ArrayList<PetMainNoteData> {
        val noteList = ArrayList<PetMainNoteData>()

        noteList.add(PetMainNoteData("메모테스트"))
        noteList.add(PetMainNoteData("메모테스트"))
        noteList.add(PetMainNoteData("메모테스트"))

        return noteList
    }

    private fun getHealthList(): ArrayList<PetMainHealthData> {
        val healthList = ArrayList<PetMainHealthData>()

        healthList.add(PetMainHealthData("건강정보"))
        healthList.add(PetMainHealthData("건강정보"))
        healthList.add(PetMainHealthData("건강정보"))

        return healthList
    }

    private fun getCheckedTrainingList(): ArrayList<PetMainTrainingData> {
        val checkedTrainingList = ArrayList<PetMainTrainingData>()

        checkedTrainingList.add(PetMainTrainingData("체크된 훈련"))
        checkedTrainingList.add(PetMainTrainingData("체크된 훈련"))
        checkedTrainingList.add(PetMainTrainingData("체크된 훈련"))

        return checkedTrainingList
    }

    private fun getMypetList(): ArrayList<PetMainMypetData> {
        val recommendList = java.util.ArrayList<PetMainMypetData>()

        recommendList.add(PetMainMypetData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        recommendList.add(PetMainMypetData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))

        return recommendList
    }

}