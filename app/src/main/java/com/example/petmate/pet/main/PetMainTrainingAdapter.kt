package com.example.petmate.pet.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.R

class PetMainTrainingAdapter(val itemList: ArrayList<PetMainTrainingData>) : RecyclerView.Adapter<PetMainTrainingAdapter.PetMainTrainingiewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetMainTrainingiewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_pet_main_checkedtraining_layout, parent, false)
        return PetMainTrainingiewHolder(view)
    }

    override fun onBindViewHolder(holder: PetMainTrainingiewHolder, position: Int) {
        holder.checkedTraining.text = itemList[position].checkedTraining
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }


    inner class PetMainTrainingiewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkedTraining = itemView.findViewById<TextView>(R.id.main_checkedtraining_text)
    }
}