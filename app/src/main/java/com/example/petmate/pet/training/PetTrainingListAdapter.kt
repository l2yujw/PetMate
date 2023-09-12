package com.example.petmate.pet.training

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.OnItemClickListener
import com.example.petmate.databinding.ItemPetTrainingListBinding

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
            binding.imgPetTraining.setImageBitmap(item.trainingImg)
            Log.d("PetTrainingFragment123", "Make Image ")
            if(!item.isStar) {
                binding.imgPetTrainingStar.visibility = View.INVISIBLE
            }
            /*Glide.with(binding.imgPetTraining)
                .load(item.trainingImg)                         // 불러올 이미지 URL
                .fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                .error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                .placeholder(R.drawable.background_glide_init)  // 이미지 로딩 시작하기 전에 표시할 이미지
                .centerInside()                                 // scaletype
                .into(binding.imgPetTraining)             // 이미지를 넣을 뷰*/
        }
    }
}