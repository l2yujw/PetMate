package com.example.petmate.pet.health

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.example.petmate.R
import com.example.petmate.databinding.FragmentPetHealthBinding

class PetHealthFragment : Fragment() {

    lateinit var binding: FragmentPetHealthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this){
            findNavController().navigate(R.id.action_petHealthFragment_to_petMainFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPetHealthBinding.inflate(inflater)

        binding.tvHealthVaccination.text = "2024.05.30"
        binding.tvHealthHelminthic.text = "2024.05.30"
        binding.tvHealthWeight.text = "정상"
        binding.tvHealthQuantity.text = "500"
        binding.tvHealthKcal.text = "250"

        return binding.getRoot()
    }
}