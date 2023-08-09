package com.example.petmate.home.petowner

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.R

class HomePetownerPetlistAdapter(petImageList: ArrayList<Int>, petTextList: ArrayList<HomePetownerPetlistData>) : RecyclerView.Adapter<HomePetownerPetlistAdapter.PetownerPetlistViewHolder>() {

    var imageItem = petImageList
    var randomTextItem = petTextList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PetownerPetlistViewHolder((parent))

    override fun getItemCount(): Int = imageItem.size

    override fun onBindViewHolder(holder: PetownerPetlistViewHolder, position: Int) {
        holder.image.setImageResource(imageItem[position])
        holder.randomText.text = randomTextItem[position].petlist_randomtext
    }


    inner class PetownerPetlistViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.item_home_petowner_petlist, parent, false)) {

        val randomText = itemView.findViewById<TextView>(R.id.petowner_petlist_randomtext)
        val image = itemView.findViewById<ImageView>(R.id.petowner_petlist_image)
    }
}