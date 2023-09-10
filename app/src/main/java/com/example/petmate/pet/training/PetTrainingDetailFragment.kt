package com.example.petmate.pet.training

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
import com.example.petmate.VerticalItemDecorator
import com.example.petmate.databinding.FragmentPetTrainingDetailBinding


class PetTrainingDetailFragment : Fragment() {

    lateinit var binding: FragmentPetTrainingDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this){
            findNavController().navigate(R.id.action_petTrainingDetailFragment_to_petTrainingFragment)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPetTrainingDetailBinding.inflate(inflater)

        val boardAdapter = PetTrainingDetailAdapter(getTrainingDetailList())
        boardAdapter.notifyDataSetChanged()

        binding.rcvTrainingDetailWays.adapter = boardAdapter
        binding.rcvTrainingDetailWays.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcvTrainingDetailWays.addItemDecoration(VerticalItemDecorator(5))
        return binding.getRoot()
    }

    private fun getTrainingDetailList(): ArrayList<PetTrainingDetailData>{
        val trainingDetailList = ArrayList<PetTrainingDetailData>()

        trainingDetailList.add(PetTrainingDetailData("1단계","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"))
        trainingDetailList.add(PetTrainingDetailData("2단계","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"))
        trainingDetailList.add(PetTrainingDetailData("3단계","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"))

        return trainingDetailList
    }
}