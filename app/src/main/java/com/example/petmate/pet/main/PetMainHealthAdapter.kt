package com.example.petmate.pet.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.OnItemClickListener
import com.example.petmate.R

class PetMainHealthAdapter(val itemList: ArrayList<PetMainHealthData>) : RecyclerView.Adapter<PetMainHealthAdapter.PetMainHealthViewHolder>() {


    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetMainHealthViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pet_main_health, parent, false)
        return PetMainHealthViewHolder(view)
    }

    override fun onBindViewHolder(holder: PetMainHealthViewHolder, position: Int) {
        holder.health.text = itemList[position].health
        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position)
        }
    }


    override fun getItemCount(): Int {
        return itemList.count()
    }


    inner class PetMainHealthViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val health = itemView.findViewById<TextView>(R.id.main_health_text)
    }
}