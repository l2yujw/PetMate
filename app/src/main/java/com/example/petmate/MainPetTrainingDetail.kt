package com.example.petmate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainPetTrainingDetail(val itemList: ArrayList<MainPetTrainingDetailData>) : RecyclerView.Adapter<MainPetTrainingDetail.TrainingDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingDetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_main_pet_training_1_specifics, parent, false)
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