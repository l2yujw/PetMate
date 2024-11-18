package com.example.petmate.ui.home.petseeker.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petmate.core.util.OnItemClickListener
import com.example.petmate.R
import com.example.petmate.databinding.ItemHomePetseekerRecommendBinding
import com.example.petmate.remote.response.home.petseeker.PetSeekerDetailInfoResult

class PetSeekerRecommendAdapter(
    private val itemList: List<PetSeekerDetailInfoResult>,
    private val isUser: String?
) : RecyclerView.Adapter<PetSeekerRecommendAdapter.PetSeekerRecommendViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetSeekerRecommendViewHolder {
        val binding = ItemHomePetseekerRecommendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetSeekerRecommendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PetSeekerRecommendViewHolder, position: Int) {
        val item = itemList[position]
        holder.setItem(item)

        // null 체크 후 클릭 리스너 처리
        holder.itemView.setOnClickListener {
            if (::itemClickListener.isInitialized) {
                itemClickListener.onClick(it, position)
            }
        }
    }

    override fun getItemCount(): Int = itemList.size

    // ViewHolder 클래스
    inner class PetSeekerRecommendViewHolder(private val binding: ItemHomePetseekerRecommendBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setItem(item: PetSeekerDetailInfoResult) {
            // 이미지 로드
            Glide.with(binding.itemHomePetseekerRecommendImage)
                .load(item.imageUrl)
                .fallback(R.drawable.background_glide_init)
                .error(R.drawable.background_glide_init)
                .placeholder(R.drawable.background_glide_init)
                .centerInside()
                .into(binding.itemHomePetseekerRecommendImage)

            // 텍스트 및 이미지 설정
            binding.itemHomePetseekerRecommendBreed.text = item.species.split("]").getOrElse(1) { item.species }
            binding.itemHomePetseekerRecommendSex.setImageResource(getSexImage(item.gender))
            binding.itemHomePetseekerRecommendColor.text = item.colorCd
            binding.itemHomePetseekerRecommendFeature.text = item.characteristic

            // 레이아웃 클릭 시 Navigation 처리
            binding.itemHomePetseekerRecommendLayout.setOnClickListener {
                val bundle = Bundle().apply {
                    putParcelable("ShelterpetInfo", item)
                    if (isUser == "isUser") {
                        putString("isUser", "isUser")
                    }
                }
                it.findNavController().navigate(R.id.action_homePetseekerFragment_to_homeShelterpetInfoFragment, bundle)
            }
        }
    }

    // 성별에 따라 이미지 리소스를 반환하는 함수
    private fun getSexImage(sex: String): Int {
        return when (sex) {
            "수컷", "M" -> R.drawable.sex_male
            "암컷", "F" -> R.drawable.sex_female
            else -> R.drawable.sex_male
        }
    }
}