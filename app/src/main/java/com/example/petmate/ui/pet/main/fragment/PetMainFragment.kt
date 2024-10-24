package com.example.petmate.ui.pet.main.fragment

import android.content.Intent
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
import com.bumptech.glide.Glide
import com.example.petmate.core.util.OnItemClickListener
import com.example.petmate.core.util.GlobalPetIdxList
import com.example.petmate.R
import com.example.petmate.core.network.Tool
import com.example.petmate.core.decorator.VerticalItemDecorator
import com.example.petmate.databinding.FragmentPetMainBinding
import com.example.petmate.ui.pet.main.activity.PetMainAddPetActivity
import com.example.petmate.remote.api.pet.main.PetMainInterface
import com.example.petmate.remote.response.pet.main.PetMainInterfaceResponse
import com.example.petmate.remote.response.pet.main.PetMainInterfaceStarResponse
import com.example.petmate.ui.pet.main.adapter.PetMainHealthAdapter
import com.example.petmate.ui.pet.main.adapter.PetMainMypetAdapter
import com.example.petmate.ui.pet.main.adapter.PetMainNoteAdapter
import com.example.petmate.ui.pet.main.adapter.PetMainTrainingAdapter
import com.example.petmate.ui.pet.main.data.PetMainHealthData
import com.example.petmate.ui.pet.main.data.PetMainMypetData
import com.example.petmate.ui.pet.main.data.PetMainNoteData
import com.example.petmate.ui.pet.main.data.PetMainTrainingData
import retrofit2.Call
import retrofit2.Callback
import kotlin.random.Random

class PetMainFragment : Fragment() {

    lateinit var binding: FragmentPetMainBinding

    private val TAG = "PetMainFragment123"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPetMainBinding.inflate(inflater)
        binding.tvMainPetName.text = "탈주닌자"
        binding.tvMainPetAge.text = "특징" + "나이"
        val petIdxList = GlobalPetIdxList.getlist()
        Log.d(TAG, "onCreateView: "+petIdxList)

        val adapterNoteList = PetMainNoteAdapter(getNoteList())
        adapterNoteList.notifyDataSetChanged()


        val adapterMyPetList = PetMainMypetAdapter(getMypetList(GlobalPetIdxList.getlist().size))
        adapterMyPetList.notifyDataSetChanged()

        val indicatorMypet = binding.circleindicatorPetmainMypet
        indicatorMypet.setViewPager(binding.viewpagerPetMainMyPet)
        indicatorMypet.createIndicators(GlobalPetIdxList.getlist().size, 0)


        binding.rcvPetMainNote.adapter = adapterNoteList
        binding.rcvPetMainNote.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcvPetMainNote.addItemDecoration(VerticalItemDecorator(5))

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
                Log.d(TAG, "position : $position")
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

        binding.btnAddPet.setOnClickListener{
            val intent = Intent(requireContext(), PetMainAddPetActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    private fun requestPetTrainingInfo(petIdx: Int) {

        val retrofit = Tool.getRetrofit()

        val service = retrofit.create(PetMainInterface::class.java)
        service.getStar(petIdx).enqueue(object : Callback<PetMainInterfaceStarResponse> {

            override fun onResponse(call: Call<PetMainInterfaceStarResponse>, response: retrofit2.Response<PetMainInterfaceStarResponse>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    val result: PetMainInterfaceStarResponse? = response.body()
                    Log.d(TAG, "onResponse 성공: " + result?.toString())

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
                Log.d(TAG, "onFailure 에러: " + t.message.toString())
            }
        })
    }

    private fun requestPetInfo(petIdx:Int) {
        //고정
        val retrofit = Tool.getRetrofit()

        val service = retrofit.create(PetMainInterface::class.java)
        service.getInfo(petIdx).enqueue(object : Callback<PetMainInterfaceResponse> {

            override fun onResponse(call: Call<PetMainInterfaceResponse>, response: retrofit2.Response<PetMainInterfaceResponse>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    val result: PetMainInterfaceResponse? = response.body()
                    Log.d(TAG, "onResponse 성공: " + result?.toString())

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


                                    if(item.image.isBlank() || item.image == ""){
                                        val tempimagelist = ArrayList<String>()
                                        tempimagelist.add("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg")
                                        tempimagelist.add("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg")
                                        tempimagelist.add("https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg")
                                        Glide.with(binding.tvMainPetImage)
                                            .load(tempimagelist.get(Random.nextInt(0,3)))                         // 불러올 이미지 URL
                                            .fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                                            .error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                                            .placeholder(R.drawable.background_glide_init)  // 이미지 로딩 시작하기 전에 표시할 이미지
                                            .centerInside()                                 // scaletype
                                            .into(binding.tvMainPetImage)             // 이미지를 넣을 뷰
                                    }else if(item.image.endsWith(".png") ||item.image.endsWith(".jpg")||item.image.endsWith(".jpeg") ) {
                                        Log.d(TAG, "onResponse: ${item.image}")
                                        Glide.with(binding.tvMainPetImage)
                                            .load(item.image)                         // 불러올 이미지 URL
                                            .fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                                            .error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                                            .placeholder(R.drawable.background_glide_init)  // 이미지 로딩 시작하기 전에 표시할 이미지
                                            .centerInside()// scaletype
                                            .into(binding.tvMainPetImage)             // 이미지를 넣을 뷰
                                    }else{
                                        val encodeByte = Base64.decode(item.image, Base64.NO_WRAP)
                                        val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
                                        binding.tvMainPetImage.setImageBitmap(bitmap)
                                    }

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
                Log.d(TAG, "onFailure 에러: " + t.message.toString())
            }
        })
    }

    private fun getNoteList(): ArrayList<PetMainNoteData> {
        val noteList = ArrayList<PetMainNoteData>()

        noteList.add(PetMainNoteData("간식사러 가야함"))
        noteList.add(PetMainNoteData("산책 부탁해야 함"))

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

    private fun getMypetList(cnt:Int): ArrayList<PetMainMypetData> {
        val recommendList = java.util.ArrayList<PetMainMypetData>()
        for (i in 0..cnt) {
            recommendList.add(PetMainMypetData("https://cdn.pixabay.com/photo/2022/08/06/13/58/jindo-dog-7368686_640.jpg"))
        }

        return recommendList
    }


}