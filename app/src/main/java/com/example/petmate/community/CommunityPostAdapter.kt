package com.example.petmate.community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.databinding.ItemCommunityPostBinding

class CommunityPostAdapter(var petList: ArrayList<CommunityPostData>) : RecyclerView.Adapter<CommunityPostAdapter.CommunityPostlistViewHolder>()  {

    lateinit var binding: ItemCommunityPostBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityPostlistViewHolder {
        binding = ItemCommunityPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CommunityPostlistViewHolder(binding)
    }

    override fun getItemCount(): Int = petList.size


    override fun onBindViewHolder(holder: CommunityPostlistViewHolder, position: Int) {
        val item = petList[position]
        holder.setItem(item)
    }

    inner class CommunityPostlistViewHolder(binding: ItemCommunityPostBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setItem(item: CommunityPostData) {
            binding.imgCommunityPost.setImageBitmap(item.postlist_img)
//            Glide.with(binding.petownerPetlistImage)
//                .load(item.petlist_img)                         // 불러올 이미지 URL
//                .fallback(R.drawable.cat1_temp)                 // 로드할 URL이 비어있을 경우 표시할 이미지
//                .error(R.drawable.cat2_temp)                    // 로딩 에러 발생 시 표시할 이미지
//                .placeholder(R.drawable.weather_little_cloudy)  // 이미지 로딩 시작하기 전에 표시할 이미지
//                .centerInside()                                 // scaletype
//                .into(binding.petownerPetlistImage)             // 이미지를 넣을 뷰
        }
    }
}