package com.example.petmate.ui.home.petowner.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.R
import com.example.petmate.ui.home.petowner.data.HomePetownerScheduleData

class HomePetownerScheduleAdapter(val itemList: ArrayList<HomePetownerScheduleData>) : RecyclerView.Adapter<HomePetownerScheduleAdapter.HavepetScheduleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HavepetScheduleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_petowner_schedule, parent, false)
        return HavepetScheduleViewHolder(view)
    }

    override fun onBindViewHolder(holder: HavepetScheduleViewHolder, position: Int) {
        holder.schedule_time.text = itemList[position].schedule_time
        holder.schedule_maintext.text = itemList[position].schedule_maintext
        holder.schedule_subtext.text = itemList[position].schedule_subtext
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }


    inner class HavepetScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val schedule_time = itemView.findViewById<TextView>(R.id.petowner_schedule_time)
        val schedule_maintext = itemView.findViewById<TextView>(R.id.petowner_schedule_maintext)
        val schedule_subtext = itemView.findViewById<TextView>(R.id.petowner_schedule_subtext)
    }
}