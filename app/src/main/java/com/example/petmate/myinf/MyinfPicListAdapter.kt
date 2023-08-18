package com.example.petmate.myinf

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.OnItemClickListener
import com.example.petmate.R

class MyinfPicListAdapter(val itemList: ArrayList<Int>) : RecyclerView.Adapter<MyinfPicListAdapter.MyinfPicListViewHolder>(){
    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyinfPicListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_myinf_piclist, parent, false)
        return MyinfPicListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MyinfPicListViewHolder, position: Int) {
        holder.picImage.setImageResource(itemList[position])
        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position)
        }
    }

    inner class MyinfPicListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val picImage = itemView.findViewById<ImageView>(R.id.img_myinf_pic)
    }
}