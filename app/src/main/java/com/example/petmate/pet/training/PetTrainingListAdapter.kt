package com.example.petmate.pet.training

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petmate.OnItemClickListener
import com.example.petmate.R
import com.example.petmate.databinding.ItemHomePetownerPetlistBinding
import com.example.petmate.databinding.ItemPetTrainingListBinding
import com.example.petmate.home.petowner.HomePetownerPetlistData
import com.example.petmate.pet.main.PetMainHealthAdapter
import com.example.petmate.pet.main.PetMainHealthData

class PetTrainingListAdapter(val itemList: ArrayList<PetTrainingListData>) : RecyclerView.Adapter<PetTrainingListAdapter.PetTrainingListViewHolder>() {

    private lateinit var itemClickListener : OnItemClickListener
    lateinit var binding: ItemPetTrainingListBinding

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetTrainingListViewHolder {
        binding = ItemPetTrainingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetTrainingListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PetTrainingListViewHolder, position: Int) {
        val item = itemList[position]
        holder.setItem(item)
        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class PetTrainingListViewHolder(binding: ItemPetTrainingListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setItem(item: PetTrainingListData) {
            Glide.with(binding.imgPetTraining)
                .load(item.trainingImg)                         // 불러올 이미지 URL
                .fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                .error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                .placeholder(R.drawable.background_glide_init)  // 이미지 로딩 시작하기 전에 표시할 이미지
                .centerInside()                                 // scaletype
                .into(binding.imgPetTraining)             // 이미지를 넣을 뷰
        }
    }
}