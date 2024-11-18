package com.example.petmate.ui.home.petseeker.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petmate.core.util.OnItemClickListener
import com.example.petmate.R
import com.example.petmate.databinding.ItemHomePetseekerSublistBinding
import com.example.petmate.remote.response.home.petseeker.PetSeekerDetailInfoResult

class PetSeekerListSubAdapter(
    private val itemList: List<PetSeekerDetailInfoResult>,
    private val isUser: String?
) : RecyclerView.Adapter<PetSeekerListSubAdapter.PetSeekerSubListViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetSeekerSubListViewHolder {
        val binding = ItemHomePetseekerSublistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetSeekerSubListViewHolder(binding)
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: PetSeekerSubListViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    inner class PetSeekerSubListViewHolder(private val binding: ItemHomePetseekerSublistBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PetSeekerDetailInfoResult) {
            binding.itemHomePetseekerListLayout.setOnClickListener {
                val bundle = Bundle().apply {
                    putParcelable("ShelterpetInfo", item)
                    if (isUser == "isUser") putString("isUser", "isUser")
                }
                it.findNavController().navigate(R.id.action_homePetseekerFragment_to_homeShelterpetInfoFragment, bundle)
            }

            // Glide를 이용한 이미지 로드
            Glide.with(binding.itemHomePetseekerListImage)
                .load(item.imageUrl)
                .fallback(R.drawable.background_glide_init)
                .error(R.drawable.background_glide_init)
                .placeholder(R.drawable.background_glide_init)
                .centerInside()
                .into(binding.itemHomePetseekerListImage)

            binding.itemHomePetseekerListBreed.text = item.species
            binding.itemHomePetseekerListSex.setImageResource(getSexImage(item.gender))
            binding.itemHomePetseekerListColor.text = item.colorCd
            binding.itemHomePetseekerListFeature.text = item.characteristic
        }

        private fun getSexImage(sex: String): Int {
            return when (sex) {
                "수컷", "M" -> R.drawable.sex_male
                "암컷", "F" -> R.drawable.sex_female
                else -> R.drawable.sex_male
            }
        }
    }
}
