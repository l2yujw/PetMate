package com.example.petmate.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.OnItemClickListener
import com.example.petmate.R

class CommunityBoardAdapter(val itemList: ArrayList<Int>) : RecyclerView.Adapter<CommunityBoardAdapter.CommunityBoardViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener

    fun setItemClickListener(onItemClickListener: OnItemClickListener){
        this.itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityBoardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_community_board, parent, false)
        return CommunityBoardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: CommunityBoardViewHolder, position: Int) {
        holder.boardImage1.setImageResource(itemList[position])
        holder.boardImage2.setBackgroundResource(itemList[position])
        holder.boardImage3.setBackgroundResource(itemList[position])

        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position)
        }
    }

    inner class CommunityBoardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val boardImage1 = itemView.findViewById<ImageView>(R.id.img_community_board1)
        val boardImage2 = itemView.findViewById<ImageView>(R.id.img_community_board2)
        val boardImage3 = itemView.findViewById<ImageView>(R.id.img_community_board3)

    }
}