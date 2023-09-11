package com.example.petmate.pet.main

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.petmate.OnItemClickListener
import com.example.petmate.PetIdxList
import com.example.petmate.R
import com.example.petmate.databinding.FragmentPetMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class PetMainFragment : Fragment() {

    lateinit var binding: FragmentPetMainBinding
    private val TAG = "PetMainFragment123"
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
        val petIdxList = PetIdxList.getlist()


        val adapterNoteList = PetMainNoteAdapter(getNoteList())
        adapterNoteList.notifyDataSetChanged()


        val adapterMyPetList = PetMainMypetAdapter(getMypetList())
        adapterMyPetList.notifyDataSetChanged()

        val indicatorMypet = binding.circleindicatorPetmainMypet
        indicatorMypet.setViewPager(binding.viewpagerPetMainMyPet)
        indicatorMypet.createIndicators(PetIdxList.getlist().size, 0)


        binding.rcvPetMainNote.adapter = adapterNoteList
        binding.rcvPetMainNote.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        val adapterHealthList = PetMainHealthAdapter(getHealthList())
        adapterHealthList.notifyDataSetChanged()
        binding.rcvPetMainHealth.adapter = adapterHealthList
        binding.rcvPetMainHealth.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        //훈련쪽 리스트 받아와서 하면될듯?
        val adapterCheckedTrainingList = PetMainTrainingAdapter(getCheckedTrainingList())
        adapterCheckedTrainingList.notifyDataSetChanged()
        binding.rcvPetMainTraining.adapter = adapterCheckedTrainingList
        binding.rcvPetMainTraining.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        binding.viewpagerPetMainMyPet.adapter = adapterMyPetList
        binding.viewpagerPetMainMyPet.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewpagerPetMainMyPet.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                indicatorMypet.animatePageSelected(position)
                Log.d(TAG, "position : ${position}")
                requestPetInfo(petIdxList[position])
                requestPetTrainingInfo(petIdxList[position])
            }
        })


        adapterHealthList.setItemClickListener(object : OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                findNavController().navigate(R.id.action_petMainFragment_to_petHealthFragment)

            }
        })

        adapterCheckedTrainingList.setItemClickListener(object : OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                findNavController().navigate(R.id.action_petMainFragment_to_petTrainingFragment)
            }
        })
        return binding.getRoot()
    }

    private fun requestPetTrainingInfo(petIdx: Int) {
//고정
        val retrofit = Retrofit.Builder()
            .baseUrl("http://13.124.16.204:3000/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        //

        val service = retrofit.create(PetMainInterface::class.java);
        service.getStar(petIdx).enqueue(object : Callback<PetMainInterfaceStarResponse> {

            override fun onResponse(call: Call<PetMainInterfaceStarResponse>, response: retrofit2.Response<PetMainInterfaceStarResponse>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    val result: PetMainInterfaceStarResponse? = response.body()
                    Log.d(TAG, "onResponse 성공: " + result?.toString());

                    when (result?.code) {
                        200 -> {
                            val list = ArrayList<PetMainTrainingData>()
                            for(item in result.result){
                                list.add(PetMainTrainingData(item.name))
                            }

                            //훈련
                            val adapterCheckedTrainingList = PetMainTrainingAdapter(list)
                            adapterCheckedTrainingList.notifyDataSetChanged()
                            binding.rcvPetMainTraining.adapter = adapterCheckedTrainingList
                            binding.rcvPetMainTraining.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

                            adapterCheckedTrainingList.setItemClickListener(object : OnItemClickListener {
                                override fun onClick(v: View, position: Int) {
                                    val bundle = Bundle()
                                    bundle.putInt("petIdx", petIdx)
                                    findNavController().navigate(R.id.action_petMainFragment_to_petTrainingFragment, bundle)
                                }
                            })
                            //훈련
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

            override fun onFailure(call: Call<PetMainInterfaceStarResponse>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d(TAG, "onFailure 에러: " + t.message.toString());
            }
        })
    }

    private fun requestPetInfo(petIdx:Int) {
        //고정
        val retrofit = Retrofit.Builder()
            .baseUrl("http://13.124.16.204:3000/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        //

        val service = retrofit.create(PetMainInterface::class.java);
        service.getInfo(petIdx).enqueue(object : Callback<PetMainInterfaceResponse> {

            override fun onResponse(call: Call<PetMainInterfaceResponse>, response: retrofit2.Response<PetMainInterfaceResponse>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    val result: PetMainInterfaceResponse? = response.body()
                    Log.d(TAG, "onResponse 성공: " + result?.toString());

                    when (result?.code) {
                        200 -> {
                            val petMainHealthData = ArrayList<PetMainHealthData>()
                            for (item in result.result) {

                                if (item.petIdx == petIdx) {
                                    binding.tvMainPetName.text = item.name
                                    binding.tvMainPetAge.text = "${item.age}살"
                                    binding.tvMainPetDescription.text = "${item.category} ${item.species}"
                                    if (item.gender == "M") {
                                        binding.walkRecordPetSex.setImageResource(R.drawable.sex_male)
                                    }else{
                                        binding.walkRecordPetSex.setImageResource(R.drawable.sex_female)
                                    }

                                    val encodeByte = Base64.decode(item.image, Base64.NO_WRAP)
                                    val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
                                    binding.tvMainPetImage.setImageBitmap(bitmap)

                                    petMainHealthData.add(PetMainHealthData("접종 예정일\n${item.vaccination}\n"))
                                    petMainHealthData.add(PetMainHealthData("구충제 예정일\n${item.helminthic}\n"))
                                    petMainHealthData.add(PetMainHealthData("체중\n${item.weight}(kg)\n"))
                                }
                            }

                            //건강정보
                            val adapterHealthList = PetMainHealthAdapter(petMainHealthData)
                            adapterHealthList.notifyDataSetChanged()
                            binding.rcvPetMainHealth.adapter = adapterHealthList
                            binding.rcvPetMainHealth.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

                            adapterHealthList.setItemClickListener(object : OnItemClickListener {
                                override fun onClick(v: View, position: Int) {
                                    val bundle = Bundle()
                                    bundle.putInt("petIdx", petIdx)
                                    findNavController().navigate(R.id.action_petMainFragment_to_petHealthFragment, bundle)
                                }
                            })
                            //건강정보

                            //훈련
                            /*val adapterCheckedTrainingList = PetMainTrainingAdapter(getCheckedTrainingList())
                            adapterCheckedTrainingList.notifyDataSetChanged()
                            binding.rcvPetMainTraining.adapter = adapterCheckedTrainingList
                            binding.rcvPetMainTraining.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

                            adapterCheckedTrainingList.setItemClickListener(object : OnItemClickListener {
                                override fun onClick(v: View, position: Int) {
                                    val bundle = Bundle()
                                    bundle.putInt("petIdx", petIdx)
                                    findNavController().navigate(R.id.action_petMainFragment_to_petTrainingFragment, bundle)
                                }
                            })*/
                            //훈련
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
        val am = resources.assets
        recommendList.add(PetMainMypetData(BitmapFactory.decodeStream(am.open("pet1.jpg"))))
        recommendList.add(PetMainMypetData(BitmapFactory.decodeStream(am.open("pet1.jpg"))))


        return recommendList
    }

}