package com.example.petmate.myinf

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petmate.R
import com.example.petmate.databinding.ItemMyinfPostSubBinding
import com.example.petmate.home.petseeker.HomePetseekerRecommendPetListInterfaceResponseResponseResult

class MyinfPostSubAdapter(val itemList: ArrayList<MyinfPostSubData>) : RecyclerView.Adapter<MyinfPostSubAdapter.MyinfPostSubViewHolder>() {

    lateinit var binding: ItemMyinfPostSubBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyinfPostSubAdapter.MyinfPostSubViewHolder {
        binding = ItemMyinfPostSubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyinfPostSubViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyinfPostSubAdapter.MyinfPostSubViewHolder, position: Int) {
        holder.setItem(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class MyinfPostSubViewHolder(binding: ItemMyinfPostSubBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setItem(item: MyinfPostSubData) {

        }
    }
}