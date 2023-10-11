package com.example.petmate.home.petseeker

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.VerticalItemDecorator
import com.example.petmate.databinding.ItemHomePetseekerListBinding


class HomePetseekerListAdapter(val itemList: ArrayList<HomePetseekerRecommendPetListInterfaceResponseResponseResult>, val isUser: String?) : RecyclerView.Adapter<HomePetseekerListAdapter.PetseekerListViewHolder>() {

    lateinit var binding: ItemHomePetseekerListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetseekerListViewHolder {
        binding = ItemHomePetseekerListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetseekerListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PetseekerListViewHolder, position: Int) {
        val petList = ArrayList<HomePetseekerRecommendPetListInterfaceResponseResponseResult>()
        for (i: Int in 0..2) {
            petList.add(itemList[3 * position + i])
        }
        //Log.d("dddd","${position} / ${petList[0].list_feature} / ${petList[1].list_feature} / ${petList[2].list_feature} ")
        holder.test(petList)
    }

    override fun getItemCount(): Int {
        return itemList.size/3
    }

    inner class PetseekerListViewHolder(binding: ItemHomePetseekerListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun test(petListDivision: ArrayList<HomePetseekerRecommendPetListInterfaceResponseResponseResult>){
            Log.d("dddd", "petseekrrecommendAdapterobj$isUser")

            val adapterHomePetseekerList2 = HomePetseekerListSubAdapter(petListDivision, isUser)

            binding.rcvPetseekerList.adapter = adapterHomePetseekerList2
            binding.rcvPetseekerList.layoutManager = LinearLayoutManager(binding.rcvPetseekerList.context,LinearLayoutManager.VERTICAL, false)
            binding.rcvPetseekerList.addItemDecoration(VerticalItemDecorator(25))
            Log.d("HomePetseekerFragment","리사이클러뷰")
        }
    }

}