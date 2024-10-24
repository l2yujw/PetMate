package com.example.petmate.ui.pet.health.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.core.util.OnItemClickListener
import com.example.petmate.databinding.ItemPetHealthRecordBinding
import com.example.petmate.ui.pet.health.data.PetHealthData

class PetHealthAdapter(val itemList: ArrayList<PetHealthData>) : RecyclerView.Adapter<PetHealthAdapter.PetHealthViewHolder>(){
    private lateinit var itemClickListener : OnItemClickListener
    lateinit var binding: ItemPetHealthRecordBinding
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetHealthViewHolder {
        binding = ItemPetHealthRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetHealthViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: PetHealthViewHolder, position: Int) {
        val item = itemList[position]
        holder.setItem(item)
        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position)
        }
    }

    inner class PetHealthViewHolder(binding: ItemPetHealthRecordBinding) : RecyclerView.ViewHolder(binding.root){
        fun setItem(item: PetHealthData) {
            binding.petHealthVaccinationRecord.text = item.record
        }
    }
}