package com.example.petmate.ui.home.petseeker.fragment

import PetSeekerListAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.petmate.R
import com.example.petmate.core.classifier.Classifier
import com.example.petmate.core.network.NetworkClient
import com.example.petmate.core.util.GlobalUserId
import com.example.petmate.databinding.FragmentHomePetseekerBinding
import com.example.petmate.remote.api.home.petseeker.PetSeekerService
import com.example.petmate.remote.response.home.petseeker.PetSeekerDetailInfoResponse
import com.example.petmate.remote.response.home.petseeker.PetSeekerDetailInfoResult
import com.example.petmate.remote.response.home.petseeker.PetSeekerInfoResponse
import com.example.petmate.ui.home.petseeker.adapter.PetSeekerRecommendAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PetSeekerFragment : Fragment() {

    private lateinit var binding: FragmentHomePetseekerBinding
    private val TAG = PetSeekerFragment::class.java.simpleName
    private lateinit var classifier: Classifier
    private val careCenterPhoneNumbers = arrayOf("031-5189-7099", "031-228-3328")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString("isUser")?.let {
            Log.d(TAG, "petseeker obj: $it")
        }
    }

    @SuppressLint("SetTextI18n", "MissingPermission")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomePetseekerBinding.inflate(inflater)

        val userId = GlobalUserId.getUserId()
        setupUi()
        loadPetRecommendations(userId)
        loadRecentPets("%")

        return binding.root
    }

    private fun setupUi() {
        setupButtonListeners()
        setupSpinner()
    }

    private fun setupButtonListeners() {
        binding.btnFilterAll.setOnClickListener { loadRecentPets("%") }
        binding.btnFilterCat.setOnClickListener { loadRecentPets("고양이") }
        binding.btnFilterDog.setOnClickListener { loadRecentPets("개") }
        binding.btnFilterOthers.setOnClickListener { loadRecentPets("기타축종") }

        binding.btnCallCenter.setOnClickListener {
            handleCenterNumberClick()
        }
    }

    private fun setupSpinner() {
        val spinnerItems = arrayOf("전체", "남양동물보호센터", "수원시 동물보호센터")
        val adapterSpinner = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        binding.spinnerFilterOptions.adapter = adapterSpinner
        binding.spinnerFilterOptions.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                loadCareCenters(spinnerItems[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun handleCenterNumberClick() {
        val selectedPosition = binding.spinnerFilterOptions.selectedItemId
        if (selectedPosition > 0) {
            val phoneNumber = careCenterPhoneNumbers[selectedPosition.toInt() - 1]
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tel:$phoneNumber"))
            startActivity(intent)
        } else {
            Toast.makeText(context, "보호소를 선택해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadCareCenters(careName: String) {
        val service = NetworkClient.getRetrofit().create(PetSeekerService::class.java)
        service.getCareNmList(careName).enqueue(object : Callback<PetSeekerDetailInfoResponse> {
            override fun onResponse(call: Call<PetSeekerDetailInfoResponse>, response: Response<PetSeekerDetailInfoResponse>) {
                response.body()?.let {
                    if (it.code == 200) {
                        setupPetListView(it.result)
                    }
                }
            }

            override fun onFailure(call: Call<PetSeekerDetailInfoResponse>, t: Throwable) {
                Log.e(TAG, "Failed to load care centers: ${t.message}")
            }
        })
    }

    private fun loadPetRecommendations(userId: Int) {
        val service = NetworkClient.getRetrofit().create(PetSeekerService::class.java)
        service.getRecommend(userId).enqueue(object : Callback<PetSeekerInfoResponse> {
            override fun onResponse(call: Call<PetSeekerInfoResponse>, response: Response<PetSeekerInfoResponse>) {
                response.body()?.let {
                    if (it.code == 200) {
                        val recommend = it.result.firstOrNull()?.recommend ?: "@"
                        loadRecommendedPets(recommend)
                    }
                }
            }

            override fun onFailure(call: Call<PetSeekerInfoResponse>, t: Throwable) {
                Log.e(TAG, "Failed to load pet recommendations: ${t.message}")
            }
        })
    }

    private fun loadRecommendedPets(recommend: String) {
        val service = NetworkClient.getRetrofit().create(PetSeekerService::class.java)
        service.getPetsBySpecies(recommend).enqueue(object : Callback<PetSeekerDetailInfoResponse> {
            override fun onResponse(call: Call<PetSeekerDetailInfoResponse>, response: Response<PetSeekerDetailInfoResponse>) {
                response.body()?.let {
                    if (it.code == 200) {
                        setupRecommendView(it.result)
                    }
                }
            }

            override fun onFailure(call: Call<PetSeekerDetailInfoResponse>, t: Throwable) {
                Log.e(TAG, "Failed to load recommended pets: ${t.message}")
            }
        })
    }

    private fun loadRecentPets(category: String) {
        val service = NetworkClient.getRetrofit().create(PetSeekerService::class.java)
        service.getCategoryPetList(category).enqueue(object : Callback<PetSeekerDetailInfoResponse> {
            override fun onResponse(call: Call<PetSeekerDetailInfoResponse>, response: Response<PetSeekerDetailInfoResponse>) {
                response.body()?.let {
                    if (it.code == 200) {
                        setupPetListView(it.result)
                    }
                }
            }

            override fun onFailure(call: Call<PetSeekerDetailInfoResponse>, t: Throwable) {
                Log.e(TAG, "Failed to load recent pets: ${t.message}")
            }
        })
    }

    private fun setupPetListView(petList: List<PetSeekerDetailInfoResult>) {
        val isUser = arguments?.getString("isUser") ?: "defaultUser"
        val adapter = PetSeekerListAdapter(petList, isUser)
        adapter.notifyDataSetChanged()

        binding.vpPetSeekerList.adapter = adapter
        binding.ciPetSeekerList.setViewPager(binding.vpPetSeekerList)
        binding.ciPetSeekerList.createIndicators(petList.size / 3, 0)
    }


    private fun setupRecommendView(petList: List<PetSeekerDetailInfoResult>) {
        val isUser = arguments?.getString("isUser") ?: "defaultUser"
        val adapter = PetSeekerRecommendAdapter(petList, isUser)
        adapter.notifyDataSetChanged()

        binding.vpPetSeekerRecommend.adapter = adapter
        binding.ciPetSeekerRecommend.setViewPager(binding.vpPetSeekerRecommend)
        binding.ciPetSeekerRecommend.createIndicators(petList.size, 0)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_homePetseekerFragment_to_homePetownerFragment)
            }
        })
    }
}
