package com.example.petmate.walk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.R

class WalkRecord(val itemList: ArrayList<WalkRecordData>) : RecyclerView.Adapter<WalkRecord.WalkRecordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalkRecordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_walk_record_list, parent, false)
        return WalkRecordViewHolder(view)
    }

    override fun onBindViewHolder(holder: WalkRecordViewHolder, position: Int) {
        holder.guardian.text = itemList[position].guardian
        holder.trainingtime.text = itemList[position].trainingtime
        holder.calories.text = itemList[position].calories
        holder.avgfrequency.text = itemList[position].avgfrequency
        holder.maxfrequency.text = itemList[position].maxfrequency
        holder.breaks.text = itemList[position].breaks
        holder.breaktimes.text = itemList[position].breaktimes
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }

    inner class WalkRecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val guardian = itemView.findViewById<TextView>(R.id.walk_record_guardian)
        val trainingtime = itemView.findViewById<TextView>(R.id.walk_record_trainingtime)
        val calories = itemView.findViewById<TextView>(R.id.walk_record_calories)
        val avgfrequency = itemView.findViewById<TextView>(R.id.walk_record_avgfrequency)
        val maxfrequency = itemView.findViewById<TextView>(R.id.walk_record_maxfrequency)
        val breaks = itemView.findViewById<TextView>(R.id.walk_record_breaks)
        val breaktimes = itemView.findViewById<TextView>(R.id.walk_record_breaktimes)
    }

}