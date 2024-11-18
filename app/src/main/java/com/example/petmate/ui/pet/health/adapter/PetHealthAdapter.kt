package com.example.petmate.ui.pet.health.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.core.util.OnItemClickListener
import com.example.petmate.databinding.ItemPetHealthRecordBinding
import com.example.petmate.ui.pet.health.data.PetHealthData

class PetHealthAdapter(private val itemList: List<PetHealthData>) : RecyclerView.Adapter<PetHealthAdapter.PetHealthViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetHealthViewHolder {
        val binding = ItemPetHealthRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetHealthViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: PetHealthViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            itemClickListener?.onClick(it, position)
        }
    }

    inner class PetHealthViewHolder(private val binding: ItemPetHealthRecordBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PetHealthData) {
            binding.petHealthVaccinationRecord.text = item.record
        }
    }
}
