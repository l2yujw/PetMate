package com.example.petmate.pet.training

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.VerticalItemDecorator
import com.example.petmate.OnItemClickListener
import com.example.petmate.R
import com.example.petmate.HorizontalItemDecorator
import com.example.petmate.databinding.FragmentPetTrainingBinding
import com.example.petmate.home.petowner.HomePetownerPetlistData
import com.example.petmate.pet.main.PetMainHealthData

class PetTrainingFragment : Fragment() {

    lateinit var binding: FragmentPetTrainingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this){
            findNavController().navigate(R.id.action_petTrainingFragment_to_petMainFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPetTrainingBinding.inflate(inflater)

        val adapterTrainingList = PetTrainingListAdapter(getPetImageList())
        adapterTrainingList.notifyDataSetChanged()

        binding.rcvPetTrainingList.adapter = adapterTrainingList
        binding.rcvPetTrainingList.layoutManager = GridLayoutManager(requireContext(), 2)
        //item 간격 결정
        binding.rcvPetTrainingList.addItemDecoration(HorizontalItemDecorator(20))
        binding.rcvPetTrainingList.addItemDecoration(VerticalItemDecorator(20))

        adapterTrainingList.setItemClickListener(object : OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                findNavController().navigate(R.id.action_petTrainingFragment_to_petTrainingDetailFragment)
            }
        })
        return binding.getRoot()
    }

    private fun getPetImageList(): ArrayList<PetTrainingListData>{
        val petTrainingList = ArrayList<PetTrainingListData>()

        petTrainingList.add(PetTrainingListData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        petTrainingList.add(PetTrainingListData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        petTrainingList.add(PetTrainingListData( "https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))

        return petTrainingList
    }

}