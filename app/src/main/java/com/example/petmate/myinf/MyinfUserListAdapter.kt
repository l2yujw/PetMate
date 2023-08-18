package com.example.petmate.myinf

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.OnItemClickListener
import com.example.petmate.R

class MyinfUserListAdapter (val itemList: ArrayList<Int>) : RecyclerView.Adapter<MyinfUserListAdapter.MyinfUserListViewHolder>(){

    private lateinit var itemClickListener : OnItemClickListener

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyinfUserListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_myinf_userlist, parent, false)
        return MyinfUserListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MyinfUserListViewHolder, position: Int) {
        holder.userImage.setImageResource(itemList[position])
        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position)
        }
    }

    inner class MyinfUserListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val userImage = itemView.findViewById<ImageView>(R.id.img_myinf_user)
    }
}