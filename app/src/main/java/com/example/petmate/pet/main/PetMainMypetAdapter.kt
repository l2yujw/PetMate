package com.example.petmate.pet.main

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petmate.R
import com.example.petmate.databinding.ItemPetMainMypetBinding
import kotlin.random.Random

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
            if(item.image.isBlank() || item.image == ""){
                val tempimagelist = ArrayList<String>()
                tempimagelist.add("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg")
                tempimagelist.add("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg")
                tempimagelist.add("https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg")
                Glide.with(binding.imgPetMainMypet)
                    .load(tempimagelist.get(Random.nextInt(0,3)))                         // 불러올 이미지 URL
                    .fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                    .error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                    .placeholder(R.drawable.background_glide_init)  // 이미지 로딩 시작하기 전에 표시할 이미지
                    .centerInside()                                 // scaletype
                    .into(binding.imgPetMainMypet)             // 이미지를 넣을 뷰
            }else if(item.image.endsWith(".png") ||item.image.endsWith(".jpg")||item.image.endsWith(".jpeg") ) {
                Glide.with(binding.imgPetMainMypet)
                    .load(item.image)                         // 불러올 이미지 URL
                    .fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                    .error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                    .placeholder(R.drawable.background_glide_init)  // 이미지 로딩 시작하기 전에 표시할 이미지
                    .centerInside()                                 // scaletype
                    .into(binding.imgPetMainMypet)             // 이미지를 넣을 뷰
            }else{
                val encodeByte = Base64.decode(item.image, Base64.NO_WRAP)
                val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
                binding.imgPetMainMypet.setImageBitmap(bitmap)
            }
        }
    }
}