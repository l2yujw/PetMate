package com.example.petmate.ui.home.petowner.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.databinding.ItemHomePetownerPetlistBinding
import com.example.petmate.ui.home.petowner.data.PetOwnerPetListData
import com.example.petmate.util.GlideHelper

class PetOwnerPetListAdapter(private var petList: ArrayList<PetOwnerPetListData>) : RecyclerView.Adapter<PetOwnerPetListAdapter.PetListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetListViewHolder {
        val binding = ItemHomePetownerPetlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetListViewHolder(binding)
    }

    override fun getItemCount(): Int = petList.size

    override fun onBindViewHolder(holder: PetListViewHolder, position: Int) {
        val item = petList[position]
        holder.bind(item)
    }

    inner class PetListViewHolder(private val binding: ItemHomePetownerPetlistBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PetOwnerPetListData) {
            // 텍스트 설정
            binding.petownerPetlistRandomtext.text = item.randomText

            // GlideHelper를 통해 이미지 처리
            GlideHelper.loadImage(binding.petownerPetlistImage, item.imageUrl)
        }
    }
}
