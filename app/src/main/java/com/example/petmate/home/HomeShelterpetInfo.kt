package com.example.petmate.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.R

class HomeShelterpetInfo(val itemList: ArrayList<HomeShelterpetInfoData>) : RecyclerView.Adapter<HomeShelterpetInfo.AbandonedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbandonedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_home_shelter_infolayout, parent, false)
        return AbandonedViewHolder(view)
    }

    override fun onBindViewHolder(holder: AbandonedViewHolder, position: Int) {
        holder.info_tag.text = itemList[position].info_tag
        holder.info_info.text = itemList[position].info_info
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }


    inner class AbandonedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val info_tag = itemView.findViewById<TextView>(R.id.abandonedinfo_additionalinfo_tag)
        val info_info = itemView.findViewById<TextView>(R.id.abandonedinfo_additionalinfo_info)
    }
}