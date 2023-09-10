package com.example.petmate.home.petseeker

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.OnItemClickListener
import com.example.petmate.R
import com.example.petmate.VerticalItemDecorator
import com.example.petmate.databinding.ItemHomePetseekerListBinding


class HomePetseekerListAdapter(val itemList: ArrayList<HomePetseekerListData>) : RecyclerView.Adapter<HomePetseekerListAdapter.PetseekerListViewHolder>() {

    lateinit var binding: ItemHomePetseekerListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetseekerListViewHolder {
        binding = ItemHomePetseekerListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetseekerListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PetseekerListViewHolder, position: Int) {
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

    inner class PetseekerListViewHolder(binding: ItemHomePetseekerListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun test(petListDivision: ArrayList<HomePetseekerListData>){
            val adapterHomePetseekerList2 = HomePetseekerListSubAdapter(petListDivision)
            adapterHomePetseekerList2.setItemClickListener(object : OnItemClickListener {
                override fun onClick(v: View, position: Int) {
                    v.findNavController().navigate(R.id.action_homePetseekerFragment_to_homeShelterpetInfoFragment)
                }
            })

            binding.rcvPetseekerList.adapter = adapterHomePetseekerList2
            binding.rcvPetseekerList.layoutManager = LinearLayoutManager(binding.rcvPetseekerList.context,LinearLayoutManager.VERTICAL, false)
            binding.rcvPetseekerList.addItemDecoration(VerticalItemDecorator(25))
            Log.d("dddd","리사이클러뷰")
        }
    }

}
//override fun getItemCount(): Int = petTextList.size