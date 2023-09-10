package com.example.petmate.home.petseeker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petmate.R
import com.example.petmate.databinding.ItemHomePetseekerRecommendBinding

class HomePetseekerRecommendAdapter(val itemList: ArrayList<HomePetseekerRecommendData>) : RecyclerView.Adapter<HomePetseekerRecommendAdapter.PetseekerReccomendViewHolder>() {

    lateinit var binding: ItemHomePetseekerRecommendBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetseekerReccomendViewHolder {
        binding = ItemHomePetseekerRecommendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetseekerReccomendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PetseekerReccomendViewHolder, position: Int) {
        val item = itemList[position]
        holder.setItem(item)
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }


    inner class PetseekerReccomendViewHolder(binding: ItemHomePetseekerRecommendBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setItem(item: HomePetseekerRecommendData) {
            Glide.with(binding.itemHomePetseekerRecommendImage)
                .load(item.recommend_img)                       // 불러올 이미지 URL
                .fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                .error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                .placeholder(R.drawable.background_glide_init)             // 이미지 로딩 시작하기 전에 표시할 이미지
                .centerInside()                             // scaletype
                .into(binding.itemHomePetseekerRecommendImage)  // 이미지를 넣을 뷰

            binding.itemHomePetseekerRecommendBreed.text = item.recommend_breed
            binding.itemHomePetseekerRecommendSex.setImageResource(getSexImage(item.recommend_sex))
            binding.itemHomePetseekerRecommendColor.text = item.recommend_color
            binding.itemHomePetseekerRecommendFeature.text = item.recommend_feature
        }
    }

    // 성별
    fun getSexImage(sex: String): Int {
        return when (sex) {
            "수컷" -> R.drawable.sex_male
            "암컷" -> R.drawable.sex_female
            else -> R.drawable.sex_male
        }
    }
}