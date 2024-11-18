package com.example.petmate.ui.pet.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.R
import com.example.petmate.core.util.OnItemClickListener
import com.example.petmate.ui.pet.main.data.PetMainHealthData

class PetMainHealthAdapter(private val itemList: List<PetMainHealthData>) :
    RecyclerView.Adapter<PetMainHealthAdapter.PetMainHealthViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetMainHealthViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pet_main_health, parent, false)
        return PetMainHealthViewHolder(view)
    }

    override fun onBindViewHolder(holder: PetMainHealthViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { itemClickListener.onClick(it, position) }
    }

    override fun getItemCount(): Int = itemList.size

    inner class PetMainHealthViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val health: TextView = itemView.findViewById(R.id.main_health_text)

        fun bind(item: PetMainHealthData) {
            health.text = item.health
        }
    }
}