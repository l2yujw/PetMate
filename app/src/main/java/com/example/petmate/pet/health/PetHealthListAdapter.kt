package com.example.petmate.pet.health

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petmate.R
import com.example.petmate.databinding.ItemHomePetownerPetlistBinding
import com.example.petmate.databinding.ItemPetHealthPetlistBinding
import com.example.petmate.home.petowner.HomePetownerPetlistAdapter
import com.example.petmate.home.petowner.HomePetownerPetlistData

class PetHealthListAdapter(var petList: ArrayList<PetHealthListData>) : RecyclerView.Adapter<PetHealthListAdapter.PetHealthlistViewHolder>() {

    lateinit var binding: ItemPetHealthPetlistBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetHealthlistViewHolder {
        binding = ItemPetHealthPetlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetHealthlistViewHolder(binding)
    }

    override fun getItemCount(): Int = petList.size

    override fun onBindViewHolder(holder: PetHealthlistViewHolder, position: Int) {
        val item = petList[position]
        holder.setItem(item)

    }

    inner class PetHealthlistViewHolder(binding: ItemPetHealthPetlistBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setItem(item: PetHealthListData) {

            binding.petHealthPetlistImage.setImageBitmap(item.petlist_img)

            //Glide.with(binding.petHealthPetlistImage)
                //.load(item.petlist_img)                         // 불러올 이미지 URL
                //.fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                //.error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                //.placeholder(R.drawable.background_glide_init)  // 이미지 로딩 시작하기 전에 표시할 이미지
                .//centerInside()                                 // scaletype
                //.into(binding.petHealthPetlistImage)            // 이미지를 넣을 뷰
        }
    }
}