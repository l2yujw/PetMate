package com.example.petmate.pet.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.petmate.OnItemClickListener
import com.example.petmate.R
import com.example.petmate.databinding.FragmentPetMainBinding

class PetMainFragment : Fragment() {

    lateinit var binding: FragmentPetMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPetMainBinding.inflate(inflater)

        val adapterNoteList = PetMainNoteAdapter(getNoteList())
        adapterNoteList.notifyDataSetChanged()
        val adapterHealthList = PetMainHealthAdapter(getHealthList())
        adapterHealthList.notifyDataSetChanged()
        val adapterCheckedTrainingList = PetMainTrainingAdapter(getCheckedTrainingList())
        adapterCheckedTrainingList.notifyDataSetChanged()
        val adapterMyPetList = PetMainMypetAdapter(getMypetList())
        adapterMyPetList.notifyDataSetChanged()

        val indicatorMypet = binding.circleindicatorPetmainMypet
        indicatorMypet.setViewPager(binding.viewpagerPetMainMyPet)
        indicatorMypet.createIndicators(getMypetList().size, 0)

        binding.rcvPetMainNote.adapter = adapterNoteList
        binding.rcvPetMainNote.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.rcvPetMainHealth.adapter = adapterHealthList
        binding.rcvPetMainHealth.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.viewpagerPetMainMyPet.adapter = adapterMyPetList
        binding.viewpagerPetMainMyPet.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewpagerPetMainMyPet.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                indicatorMypet.animatePageSelected(position)
                //Toast.makeText(requireContext(), "${position + 1} 페이지 선택됨", Toast.LENGTH_SHORT).show()
            }
        })

        adapterHealthList.setItemClickListener(object : OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                findNavController().navigate(R.id.action_petMainFragment_to_petHealthFragment)
            }
        })

        binding.rcvPetMainTraining.adapter = adapterCheckedTrainingList
        binding.rcvPetMainTraining.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        adapterCheckedTrainingList.setItemClickListener(object : OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                findNavController().navigate(R.id.action_petMainFragment_to_petTrainingFragment)
            }
        })

        binding.tvMainPetName.text = "탈주닌자"
        binding.tvMainPetAge.text = "특징" + "나이"

        return binding.getRoot()
    }

    private fun getNoteList(): ArrayList<PetMainNoteData> {
        val noteList = ArrayList<PetMainNoteData>()

        noteList.add(PetMainNoteData("메모테스트"))
        noteList.add(PetMainNoteData("메모테스트"))
        noteList.add(PetMainNoteData("메모테스트"))

        return noteList
    }

    private fun getHealthList(): ArrayList<PetMainHealthData> {
        val healthList = ArrayList<PetMainHealthData>()

        healthList.add(PetMainHealthData("건강정보"))
        healthList.add(PetMainHealthData("건강정보"))
        healthList.add(PetMainHealthData("건강정보"))

        return healthList
    }

    private fun getCheckedTrainingList(): ArrayList<PetMainTrainingData> {
        val checkedTrainingList = ArrayList<PetMainTrainingData>()

        checkedTrainingList.add(PetMainTrainingData("체크된 훈련"))
        checkedTrainingList.add(PetMainTrainingData("체크된 훈련"))
        checkedTrainingList.add(PetMainTrainingData("체크된 훈련"))

        return checkedTrainingList
    }

    private fun getMypetList(): ArrayList<PetMainMypetData> {
        val recommendList = java.util.ArrayList<PetMainMypetData>()

        recommendList.add(PetMainMypetData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        recommendList.add(PetMainMypetData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))

        return recommendList
    }

}