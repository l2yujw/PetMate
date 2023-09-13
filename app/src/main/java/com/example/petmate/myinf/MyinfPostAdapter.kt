package com.example.petmate.myinf

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.petmate.R
import com.example.petmate.community.CommunityBoardData
import com.example.petmate.databinding.ItemCommunityBoardBinding
import com.example.petmate.databinding.ItemMyinfPostBinding
import com.example.petmate.home.petseeker.HomePetseekerRecommendPetListInterfaceResponseResponseResult

class MyinfPostAdapter(val itemList: ArrayList<MyinfPostData>) : RecyclerView.Adapter<MyinfPostAdapter.MyinfPostViewHolder>()  {

    lateinit var binding: ItemMyinfPostBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyinfPostAdapter.MyinfPostViewHolder {
        binding = ItemMyinfPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyinfPostViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MyinfPostAdapter.MyinfPostViewHolder, position: Int) {
        var itemSubList = ArrayList<MyinfPostSubData>()
        var subImg = itemList[position].toString()
        itemSubList.add(MyinfPostSubData(subImg))
        itemSubList.add(MyinfPostSubData(subImg))

        holder.setItem(itemSubList)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class MyinfPostViewHolder(binding: ItemMyinfPostBinding) : RecyclerView.ViewHolder(binding.root){
        fun setItem(itemSubList: ArrayList<MyinfPostSubData>) {

            val adapterMyinfPostSub = MyinfPostSubAdapter(itemSubList)
            adapterMyinfPostSub.notifyDataSetChanged()

            val indicatorList = binding.circleindicatorMyinfPost

            indicatorList.setViewPager(binding.viewpagerMyinfPost)
            indicatorList.createIndicators(itemSubList.size, 0)

            binding.viewpagerMyinfPost.adapter = adapterMyinfPostSub
            binding.viewpagerMyinfPost.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            binding.viewpagerMyinfPost.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    indicatorList.animatePageSelected(position)
                    //Toast.makeText(requireContext(), "${position + 1} 페이지 선택됨", Toast.LENGTH_SHORT).show()
                }
            })

            binding.viewpagerMyinfPost
        }

    }
}