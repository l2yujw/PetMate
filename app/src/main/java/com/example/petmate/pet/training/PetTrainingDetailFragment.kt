package com.example.petmate.pet.training

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.petmate.R
import com.example.petmate.VerticalItemDecorator
import com.example.petmate.databinding.FragmentPetTrainingDetailBinding

class PetTrainingDetailFragment : Fragment() {

    lateinit var binding: FragmentPetTrainingDetailBinding
    private val TAG = "PetTrainingDetailFragment123"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPetTrainingDetailBinding.inflate(inflater)
        val bundle = arguments
        val obj = bundle?.getParcelable<PetTrainingInterfaceResponseResult>("trainingInfo")
        val list = ArrayList<PetTrainingDetailData>()
        Log.d(TAG, "PetTrainingDetailFragment onCreateView: $obj")
        if (obj != null) {
            for(item in obj.detail.split("@")){
                //Log.d(TAG, "item: ${item}")
                if (item !=" ") {
                    val temp = item.split("#")
                    list.add(PetTrainingDetailData(temp[0], temp[1]))
                }
            }
        }

        var imageUrl = obj!!.url
        Log.d(TAG, "imageUrl: $imageUrl")
        imageUrl = imageUrl.split("/watch?v=")[1]
        val link = "http://img.youtube.com/vi/${imageUrl}/0.jpg"

        Glide.with(this)
            .load(link)
            .into(binding.petTariningImage)

        binding.petTariningImage.setOnClickListener {
            val intent= Intent(Intent.ACTION_VIEW,Uri.parse(obj.url))
            startActivity(intent)
        }


        val boardAdapter = PetTrainingDetailAdapter(list)
        boardAdapter.notifyDataSetChanged()

        binding.rcvTrainingDetailWays.adapter = boardAdapter
        binding.rcvTrainingDetailWays.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcvTrainingDetailWays.addItemDecoration(VerticalItemDecorator(5))
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_petTrainingDetailFragment_to_petTrainingFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }
}