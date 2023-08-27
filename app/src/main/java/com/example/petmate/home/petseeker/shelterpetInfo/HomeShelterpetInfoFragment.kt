package com.example.petmate.home.petseeker.shelterpetInfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.R
import com.example.petmate.databinding.FragmentHomePetseekerBinding
import com.example.petmate.databinding.FragmentHomeShelterpetInfoBinding

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

        val itemList = ArrayList<HomeShelterpetInfoData>()

        itemList.add(HomeShelterpetInfoData("발생장소", "임은동 806-4"))
        itemList.add(HomeShelterpetInfoData("접수일시", "2023-07-07"))
        itemList.add(HomeShelterpetInfoData("관할기관", "경상북도 구미시"))
        itemList.add(HomeShelterpetInfoData("상태", "보호중"))
        itemList.add(HomeShelterpetInfoData("보호장소", "경상북도 구미시 인동22길 43-4 (진평동)"))
        itemList.add(HomeShelterpetInfoData("보호센터연락처", "054-716-0211"))

        val boardAdapter = HomeShelterpetInfoAdapter(itemList)
        boardAdapter.notifyDataSetChanged()

        binding.rcvShelterpetInf.adapter = boardAdapter
        binding.rcvShelterpetInf.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        return binding.getRoot()
    }

}