package com.example.petmate.ui.myinfo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.databinding.ItemMyinfUserlistBinding
import com.example.petmate.ui.myinfo.data.MyInfoUsersData
import com.example.petmate.util.GlideHelper

class MyInfoUserListAdapter(val itemList: ArrayList<MyInfoUsersData>) : RecyclerView.Adapter<MyInfoUserListAdapter.MyInfoUserListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyInfoUserListViewHolder {
        val binding = ItemMyinfUserlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyInfoUserListViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: MyInfoUserListViewHolder, position: Int) {
        holder.setItem(itemList[position])
    }

    inner class MyInfoUserListViewHolder(private val binding: ItemMyinfUserlistBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setItem(item: MyInfoUsersData) {
            // 이미지 로드 로직을 GlideHelper로 통일
            GlideHelper.loadImage(binding.imgMyinfUser, item.userImg)
        }
    }
}
