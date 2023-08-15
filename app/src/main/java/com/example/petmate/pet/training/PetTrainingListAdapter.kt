package com.example.petmate.pet.training

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.OnItemClickListener
import com.example.petmate.R
import com.example.petmate.pet.main.PetMainHealthAdapter
import com.example.petmate.pet.main.PetMainHealthData

class PetTrainingListAdapter(val itemList: ArrayList<Int>) : RecyclerView.Adapter<PetTrainingListAdapter.PetTrainingListViewHolder>() {


    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetTrainingListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pet_training_list, parent, false)
        return PetTrainingListViewHolder(view)
    }

    override fun onBindViewHolder(holder: PetTrainingListViewHolder, position: Int) {
        holder.trainingImage.setImageResource(itemList[position])
        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }


    inner class PetTrainingListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val trainingImage = itemView.findViewById<ImageView>(R.id.img_pet_training)
    }
}