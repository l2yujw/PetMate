package com.example.petmate.home.petseeker

import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petmate.OnItemClickListener
import com.example.petmate.R
import com.example.petmate.databinding.ItemHomePetseekerListBinding


class HomePetseekerListAdapter(val itemList: ArrayList<HomePetseekerListData>) : RecyclerView.Adapter<HomePetseekerListAdapter.PetseekerlistViewHolder>() {

    lateinit var binding: ItemHomePetseekerListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetseekerlistViewHolder {
        binding = ItemHomePetseekerListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetseekerlistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PetseekerlistViewHolder, position: Int) {
        val petList = ArrayList<HomePetseekerListData>()
        for (i: Int in 0..2) {
            if (itemList[position + i] != null) {
                petList.add(itemList[3 * position + i])
            }
        }
        Log.d("dddd","${position} / ${petList[0].list_feature} / ${petList[1].list_feature} / ${petList[2].list_feature} ")
        holder.test(petList)
    }

    override fun getItemCount(): Int {
        return itemList.size/3
    }

    inner class PetseekerlistViewHolder(binding: ItemHomePetseekerListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun test(petListDivision: ArrayList<HomePetseekerListData>){
            val adapterHomePetseekerList2 = HomePetseekerList2Adapter(petListDivision)
            adapterHomePetseekerList2.setItemClickListener(object : OnItemClickListener {
                override fun onClick(v: View, position: Int) {
                    v.findNavController().navigate(R.id.action_homePetseekerFragment_to_homeShelterpetInfoFragment)
                }
            })
            binding.rcvPetseekerList.adapter = adapterHomePetseekerList2
            binding.rcvPetseekerList.layoutManager = LinearLayoutManager(binding.rcvPetseekerList.context,LinearLayoutManager.VERTICAL, false)
            Log.d("dddd","리사이클러뷰")
        }
    }

}
//override fun getItemCount(): Int = petTextList.size