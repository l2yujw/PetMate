package com.example.petmate.home.petseeker.shelterpetInfo

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
import com.example.petmate.databinding.FragmentHomeShelterpetInfoBinding
import com.example.petmate.home.petseeker.HomePetseekerRecommendPetListInterfaceResponseResponseResult
import com.example.petmate.pet.training.PetTrainingInterfaeResponseResult
import java.net.URL
import kotlin.concurrent.thread

class HomeShelterpetInfoFragment : Fragment() {

    lateinit var binding: FragmentHomeShelterpetInfoBinding
    private val TAG = "HomeShelterpetInfoFragment123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this){
            findNavController().navigate(R.id.action_homeShelterpetInfoFragment_to_homePetseekerFragment)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeShelterpetInfoBinding.inflate(inflater)
        Log.d(TAG, "onCreateView: ")

        val bundle = arguments
        val obj = bundle?.getParcelable<HomePetseekerRecommendPetListInterfaceResponseResponseResult>("ShelterpetInfo")
        //HomePetseekerRecommendPetListInterfaceResponseResponseResult  Ctrl+B
        val shelterpetInfoList = ArrayList<HomeShelterpetInfoData>()
        if (obj != null) {
            Log.d(TAG, "123")
            thread{
                val url = URL(obj.imageUrl)
                val bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                binding.tvShelterpetPetImage.setImageBitmap(bitmap)
            }.join()
            binding.tvShelterpetPetName.text = obj.species
            binding.tvShelterpetPetSex.setImageResource(getSexImage(obj.gender))
            binding.tvShelterpetPetDescription.text = obj.characteristic
            binding.tvShelterpetPetAge.text = "${2023 - obj.age}살"
            binding.tvShelterpetPetAbout.text = "탈주닌자" + "에 대하여..."
            binding.tvShelterpetPetWeight.text = "${obj.weight.split("(")[0]}Kg"
            binding.tvShelterpetNeutered.text = "아니오"
            binding.tvShelterpetPetColor.text = obj.colorCd
            binding.tvShelterpetPetCharac.text = "사람을 좋아하고 얌전함"
            binding.tvShelterpetPetMoreInf.text = "탈주닌자" + "추가 정보"

            shelterpetInfoList.add(HomeShelterpetInfoData("발생장소", obj.discoveryPlace))
            shelterpetInfoList.add(HomeShelterpetInfoData("접수일시", obj.receiptDate))
            shelterpetInfoList.add(HomeShelterpetInfoData("관할기관", obj.orgNm))
            shelterpetInfoList.add(HomeShelterpetInfoData("상태", "보호중"))
            shelterpetInfoList.add(HomeShelterpetInfoData("보호장소", obj.careAddr))
            shelterpetInfoList.add(HomeShelterpetInfoData("보호센터연락처", obj.officetel))

        }

        val boardAdapter = HomeShelterpetInfoAdapter(shelterpetInfoList)
        boardAdapter.notifyDataSetChanged()

        binding.rcvShelterpetInf.adapter = boardAdapter
        binding.rcvShelterpetInf.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcvShelterpetInf.addItemDecoration(VerticalItemDecorator(5))

        return binding.getRoot()
    }

    private fun getShelterpetInfoList(): ArrayList<HomeShelterpetInfoData> {
        val shelterpetInfoList = ArrayList<HomeShelterpetInfoData>()

        shelterpetInfoList.add(HomeShelterpetInfoData("발생장소", "임은동 806-4"))
        shelterpetInfoList.add(HomeShelterpetInfoData("접수일시", "2023-07-07"))
        shelterpetInfoList.add(HomeShelterpetInfoData("관할기관", "경상북도 구미시"))
        shelterpetInfoList.add(HomeShelterpetInfoData("상태", "보호중"))
        shelterpetInfoList.add(HomeShelterpetInfoData("보호장소", "경상북도 구미시 인동22길 43-4 (진평동)"))
        shelterpetInfoList.add(HomeShelterpetInfoData("보호센터연락처", "054-716-0211"))

        return shelterpetInfoList
    }
    private fun getSexImage(sex: String): Int {
        return when (sex) {
            "수컷","M" -> R.drawable.sex_male
            "암컷","F" -> R.drawable.sex_female
            else -> R.drawable.sex_male
        }
    }

}