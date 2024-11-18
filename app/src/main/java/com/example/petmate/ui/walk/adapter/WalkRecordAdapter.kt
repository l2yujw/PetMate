package com.example.petmate.ui.walk.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.databinding.ItemWalkRecordListBinding
import com.example.petmate.ui.walk.data.WalkRecordData

class WalkRecordAdapter(
    private val itemList: List<WalkRecordData> // 불변성을 유지하기 위해 List 사용
) : RecyclerView.Adapter<WalkRecordAdapter.WalkRecordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalkRecordViewHolder {
        val binding = ItemWalkRecordListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WalkRecordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WalkRecordViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    inner class WalkRecordViewHolder(private val binding: ItemWalkRecordListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WalkRecordData) {
            binding.apply {
                walkRecordGuardian.text = item.guardian
                walkRecordTrainingtime.text = item.trainingTime
                walkRecordCalories.text = item.calories
                walkRecordAvgfrequency.text = item.avgFrequency
                walkRecordMaxfrequency.text = item.maxFrequency
                walkRecordBreaks.text = item.breaks
                walkRecordBreaktimes.text = item.breakTimes
            }
        }
    }
}
