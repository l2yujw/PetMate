package com.example.petmate.pet.training.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.R

class PetTrainingDetail(val itemList: ArrayList<PetTrainingDetailData>) : RecyclerView.Adapter<PetTrainingDetail.TrainingDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingDetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_pet_training_1_specifics, parent, false)
        return TrainingDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrainingDetailViewHolder, position: Int) {
        holder.level.text = itemList[position].level
        holder.detail.text = itemList[position].detail
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }


    inner class TrainingDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val level = itemView.findViewById<TextView>(R.id.pet_tarining_1_level)
        val detail = itemView.findViewById<TextView>(R.id.pet_tarining_1_detail)
    }
}