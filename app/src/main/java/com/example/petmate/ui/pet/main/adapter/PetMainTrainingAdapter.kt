package com.example.petmate.ui.pet.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.core.util.OnItemClickListener
import com.example.petmate.R
import com.example.petmate.ui.pet.main.data.PetMainTrainingData

class PetMainTrainingAdapter(val itemList: List<PetMainTrainingData>) : RecyclerView.Adapter<PetMainTrainingAdapter.PetMainTrainingViewHolder>() {

    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetMainTrainingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pet_main_training, parent, false)
        return PetMainTrainingViewHolder(view)
    }

    override fun onBindViewHolder(holder: PetMainTrainingViewHolder, position: Int) {
        holder.checkedTraining.text = itemList[position].checkedTraining
        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }

    inner class PetMainTrainingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkedTraining: TextView = itemView.findViewById(R.id.main_checkedtraining_text)
    }
}