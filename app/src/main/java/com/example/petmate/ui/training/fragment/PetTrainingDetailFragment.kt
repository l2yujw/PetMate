package com.example.petmate.ui.training.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petmate.R
import com.example.petmate.core.decorator.VerticalDividerItemDecorator
import com.example.petmate.databinding.FragmentPetTrainingDetailBinding
import com.example.petmate.remote.response.training.TrainingResult
import com.example.petmate.ui.training.adapter.PetTrainingDetailAdapter
import com.example.petmate.ui.training.data.PetTrainingDetailData
import com.example.petmate.util.GlideHelper

class PetTrainingDetailFragment : Fragment() {

    private lateinit var binding: FragmentPetTrainingDetailBinding
    private val TAG = "PetTrainingDetail"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPetTrainingDetailBinding.inflate(inflater)

        val trainingInfo = arguments?.getParcelable<TrainingResult>("trainingInfo")
        trainingInfo?.let {
            setupUI(it)
        } ?: Log.e(TAG, "Training info is null.")

        return binding.root
    }

    private fun setupUI(trainingInfo: TrainingResult) {
        setupRecyclerView(parseTrainingDetails(trainingInfo.detail))
        loadThumbnailImage(trainingInfo.url)
        setupImageClickListener(trainingInfo.url)
    }

    private fun parseTrainingDetails(details: String): List<PetTrainingDetailData> {
        return details.split("@").mapNotNull { item ->
            val parts = item.split("#")
            if (parts.size == 2) {
                PetTrainingDetailData(parts[0].trim(), parts[1].trim())
            } else {
                Log.w(TAG, "Invalid detail format: $item")
                null
            }
        }
    }

    private fun loadThumbnailImage(videoUrl: String) {
        val thumbnailUrl = getYouTubeThumbnailUrl(videoUrl)
        GlideHelper.loadImage(binding.ivTraining, thumbnailUrl)
    }

    private fun getYouTubeThumbnailUrl(videoUrl: String): String {
        return try {
            val videoId = videoUrl.split("/watch?v=").getOrNull(1) ?: ""
            "http://img.youtube.com/vi/$videoId/0.jpg"
        } catch (e: Exception) {
            Log.e(TAG, "Error parsing YouTube URL: ${e.message}")
            ""
        }
    }

    private fun setupRecyclerView(detailList: List<PetTrainingDetailData>) {
        val adapter = PetTrainingDetailAdapter(ArrayList(detailList))
        binding.rvTrainingDetailWays.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(VerticalDividerItemDecorator(5))
        }
    }

    private fun setupImageClickListener(videoUrl: String) {
        binding.ivTraining.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
            startActivity(intent)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateToPreviousScreen()
            }
        })
    }

    private fun navigateToPreviousScreen() {
        findNavController().navigate(R.id.action_petTrainingDetailFragment_to_petTrainingFragment)
    }
}
