package com.example.petmate.ui.myinfo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.core.util.OnItemClickListener
import com.example.petmate.databinding.ItemMyinfPiclistBinding
import com.example.petmate.remote.response.myinfo.MyInfoImageResult
import com.example.petmate.util.GlideHelper

class MyInfoImageAdapter(val itemList: List<MyInfoImageResult>) : RecyclerView.Adapter<MyInfoImageAdapter.MyinfPicListViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener
    lateinit var binding: ItemMyinfPiclistBinding

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyinfPicListViewHolder {
        binding = ItemMyinfPiclistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyinfPicListViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: MyinfPicListViewHolder, position: Int) {
        val item = itemList[position]
        holder.setItem(item)
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    inner class MyinfPicListViewHolder(binding: ItemMyinfPiclistBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setItem(item: MyInfoImageResult) {
            GlideHelper.loadImage(binding.imgMyinfPic, item.image)
        }
    }
}