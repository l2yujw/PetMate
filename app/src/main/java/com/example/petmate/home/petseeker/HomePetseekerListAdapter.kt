package com.example.petmate.home.petseeker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petmate.R
import com.example.petmate.databinding.ItemHomePetseekerListBinding


class HomePetseekerListAdapter(val itemList: ArrayList<HomePetseekerListData>) : RecyclerView.Adapter<HomePetseekerListAdapter.PetseekerlistViewHolder>() {

    lateinit var binding: ItemHomePetseekerListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetseekerlistViewHolder {
        binding = ItemHomePetseekerListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetseekerlistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PetseekerlistViewHolder, position: Int) {
        val item = itemList[position]
        holder.setItem(item)
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }


    inner class PetseekerlistViewHolder(binding: ItemHomePetseekerListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setItem(item: HomePetseekerListData) {
            Glide.with(binding.itemHomePetseekerListImage1)
                .load(item.list_img1)                       // 불러올 이미지 URL
                .fallback(R.drawable.cat1_temp)             // 로드할 URL이 비어있을 경우 표시할 이미지
                .error(R.drawable.cat2_temp)                // 로딩 에러 발생 시 표시할 이미지
                .placeholder(R.drawable.search)             // 이미지 로딩 시작하기 전에 표시할 이미지
                .centerInside()                             // scaletype
                .into(binding.itemHomePetseekerListImage1)  // 이미지를 넣을 뷰
            Glide.with(binding.itemHomePetseekerListImage2)
                .load(item.list_img2)
                .fallback(R.drawable.cat1_temp)
                .error(R.drawable.cat2_temp)
                .placeholder(R.drawable.search)
                .centerInside()
                .into(binding.itemHomePetseekerListImage2)
            Glide.with(binding.itemHomePetseekerListImage3)
                .load(item.list_img3)
                .fallback(R.drawable.cat1_temp)
                .error(R.drawable.cat2_temp)
                .placeholder(R.drawable.search)
                .centerInside()
                .into(binding.itemHomePetseekerListImage3)

            binding.itemHomePetseekerListBreed1.text = item.list_breed1
            binding.itemHomePetseekerListBreed2.text = item.list_breed2
            binding.itemHomePetseekerListBreed3.text = item.list_breed3

            binding.itemHomePetseekerListSex1.setImageResource(getSexImage(item.list_sex1))
            binding.itemHomePetseekerListSex2.setImageResource(getSexImage(item.list_sex2))
            binding.itemHomePetseekerListSex3.setImageResource(getSexImage(item.list_sex3))

            binding.itemHomePetseekerListColor1.text = item.list_color1
            binding.itemHomePetseekerListColor2.text = item.list_color2
            binding.itemHomePetseekerListColor3.text = item.list_color3

            binding.itemHomePetseekerListFeature1.text = item.list_feature1
            binding.itemHomePetseekerListFeature2.text = item.list_feature2
            binding.itemHomePetseekerListFeature3.text = item.list_feature3
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
//override fun getItemCount(): Int = petTextList.size