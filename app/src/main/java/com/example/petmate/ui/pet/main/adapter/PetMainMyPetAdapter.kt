package com.example.petmate.ui.pet.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.databinding.ItemPetMainMypetBinding
import com.example.petmate.ui.pet.main.data.PetMainMypetData
import com.example.petmate.util.GlideHelper

class PetMainMyPetAdapter(val itemList: List<PetMainMypetData>) :
    RecyclerView.Adapter<PetMainMyPetAdapter.MyPetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPetViewHolder {
        val binding = ItemPetMainMypetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyPetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyPetViewHolder, position: Int) {
        val item = itemList[position]
        holder.setItem(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class MyPetViewHolder(private val binding: ItemPetMainMypetBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setItem(item: PetMainMypetData) {
            GlideHelper.loadImage(binding.imgPetMainMypet, item.image)
        }
    }
}
