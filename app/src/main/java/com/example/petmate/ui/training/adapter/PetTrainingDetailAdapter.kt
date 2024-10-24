package com.example.petmate.ui.training.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.R
import com.example.petmate.ui.training.data.PetTrainingDetailData

class PetTrainingDetailAdapter(val itemList: ArrayList<PetTrainingDetailData>) : RecyclerView.Adapter<PetTrainingDetailAdapter.TrainingDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingDetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pet_training_detail_ways, parent, false)
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
        val level: TextView = itemView.findViewById<TextView>(R.id.pet_tarining_level)
        val detail: TextView = itemView.findViewById<TextView>(R.id.pet_tarining_detail)
    }
}