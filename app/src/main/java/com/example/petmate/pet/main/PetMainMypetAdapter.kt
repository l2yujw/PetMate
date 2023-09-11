package com.example.petmate.pet.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petmate.R
import com.example.petmate.databinding.ItemHomePetseekerRecommendBinding
import com.example.petmate.databinding.ItemPetMainMypetBinding
import com.example.petmate.home.petseeker.HomePetseekerRecommendAdapter
import com.example.petmate.home.petseeker.HomePetseekerRecommendData

class PetMainMypetAdapter(val itemList: ArrayList<PetMainMypetData>) : RecyclerView.Adapter<PetMainMypetAdapter.MypetViewHolder>() {

    lateinit var binding: ItemPetMainMypetBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MypetViewHolder {
        binding = ItemPetMainMypetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MypetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MypetViewHolder, position: Int) {
        val item = itemList[position]
        holder.setItem(item)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class MypetViewHolder(binding: ItemPetMainMypetBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setItem(item: PetMainMypetData) {
            binding.imgPetMainMypet.setImageBitmap(item.mypet_img)
            Log.d("PetMainFragment123", "setItem: ${item.mypet_img}")
           /* Glide.with(binding.imgPetMainMypet)
                .load(item.mypet_img)                       // 불러올 이미지 URL
                .fallback(R.drawable.cat1_temp)             // 로드할 URL이 비어있을 경우 표시할 이미지
                .error(R.drawable.cat2_temp)                // 로딩 에러 발생 시 표시할 이미지
                .placeholder(R.drawable.search)             // 이미지 로딩 시작하기 전에 표시할 이미지
                .centerInside()                             // scaletype
                .into(binding.imgPetMainMypet)  // 이미지를 넣을 뷰*/
        }
    }
}