package com.example.petmate.myinf

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petmate.OnItemClickListener
import com.example.petmate.R
import com.example.petmate.databinding.ItemMyinfPiclistBinding
import com.example.petmate.databinding.ItemPetTrainingListBinding
import com.example.petmate.pet.training.PetTrainingListData

class MyinfPicListAdapter(val itemList: ArrayList<MyinfPicListData>) : RecyclerView.Adapter<MyinfPicListAdapter.MyinfPicListViewHolder>(){
    private lateinit var itemClickListener : OnItemClickListener
    lateinit var binding: ItemMyinfPiclistBinding
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyinfPicListViewHolder {
        binding = ItemMyinfPiclistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyinfPicListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MyinfPicListViewHolder, position: Int) {
        val item = itemList[position]
        holder.setItem(item)
        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position)
        }
    }

    inner class MyinfPicListViewHolder(binding: ItemMyinfPiclistBinding) : RecyclerView.ViewHolder(binding.root){
        fun setItem(item: MyinfPicListData) {
            Glide.with(binding.imgMyinfPic)
                .load(item.picImg)                         // 불러올 이미지 URL
                .fallback(R.drawable.cat1_temp)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                .error(R.drawable.cat2_temp)                    // 로딩 에러 발생 시 표시할 이미지
                .placeholder(R.drawable.cat1_temp)  // 이미지 로딩 시작하기 전에 표시할 이미지
                .centerInside()                                 // scaletype
                .into(binding.imgMyinfPic)             // 이미지를 넣을 뷰
        }    }
}