
package com.example.petmate.ui.pet.main.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.petmate.core.decorator.VerticalDividerItemDecorator
import com.example.petmate.core.network.NetworkClient
import com.example.petmate.core.util.PetIndexList
import com.example.petmate.databinding.FragmentPetMainBinding
import com.example.petmate.remote.api.pet.main.PetMainService
import com.example.petmate.remote.response.pet.main.PetMainResponse
import com.example.petmate.remote.response.pet.main.PetMainStarResponse
import com.example.petmate.ui.pet.main.activity.PetMainAddPetActivity
import com.example.petmate.ui.pet.main.adapter.PetMainHealthAdapter
import com.example.petmate.ui.pet.main.adapter.PetMainMyPetAdapter
import com.example.petmate.ui.pet.main.adapter.PetMainNoteAdapter
import com.example.petmate.ui.pet.main.adapter.PetMainTrainingAdapter
import com.example.petmate.ui.pet.main.data.PetMainHealthData
import com.example.petmate.ui.pet.main.data.PetMainMypetData
import com.example.petmate.ui.pet.main.data.PetMainNoteData
import com.example.petmate.ui.pet.main.data.PetMainTrainingData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PetMainFragment : Fragment() {

    private lateinit var binding: FragmentPetMainBinding
    private val TAG = "PetMainFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPetMainBinding.inflate(inflater)
        setupRecyclerViews()
        setupViewPager()
        setupButtonListeners()
        return binding.root
    }

    private fun setupRecyclerViews() {
        setupRecyclerView(
            binding.rvPetMainNote,
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false),
            PetMainNoteAdapter(getNoteList())
        )
        setupRecyclerView(
            binding.rvPetMainHealth,
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false),
            PetMainHealthAdapter(getHealthList())
        )
        setupRecyclerView(
            binding.rvPetMainTraining,
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false),
            PetMainTrainingAdapter(getCheckedTrainingList())
        )
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView,
        layoutManager: LinearLayoutManager,
        adapter: RecyclerView.Adapter<*>
    ) {
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(VerticalDividerItemDecorator(5))
    }

    private fun setupViewPager() {
        val petIdxList = PetIndexList.get()
        val adapterMyPetList = PetMainMyPetAdapter(getMypetList(petIdxList.size))
        binding.vpMyPet.adapter = adapterMyPetList
        binding.vpMyPet.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.vpMyPet.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                requestPetInfo(petIdxList[position])
                requestPetTrainingInfo(petIdxList[position])
            }
        })
    }

    private fun setupButtonListeners() {
        binding.btnAddPet.setOnClickListener {
            val intent = Intent(requireContext(), PetMainAddPetActivity::class.java)
            startActivity(intent)
        }
    }

    private fun requestPetTrainingInfo(petIdx: Int) {
        val service = NetworkClient.getRetrofit().create(PetMainService::class.java)
        service.getTrainingStar(petIdx).enqueue(object : Callback<PetMainStarResponse> {
            override fun onResponse(
                call: Call<PetMainStarResponse>, response: Response<PetMainStarResponse>
            ) {
                if (response.isSuccessful && response.body()?.code == 200) {
                    val trainingData = response.body()?.result?.map {
                        PetMainTrainingData(it.name)
                    } ?: emptyList()

                    val adapter = PetMainTrainingAdapter(trainingData)
                    binding.rvPetMainTraining.adapter = adapter
                } else {
                    Log.d(TAG, "Training info request failed")
                }
            }

            override fun onFailure(call: Call<PetMainStarResponse>, t: Throwable) {
                Log.e(TAG, "Training info request failed", t)
            }
        })
    }

    private fun requestPetInfo(petIdx: Int) {
        val service = NetworkClient.getRetrofit().create(PetMainService::class.java)
        service.getPetDetails(petIdx).enqueue(object : Callback<PetMainResponse> {
            override fun onResponse(call: Call<PetMainResponse>, response: Response<PetMainResponse>) {
                if (response.isSuccessful && response.body()?.code == 200) {
                    updatePetInfo(response.body()!!)
                } else {
                    Log.d(TAG, "Pet info request failed")
                }
            }

            override fun onFailure(call: Call<PetMainResponse>, t: Throwable) {
                Log.e(TAG, "Pet info request failed", t)
            }
        })
    }

    private fun updatePetInfo(response: PetMainResponse) {
        response.result.firstOrNull()?.let { pet ->
            binding.tvMainPetName.text = pet.name
            binding.tvMainPetAge.text = "${pet.age}살"
            binding.tvMainPetDescription.text = "${pet.category} ${pet.species}"
            // 기타 펫 정보 UI 업데이트
        }
    }

    // Sample data generation methods
    private fun getNoteList(): ArrayList<PetMainNoteData> = arrayListOf(
        PetMainNoteData("간식 사러 가야 함"),
        PetMainNoteData("산책 부탁해야 함")
    )

    private fun getHealthList(): ArrayList<PetMainHealthData> = arrayListOf(
        PetMainHealthData("건강정보"),
        PetMainHealthData("건강정보")
    )

    private fun getCheckedTrainingList(): ArrayList<PetMainTrainingData> = arrayListOf(
        PetMainTrainingData("체크된 훈련")
    )

    private fun getMypetList(cnt: Int): ArrayList<PetMainMypetData> = ArrayList<PetMainMypetData>().apply {
        for (i in 0..cnt) {
            add(PetMainMypetData("https://example.com/mypet_image.jpg"))
        }
    }
}