package com.example.petmate.ui.community.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.databinding.ItemCommunityBoardBinding
import com.example.petmate.remote.response.community.CommunityResult
import com.example.petmate.util.GlideHelper
import com.example.petmate.core.util.OnItemClickListener

class
CommunityBoardAdapter(private val itemList: List<CommunityResult>) : RecyclerView.Adapter<CommunityBoardAdapter.CommunityBoardViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener

    fun setItemClickListener(onItemClickListener: OnItemClickListener){
        this.itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityBoardViewHolder {
        val binding = ItemCommunityBoardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommunityBoardViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: CommunityBoardViewHolder, position: Int) {
        val item = itemList[position]
        holder.setItem(item)

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    inner class CommunityBoardViewHolder(private val binding: ItemCommunityBoardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setItem(item: CommunityResult) {
            GlideHelper.loadImage(binding.imgCommunityBoard, item.image)
            GlideHelper.loadImage(binding.ivCommunityBoard, item.userImage)

            binding.tvNicknameCommunityBoard.text = if (item.nickName.isBlank()) "익명" else item.nickName
            binding.tvTitleCommunityBoard.text = item.title
        }
    }
}

