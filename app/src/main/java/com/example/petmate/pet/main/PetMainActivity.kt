package com.example.petmate.pet.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.R

class PetMainActivity  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_main)

        val rv_board_note = findViewById<RecyclerView>(R.id.pet_main_note_recyclerview)
        val rv_board_health = findViewById<RecyclerView>(R.id.pet_main_health_recyclerview)
        val rv_board_training = findViewById<RecyclerView>(R.id.pet_main_training_recyclerview)

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

        val boardAdapterNoteList = PetMainNote(NoteList)
        boardAdapterNoteList.notifyDataSetChanged()
        val boardAdapterHealthList = PetMainHealth(HealthList)
        boardAdapterHealthList.notifyDataSetChanged()
        val boardAdapterCheckedTrainingList = PetMainTraining(CheckedTrainingList)
        boardAdapterCheckedTrainingList.notifyDataSetChanged()

        rv_board_note.adapter = boardAdapterNoteList
        rv_board_note.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        rv_board_health.adapter = boardAdapterHealthList
        rv_board_health.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        rv_board_training.adapter = boardAdapterCheckedTrainingList
        rv_board_training.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}