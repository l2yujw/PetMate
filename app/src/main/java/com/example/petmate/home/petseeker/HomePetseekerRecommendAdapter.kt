package com.example.petmate.home.petseeker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petmate.OnItemClickListener
import com.example.petmate.R
import com.example.petmate.databinding.ItemHomePetseekerRecommendBinding

class HomePetseekerRecommendAdapter(val itemList: ArrayList<HomePetseekerRecommendPetListInterfaceResponseResponseResult>) : RecyclerView.Adapter<HomePetseekerRecommendAdapter.PetseekerReccomendViewHolder>() {

    lateinit var binding: ItemHomePetseekerRecommendBinding
    private lateinit var itemClickListener : OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetseekerReccomendViewHolder {
        binding = ItemHomePetseekerRecommendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetseekerReccomendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PetseekerReccomendViewHolder, position: Int) {
        val item = itemList[position]
        holder.setItem(item)
        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }


    inner class PetseekerReccomendViewHolder(binding: ItemHomePetseekerRecommendBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setItem(item: HomePetseekerRecommendPetListInterfaceResponseResponseResult) {
            Glide.with(binding.itemHomePetseekerRecommendImage)
                .load(item.imageUrl)                       // 불러올 이미지 URL
                .fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                .error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                .placeholder(R.drawable.background_glide_init)             // 이미지 로딩 시작하기 전에 표시할 이미지
                .centerInside()                             // scaletype
                .into(binding.itemHomePetseekerRecommendImage)  // 이미지를 넣을 뷰

            binding.itemHomePetseekerRecommendBreed.text = item.species.split("]")[1]
            binding.itemHomePetseekerRecommendSex.setImageResource(getSexImage(item.gender))
            binding.itemHomePetseekerRecommendColor.text = item.colorCd
            binding.itemHomePetseekerRecommendFeature.text = item.characteristic
            binding.itemHomePetseekerRecommendLayout.setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelable("ShelterpetInfo",item)
                it.findNavController().navigate(R.id.action_homePetseekerFragment_to_homeShelterpetInfoFragment,bundle)
            }
        }
    }

    // 성별
    fun getSexImage(sex: String): Int {
        return when (sex) {
            "수컷","M" -> R.drawable.sex_male
            "암컷","F" -> R.drawable.sex_female
            else -> R.drawable.sex_male
        }
    }
}