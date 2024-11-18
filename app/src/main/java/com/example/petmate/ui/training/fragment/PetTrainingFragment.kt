package com.example.petmate.ui.training.fragment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.petmate.R
import com.example.petmate.core.decorator.HorizontalDividerItemDecorator
import com.example.petmate.core.decorator.VerticalDividerItemDecorator
import com.example.petmate.core.network.NetworkClient
import com.example.petmate.core.util.OnItemClickListener
import com.example.petmate.core.util.PetIndexList
import com.example.petmate.databinding.FragmentPetTrainingBinding
import com.example.petmate.remote.api.training.PetTrainingService
import com.example.petmate.remote.response.training.PetTrainingResponse
import com.example.petmate.remote.response.training.TrainingResult
import com.example.petmate.ui.training.adapter.PetTrainingListAdapter
import com.example.petmate.ui.training.data.PetTrainingListData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL

class PetTrainingFragment : Fragment() {

    private lateinit var binding: FragmentPetTrainingBinding
    private lateinit var adapter: PetTrainingListAdapter
    private val trainingDataList = mutableListOf<TrainingResult>()
    private val petIdx by lazy { PetIndexList.get()[0] }
    private val scope = CoroutineScope(Dispatchers.Main)

    companion object {
        private const val TAG = "PetTrainingFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPetTrainingBinding.inflate(inflater)
        initializeRecyclerView()
        setupEventListeners()
        loadTrainingData()
        return binding.root
    }

    private fun initializeRecyclerView() {
        adapter = PetTrainingListAdapter(ArrayList())
        binding.rvPetTrainingList.apply {
            adapter = this@PetTrainingFragment.adapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            addItemDecoration(HorizontalDividerItemDecorator(20))
            addItemDecoration(VerticalDividerItemDecorator(20))
        }

        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                navigateToDetail(trainingDataList[position])
            }
        })
    }

    private fun setupEventListeners() {
        binding.btnReadAll.setOnClickListener { loadTrainingData() }
        binding.btnLevelOne.setOnClickListener { loadTrainingData(level = 1) }
        binding.btnLevelTwo.setOnClickListener { loadTrainingData(level = 2) }
        binding.btnLevelThree.setOnClickListener { loadTrainingData(level = 3) }
        binding.ivTrainingSearch.setOnClickListener {
            val query = binding.etTrainingSearch.text.toString()
            loadTrainingData(name = query)
        }
    }

    private fun loadTrainingData(level: Int? = null, name: String? = null) {
        val service = NetworkClient.getRetrofit().create(PetTrainingService::class.java)
        val call = when {
            level != null -> service.getByLevel(level, petIdx)
            name != null -> service.getByName(name, petIdx)
            else -> service.getAll(petIdx)
        }

        call.enqueue(object : Callback<PetTrainingResponse> {
            override fun onResponse(call: Call<PetTrainingResponse>, response: Response<PetTrainingResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result?.code == 200) {
                        processTrainingData(result.result)
                    } else {
                        Log.e(TAG, "Error: ${result?.code}")
                    }
                } else {
                    Log.e(TAG, "API call failed: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<PetTrainingResponse>, t: Throwable) {
                Log.e(TAG, "Network failure: ${t.message}")
            }
        })
    }

    private fun processTrainingData(results: List<TrainingResult>) {
        trainingDataList.clear()
        trainingDataList.addAll(results)

        val processedList = results.map { item ->
            PetTrainingListData(
                imageUrl = item.url,
                isStar = item.petIdx != 0
            )
        }

        // 이미지 비동기 로드 및 UI 갱신
        scope.launch {
            processedList.forEach { data ->
                data.bitmap = downloadBitmapFromUrl(data.imageUrl)
            }
            adapter.updateTrainingItems(processedList)
        }
    }

    private suspend fun downloadBitmapFromUrl(url: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                val videoId = url.split("/watch?v=").getOrNull(1)
                    ?.let { "http://img.youtube.com/vi/$it/0.jpg" }
                videoId?.let { BitmapFactory.decodeStream(URL(it).openConnection().getInputStream()) }
            } catch (e: Exception) {
                Log.e(TAG, "Image download failed: ${e.message}")
                null
            }
        }
    }

    private fun navigateToDetail(trainingResult: TrainingResult) {
        val bundle = Bundle().apply {
            putParcelable("trainingInfo", trainingResult)
        }
        findNavController().navigate(R.id.action_petTrainingFragment_to_petTrainingDetailFragment, bundle)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_petTrainingFragment_to_petMainFragment)
            }
        })
    }
}
