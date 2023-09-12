package com.example.petmate.pet.health

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petmate.R
import com.example.petmate.VerticalItemDecorator
import com.example.petmate.databinding.FragmentPetHealthBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.ArrayList

class PetHealthFragment : Fragment() {

    lateinit var binding: FragmentPetHealthBinding
    var petIdx = 0
    private val TAG = "PetHealthFragment123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_petHealthFragment_to_petMainFragment)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPetHealthBinding.inflate(inflater)

        petIdx = arguments?.getInt("petIdx") ?: 0
        Log.d(TAG, "petIdx : ${petIdx}")

        getInfoList()
        requestInfo(petIdx)

        return binding.getRoot()
    }

    private fun requestInfo(petIdx: Int) {
//고정
        val retrofit = Retrofit.Builder()
            .baseUrl("http://13.124.16.204:3000/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        //

        val service = retrofit.create(PetHealthInterface::class.java);
        service.getInfo(petIdx).enqueue(object : Callback<PetHealthInterfaceResponse> {

            override fun onResponse(call: Call<PetHealthInterfaceResponse>, response: retrofit2.Response<PetHealthInterfaceResponse>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    val result: PetHealthInterfaceResponse? = response.body()
                    Log.d(TAG, "onResponse 성공: " + result?.toString());

                    when (result?.code) {
                        200 -> {
                            val item = result.result[0]

                            binding.petHealthPetname.text = item.name
                            binding.petHealthPetdescription.text = "${item.category} ${item.species}"
                            binding.petHealthPetage.text = "${item.age}살"
                            binding.petHealthSexImage.setImageResource(getSexImage(item.gender))
                            binding.tvHealthVaccination.text = item.vaccination
                            binding.tvHealthHelminthic.text = item.helminthic
                            binding.tvHealthWeight.text = item.weight.toString()
                            binding.tvHealthWeightCondition.text = "정상"
                            binding.tvHealthQuantity.text = "500"
                            binding.tvHealthKcal.text = "250"
                            binding.petHealthKnow.text = "알고 계셨나요?"


                            val encodeByte = Base64.decode(item.image, Base64.NO_WRAP)
                            val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
                            binding.petHealthImage.setImageBitmap(bitmap)




                            binding.rcvHealthRecord.adapter = PetHealthAdapter(getRecordList())
                            binding.rcvHealthRecord.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


                            //item 간격 결정
                            binding.rcvHealthRecord.addItemDecoration(VerticalItemDecorator(10))

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

            override fun onFailure(call: Call<PetHealthInterfaceResponse>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d(TAG, "onFailure 에러: " + t.message.toString());
            }
        })
    }

    private fun getRecordList(): ArrayList<PetHealthData> {
        val recordList = ArrayList<PetHealthData>()

        recordList.add(PetHealthData("2021.05.30"))
        recordList.add(PetHealthData("2022.05.30"))
        recordList.add(PetHealthData("2023.05.30"))

        return recordList
    }


    // 성별
    private fun getSexImage(sex: String): Int {
        return when (sex) {
            "수컷", "M" -> R.drawable.sex_male
            "암컷", "F" -> R.drawable.sex_female
            else -> R.drawable.sex_male
        }
    }

    private fun getInfoList() {
        binding.petHealthPetname.text = "탈주닌자"
        binding.petHealthPetdescription.text = "놀라버린 고양이"
        binding.petHealthPetage.text = "1살"
        binding.petHealthSexImage.setImageResource(getSexImage("수컷"))
        binding.tvHealthVaccination.text = "2024.05.30"
        binding.tvHealthHelminthic.text = "2024.05.30"
        binding.tvHealthWeight.text = "5"
        binding.tvHealthWeightCondition.text = "정상"
        binding.tvHealthQuantity.text = "500"
        binding.tvHealthKcal.text = "250"
        binding.petHealthKnow.text = "알고 계셨나요?"


        binding.rcvHealthRecord.adapter = PetHealthAdapter(getRecordList())
        binding.rcvHealthRecord.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        //item 간격 결정
        binding.rcvHealthRecord.addItemDecoration(VerticalItemDecorator(10))
    }
}