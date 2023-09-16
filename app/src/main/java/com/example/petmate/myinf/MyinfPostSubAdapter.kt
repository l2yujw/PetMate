package com.example.petmate.myinf

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petmate.R
import com.example.petmate.databinding.ItemMyinfPostSubBinding
import com.example.petmate.home.petseeker.HomePetseekerRecommendPetListInterfaceResponseResponseResult
import kotlin.random.Random

class MyinfPostSubAdapter(val itemList: ArrayList<MyInfPicInterfaceResponseResult>) : RecyclerView.Adapter<MyinfPostSubAdapter.MyinfPostSubViewHolder>() {

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

        fun setItem(item: MyInfPicInterfaceResponseResult) {
            /*val tempimagelist = ArrayList<String>()
            tempimagelist.add("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg")
            tempimagelist.add("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg")
            tempimagelist.add("https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg")
            if(item.image.isBlank() || item.image == ""){

                Glide.with(binding.imgMyinfPost)
                    .load(tempimagelist.get(Random.nextInt(0,3)))                         // 불러올 이미지 URL
                    .fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                    .error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                    .placeholder(R.drawable.background_glide_init)  // 이미지 로딩 시작하기 전에 표시할 이미지
                    .centerInside()                                 // scaletype
                    .into(binding.imgMyinfPost)             // 이미지를 넣을 뷰
                val encodeByte = Base64.decode(item.userImage, Base64.NO_WRAP)
                val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)

            }else{
                val encodeByte = Base64.decode(item.userImage, Base64.NO_WRAP)
                val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
                binding.imgMyinfPost.setImageBitmap(bitmap)
                Glide.with(binding.imgMyinfPost)
                    .load(item.image)                         // 불러올 이미지 URL
                    .fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                    .error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                    .placeholder(R.drawable.background_glide_init)  // 이미지 로딩 시작하기 전에 표시할 이미지
                    .centerInside()                                 // scaletype
                    .into(binding.imgMyinfPost)             // 이미지를 넣을 뷰

            }*/
        }
    }
}