package com.example.petmate.ui.community.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.databinding.ItemCommunityPostBinding
import com.example.petmate.remote.response.community.CommunityResult
import com.example.petmate.util.GlideHelper

class CommunityPostAdapter(var communityPostList: List<CommunityResult>) : RecyclerView.Adapter<CommunityPostAdapter.CommunityPostViewHolder>() {

    lateinit var binding: ItemCommunityPostBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityPostViewHolder {
        binding = ItemCommunityPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommunityPostViewHolder(binding)
    }

    override fun getItemCount(): Int = communityPostList.size

    override fun onBindViewHolder(holder: CommunityPostViewHolder, position: Int) {
        val item = communityPostList[position]
        holder.setItem(item)
    }

    inner class CommunityPostViewHolder(binding: ItemCommunityPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setItem(item: CommunityResult) {
            GlideHelper.loadImage(binding.imgCommunityPost, item.image)
        }
    }
}
