package com.example.petmate.ui.community.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.core.util.OnItemClickListener
import com.example.petmate.databinding.ItemCommunityPopularBinding
import com.example.petmate.ui.community.data.CommunityPopularData
import com.example.petmate.util.GlideHelper

class
CommunityPopularAdapter(private val itemList: List<CommunityPopularData>) : RecyclerView.Adapter<CommunityPopularAdapter.CommunityPopularViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener

    fun setItemClickListener(onItemClickListener: OnItemClickListener){
        this.itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityPopularViewHolder {
        val binding = ItemCommunityPopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommunityPopularViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: CommunityPopularViewHolder, position: Int) {
        val item = itemList[position]
        holder.setItem(item)

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    inner class CommunityPopularViewHolder(private val binding: ItemCommunityPopularBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setItem(item: CommunityPopularData) {
            GlideHelper.loadImage(binding.ivCommunityPopular, item.popularImg)
        }
    }
}
