package com.example.petmate.pet.training

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.petmate.R
import com.example.petmate.VerticalItemDecorator
import com.example.petmate.databinding.FragmentPetTrainingDetailBinding
import java.net.URL

class PetTrainingDetailFragment : Fragment() {

    lateinit var binding: FragmentPetTrainingDetailBinding
    private val TAG = "PetTrainingDetailFragment123"
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
        val bundle = arguments
        val obj = bundle?.getParcelable<PetTrainingInterfaceResponseResult>("trainingInfo")
        val list = ArrayList<PetTrainingDetailData>()
        Log.d(TAG, "PetTrainingDetailFragment onCreateView: ${obj}")
        if (obj != null) {
            for(item in obj.detail.split("@")){
                //Log.d(TAG, "item: ${item}")
                val temp = item.split("#")
                list.add(PetTrainingDetailData(temp[0],temp[1]))
            }
        }

        var imageUrl = obj!!.url
        Log.d(TAG, "imageUrl: ${imageUrl}")
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