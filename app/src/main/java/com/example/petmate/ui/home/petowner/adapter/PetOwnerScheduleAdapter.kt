package com.example.petmate.ui.home.petowner.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.R
import com.example.petmate.ui.home.petowner.data.PetOwnerScheduleData

class PetOwnerScheduleAdapter(val itemList: ArrayList<PetOwnerScheduleData>) : RecyclerView.Adapter<PetOwnerScheduleAdapter.ScheduleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_petowner_schedule, parent, false)
        return ScheduleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.schedule_time.text = itemList[position].time
        holder.schedule_maintext.text = itemList[position].mainText
        holder.schedule_subtext.text = itemList[position].subText
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }


    inner class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val schedule_time = itemView.findViewById<TextView>(R.id.petowner_schedule_time)
        val schedule_maintext = itemView.findViewById<TextView>(R.id.petowner_schedule_maintext)
        val schedule_subtext = itemView.findViewById<TextView>(R.id.petowner_schedule_subtext)
    }
}