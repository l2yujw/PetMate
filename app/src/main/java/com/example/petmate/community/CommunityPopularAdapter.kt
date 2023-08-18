package com.example.petmate.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.OnItemClickListener
import com.example.petmate.R

class CommunityPopularAdapter(val itemList: ArrayList<Int>) : RecyclerView.Adapter<CommunityPopularAdapter.CommunityPopularViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener

    fun setItemClickListener(onItemClickListener: OnItemClickListener){
        this.itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityPopularViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_community_popular, parent, false)
        return CommunityPopularViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: CommunityPopularViewHolder, position: Int) {
        holder.userImage.setImageResource(itemList[position])
        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position)
        }
    }

    inner class CommunityPopularViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val userImage = itemView.findViewById<ImageView>(R.id.img_community_popular)
    }
}