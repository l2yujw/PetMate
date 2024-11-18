package com.example.petmate.ui.myinfo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.databinding.ItemMyinfPostBinding
import com.example.petmate.remote.response.myinfo.MyInfoImageResult
import com.example.petmate.util.GlideHelper

class MyInfoPostAdapter(val itemList: ArrayList<MyInfoImageResult>) : RecyclerView.Adapter<MyInfoPostAdapter.MyinfPostViewHolder>()  {

    lateinit var binding: ItemMyinfPostBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyinfPostViewHolder {
        binding = ItemMyinfPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyinfPostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyinfPostViewHolder, position: Int) {
        holder.setItem(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    inner class MyinfPostViewHolder(binding: ItemMyinfPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setItem(item: MyInfoImageResult) {
            // 이미지 로드
            GlideHelper.loadImage(binding.imageMyinfPost, item.image)
            GlideHelper.loadImage(binding.userImageMyinfPost, item.userImage)

            // 텍스트 설정
            binding.nickNameMyinfPost.text = item.nickName
            binding.detailMyinfPost.text = item.detail
            binding.titleMyinfPost.text = item.title
        }
    }
}
