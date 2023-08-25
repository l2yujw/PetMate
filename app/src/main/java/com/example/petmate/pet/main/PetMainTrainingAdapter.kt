package com.example.petmate.pet.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.OnItemClickListener
import com.example.petmate.R

class PetMainTrainingAdapter(val itemList: ArrayList<PetMainTrainingData>) : RecyclerView.Adapter<PetMainTrainingAdapter.PetMainTrainingiewHolder>() {

    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetMainTrainingiewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pet_main_training, parent, false)
        return PetMainTrainingiewHolder(view)
    }

    override fun onBindViewHolder(holder: PetMainTrainingiewHolder, position: Int) {
        holder.checkedTraining.text = itemList[position].checkedTraining
        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }

    inner class PetMainTrainingiewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkedTraining = itemView.findViewById<TextView>(R.id.main_checkedtraining_text)
    }
}