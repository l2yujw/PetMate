package com.example.petmate.pet.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.OnItemClickListener
import com.example.petmate.R
import com.example.petmate.navigation.BottomNavActivity
import com.example.petmate.pet.health.PetHealthActivity
import com.example.petmate.pet.training.detail.PetTrainingActivity


class PetMainFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var rootView = inflater.inflate(R.layout.fragment_pet_main, container, false)

        val rv_board_note = rootView.findViewById<RecyclerView>(R.id.rcv_pet_main_note)
        val rv_board_health = rootView.findViewById<RecyclerView>(R.id.rcv_pet_main_health)
        val rv_board_training = rootView.findViewById<RecyclerView>(R.id.rcv_pet_main_training)

        val NoteList = ArrayList<PetMainNoteData>()
        val HealthList = ArrayList<PetMainHealthData>()
        val CheckedTrainingList = ArrayList<PetMainTrainingData>()

        NoteList.add(PetMainNoteData("메모테스트"))
        NoteList.add(PetMainNoteData("메모테스트"))
        NoteList.add(PetMainNoteData("메모테스트"))

        HealthList.add(PetMainHealthData("건강정보"))
        HealthList.add(PetMainHealthData("건강정보"))
        HealthList.add(PetMainHealthData("건강정보"))

        CheckedTrainingList.add(PetMainTrainingData("체크된 훈련"))
        CheckedTrainingList.add(PetMainTrainingData("체크된 훈련"))
        CheckedTrainingList.add(PetMainTrainingData("체크된 훈련"))

        val boardAdapterNoteList = PetMainNoteAdapter(NoteList)
        boardAdapterNoteList.notifyDataSetChanged()
        val boardAdapterHealthList = PetMainHealthAdapter(HealthList)
        boardAdapterHealthList.notifyDataSetChanged()
        val boardAdapterCheckedTrainingList = PetMainTrainingAdapter(CheckedTrainingList)
        boardAdapterCheckedTrainingList.notifyDataSetChanged()

        rv_board_note.adapter = boardAdapterNoteList
        rv_board_note.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        rv_board_health.adapter = boardAdapterHealthList
        rv_board_health.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        //BottomNavigation으로 터치 인식 값 보내서 Fragment 전환 할 계획임
        boardAdapterHealthList.setItemClickListener(object : OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                activity?.let {
                    val intent = Intent(context, PetHealthActivity::class.java)
                    startActivity(intent)
                }
            }
        })

        rv_board_training.adapter = boardAdapterCheckedTrainingList
        rv_board_training.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        boardAdapterCheckedTrainingList.setItemClickListener(object : OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                activity?.let {
                    val intent = Intent(context, PetTrainingActivity::class.java)
                    startActivity(intent)
                }
            }
        })

        return rootView
    }

}