package com.example.petmate.ui.home.petseeker.shelterpetInfo.fragment

import ShelterPetInfoAdapter
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
import com.example.petmate.core.decorator.VerticalDividerItemDecorator
import com.example.petmate.databinding.FragmentHomeShelterpetInfoBinding
import com.example.petmate.remote.response.home.petseeker.PetSeekerDetailInfoResult
import com.example.petmate.ui.home.petseeker.shelterpetInfo.data.ShelterpetInfoData


class ShelterPetInfoFragment : Fragment() {

    lateinit var binding: FragmentHomeShelterpetInfoBinding
    private val TAG = "ShelterpetInfoFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupOnBackPressedCallback()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeShelterpetInfoBinding.inflate(inflater)
        Log.d(TAG, "onCreateView: ")

        arguments?.getParcelable<PetSeekerDetailInfoResult>("ShelterpetInfo")?.let { petInfo ->
            setupUI(petInfo)
        }

        return binding.root
    }

    private fun setupUI(petInfo: PetSeekerDetailInfoResult) {
        // Glide 사용하여 비동기 이미지 로딩
        Glide.with(this)
            .load(petInfo.imageUrl)
            .placeholder(R.drawable.background_glide_init)
            .error(R.drawable.background_glide_init)
            .into(binding.ivShelterPetImage)

        binding.tvShelterPetName.text = petInfo.species
        binding.tvShelterPetSex.setImageResource(getGenderImageResource(petInfo.gender))
        binding.tvShelterPetAge.text = "${2023 - petInfo.age+1}살"
        binding.tvShelterWeight.text = "${petInfo.weight.split("(")[0]}Kg"
        binding.tvShelterPetNeutered.text = "아니오"
        binding.tvShelterPetColor.text = petInfo.colorCd
        binding.tvShelterPetCharacter.text = petInfo.characteristic


        // 데이터 리스트 생성 및 RecyclerView 설정
        val shelterpetInfoList = createShelterpetInfoList(petInfo)
        setupRecyclerView(shelterpetInfoList)
    }

    private fun createShelterpetInfoList(petInfo: PetSeekerDetailInfoResult): List<ShelterpetInfoData> {
        return listOf(
            ShelterpetInfoData("발생장소", petInfo.discoveryPlace),
            ShelterpetInfoData("접수일시", petInfo.receiptDate),
            ShelterpetInfoData("관할기관", petInfo.orgNm),
            ShelterpetInfoData("상태", "보호중"),
            ShelterpetInfoData("보호장소", petInfo.careAddr),
            ShelterpetInfoData("보호센터연락처", petInfo.officetel.replace("$", ""))
        )
    }

    private fun setupRecyclerView(shelterpetInfoList: List<ShelterpetInfoData>) {
        val adapter = ShelterPetInfoAdapter(shelterpetInfoList)
        binding.rvShelterPetInf.adapter = adapter
        binding.rvShelterPetInf.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvShelterPetInf.addItemDecoration(VerticalDividerItemDecorator(5))
    }

    private fun setupOnBackPressedCallback() {
        arguments?.getString("isUser")?.let { isUser ->
            val bundle = Bundle().apply { putString("isUser", isUser) }
            requireActivity().onBackPressedDispatcher.addCallback(this) {
                findNavController().navigate(R.id.action_homeShelterpetInfoFragment_to_homePetseekerFragment, bundle)
            }
        } ?: run {
            requireActivity().onBackPressedDispatcher.addCallback(this) {
                findNavController().navigate(R.id.action_homeShelterpetInfoFragment_to_homePetseekerFragment)
            }
        }
    }

    private fun getGenderImageResource(sex: String): Int {
        return when (sex) {
            "수컷", "M" -> R.drawable.sex_male
            "암컷", "F" -> R.drawable.sex_female
            else -> R.drawable.sex_male
        }
    }
}