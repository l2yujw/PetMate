package com.example.petmate.pet.health

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.petmate.HorizontalItemDecorator
import com.example.petmate.R
import com.example.petmate.VerticalItemDecorator
import com.example.petmate.databinding.FragmentPetHealthBinding
import com.example.petmate.home.petowner.HomePetownerPetlistAdapter
import com.example.petmate.home.petowner.HomePetownerPetlistData
import java.util.ArrayList

class PetHealthFragment : Fragment() {

    lateinit var binding: FragmentPetHealthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_petHealthFragment_to_petMainFragment)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPetHealthBinding.inflate(inflater)

        getInfoList()

        return binding.getRoot()
    }

    private fun getRecordList(): ArrayList<PetHealthData> {
        val recordList = ArrayList<PetHealthData>()

        recordList.add(PetHealthData("2021.05.30"))
        recordList.add(PetHealthData("2022.05.30"))
        recordList.add(PetHealthData("2023.05.30"))

        return recordList
    }

    private fun getPetList(): ArrayList<PetHealthListData> {
        val petList = ArrayList<PetHealthListData>()

        petList.add(PetHealthListData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        petList.add(PetHealthListData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        petList.add(PetHealthListData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))

        return petList
    }

    // 성별
    fun getSexImage(sex: String): Int {
        return when (sex) {
            "수컷" -> R.drawable.sex_male
            "암컷" -> R.drawable.sex_female
            else -> R.drawable.sex_male
        }
    }

    private fun getInfoList() {
        binding.petHealthPetname.text = "탈주닌자"
        binding.petHealthPetdescription.text = "놀라버린 고양이"
        binding.petHealthPetage.text = "1살"
        binding.petHealthSexImage.setImageResource(getSexImage("수컷"))
        binding.tvHealthVaccination.text = "2024.05.30"
        binding.tvHealthHelminthic.text = "2024.05.30"
        binding.tvHealthWeight.text = "5"
        binding.tvHealthWeightCondition.text = "정상"
        binding.tvHealthQuantity.text = "500"
        binding.tvHealthKcal.text = "250"
        binding.petHealthKnow.text = "알고 계셨나요?"

        val boardAdapterPetHealthRecord = PetHealthAdapter(getRecordList())
        boardAdapterPetHealthRecord.notifyDataSetChanged()
        val boardAdapterPetHealthPetlist = PetHealthListAdapter(getPetList())
        boardAdapterPetHealthPetlist.notifyDataSetChanged()

        binding.rcvHealthRecord.adapter = PetHealthAdapter(getRecordList())
        binding.rcvHealthRecord.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.viewpagerHealthPetlist.adapter = PetHealthListAdapter(getPetList())
        binding.viewpagerHealthPetlist.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        //item 간격 결정
        binding.rcvHealthRecord.addItemDecoration(VerticalItemDecorator(10))
    }
}