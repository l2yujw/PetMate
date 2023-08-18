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

class PetTrainingFragment : Fragment() {


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

        var rootView = inflater.inflate(R.layout.fragment_pet_training, container, false)

        var rcv_training_list = rootView.findViewById<RecyclerView>(R.id.rcv_pet_training_list)
        // Inflate the layout for this fragment

        val adapterTrainingList = PetTrainingListAdapter(getPetImageList())
        adapterTrainingList.notifyDataSetChanged()

        rcv_training_list.adapter = adapterTrainingList
        rcv_training_list.layoutManager = GridLayoutManager(requireContext(), 2)
        //item 간격 결정
        rcv_training_list.addItemDecoration(HorizontalItemDecorator(50))
        rcv_training_list.addItemDecoration(VerticalItemDecorator(50))

        adapterTrainingList.setItemClickListener(object : OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                findNavController().navigate(R.id.action_petTrainingFragment_to_petTrainingDetailFragment)
            }
        })
        return rootView
    }

    private fun getPetImageList(): ArrayList<Int>{
        return arrayListOf<Int>(R.drawable.cat1_temp, R.drawable.cat2_temp, R.drawable.cat1_temp)
    }

}