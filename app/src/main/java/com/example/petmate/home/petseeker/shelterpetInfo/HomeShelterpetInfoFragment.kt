package com.example.petmate.home.petseeker.shelterpetInfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petmate.R
import com.example.petmate.databinding.FragmentHomeShelterpetInfoBinding
import com.example.petmate.home.petseeker.HomePetseekerRecommendData

class HomeShelterpetInfoFragment : Fragment() {

    lateinit var binding: FragmentHomeShelterpetInfoBinding

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

        val boardAdapter = HomeShelterpetInfoAdapter(getShelterpetInfoList())
        boardAdapter.notifyDataSetChanged()

        binding.rcvShelterpetInf.adapter = boardAdapter
        binding.rcvShelterpetInf.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.tvShelterpetPetName.text = "탈주닌자"
        binding.tvShelterpetPetDescription.text="특징"
        binding.tvShelterpetPetAge.text = "1살"
        binding.tvShelterpetPetAbout.text = "탈주닌자"+"에 대하여..."
        binding.tvShelterpetPetWeight.text = "3.2"+"kg"
        binding.tvShelterpetNeutered.text = "아니오"
        binding.tvShelterpetPetColor.text = "흰갈"
        binding.tvShelterpetPetCharac.text = "사람을 좋아하고 얌전함"
        binding.tvShelterpetPetMoreInf.text = "탈주닌자"+"추가 정보"

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

}