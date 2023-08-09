package com.example.petmate.home.petowner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.R

class HomePetownerPetlistAdapter(petList: ArrayList<Int>) : RecyclerView.Adapter<HomePetownerPetlistAdapter.PetownerPetlistViewHolder>() {

    var item = petList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PetownerPetlistViewHolder((parent))

    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: PetownerPetlistViewHolder, position: Int) {
        holder.pet.setImageResource(item[position])
    }


    inner class PetownerPetlistViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.item_home_petowner_petlist, parent, false)) {

        val pet = itemView.findViewById<ImageView>(R.id.petowner_petlist_image)
    }
}