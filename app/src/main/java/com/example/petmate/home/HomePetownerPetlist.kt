package com.example.petmate.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.R

class HomePetownerPetlist(val itemList: ArrayList<HomePetownerPetlistData>) : RecyclerView.Adapter<HomePetownerPetlist.HavepetPetlistViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HavepetPetlistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_home_petowner_petlist_layout, parent, false)
        return HavepetPetlistViewHolder(view)
    }

    override fun onBindViewHolder(holder: HavepetPetlistViewHolder, position: Int) {
        holder.randomtext.text = itemList[position].petlist_randomtext
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }


    inner class HavepetPetlistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val randomtext = itemView.findViewById<TextView>(R.id.havepet_petlist_randomtext)
    }
}