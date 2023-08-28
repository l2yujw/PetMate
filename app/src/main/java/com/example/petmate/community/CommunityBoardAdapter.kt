package com.example.petmate.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petmate.OnItemClickListener
import com.example.petmate.R
import com.example.petmate.databinding.ItemCommunityBoardBinding
import com.example.petmate.databinding.ItemPetTrainingListBinding
import com.example.petmate.pet.training.PetTrainingListData

class CommunityBoardAdapter(val itemList: ArrayList<CommunityBoardData>) : RecyclerView.Adapter<CommunityBoardAdapter.CommunityBoardViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener
    lateinit var binding: ItemCommunityBoardBinding

    fun setItemClickListener(onItemClickListener: OnItemClickListener){
        this.itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityBoardViewHolder {
        binding = ItemCommunityBoardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommunityBoardViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: CommunityBoardViewHolder, position: Int) {
        val item = itemList[position]
        holder.setItem(item)

        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position)
        }
    }

    inner class CommunityBoardViewHolder(binding: ItemCommunityBoardBinding) : RecyclerView.ViewHolder(binding.root){
        fun setItem(item: CommunityBoardData) {
            Glide.with(binding.imgCommunityBoard1)
                .load(item.boardImg)                         // 불러올 이미지 URL
                .fallback(R.drawable.cat1_temp)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                .error(R.drawable.cat2_temp)                    // 로딩 에러 발생 시 표시할 이미지
                .placeholder(R.drawable.cat1_temp)  // 이미지 로딩 시작하기 전에 표시할 이미지
                .centerInside()                                 // scaletype
                .into(binding.imgCommunityBoard1)             // 이미지를 넣을 뷰

            Glide.with(binding.imgCommunityBoard2)
                .load(item.boardImg)                         // 불러올 이미지 URL
                .fallback(R.drawable.cat1_temp)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                .error(R.drawable.cat2_temp)                    // 로딩 에러 발생 시 표시할 이미지
                .placeholder(R.drawable.cat1_temp)  // 이미지 로딩 시작하기 전에 표시할 이미지
                .centerInside()                                 // scaletype
                .into(binding.imgCommunityBoard2)             // 이미지를 넣을 뷰

            Glide.with(binding.imgCommunityBoard3)
                .load(item.boardImg)                         // 불러올 이미지 URL
                .fallback(R.drawable.cat1_temp)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                .error(R.drawable.cat2_temp)                    // 로딩 에러 발생 시 표시할 이미지
                .placeholder(R.drawable.cat1_temp)  // 이미지 로딩 시작하기 전에 표시할 이미지
                .centerInside()                                 // scaletype
                .into(binding.imgCommunityBoard3)             // 이미지를 넣을 뷰
        }

    }
}