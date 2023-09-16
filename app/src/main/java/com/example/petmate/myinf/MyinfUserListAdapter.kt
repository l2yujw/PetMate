package com.example.petmate.myinf

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petmate.OnItemClickListener
import com.example.petmate.R
import com.example.petmate.databinding.ItemMyinfUserlistBinding
import kotlin.random.Random

class MyinfUserListAdapter (val itemList: ArrayList<MyinfUserListData>) : RecyclerView.Adapter<MyinfUserListAdapter.MyinfUserListViewHolder>(){

    private lateinit var itemClickListener : OnItemClickListener
    lateinit var binding: ItemMyinfUserlistBinding

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        //this.itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyinfUserListViewHolder {
        binding = ItemMyinfUserlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyinfUserListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MyinfUserListViewHolder, position: Int) {
        val item = itemList[position]
        holder.setItem(item)
        holder.itemView.setOnClickListener{
            //itemClickListener.onClick(it, position)
        }
    }

    inner class MyinfUserListViewHolder(binding: ItemMyinfUserlistBinding) : RecyclerView.ViewHolder(binding.root){
        fun setItem(item: MyinfUserListData) {
            if(item.userImg.isBlank() || item.userImg == ""){
                val tempimagelist = ArrayList<String>()
                tempimagelist.add("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg")
                tempimagelist.add("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg")
                tempimagelist.add("https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg")
                Glide.with(binding.imgMyinfUser)
                    .load(tempimagelist.get(Random.nextInt(0,3)))                         // 불러올 이미지 URL
                    .fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                    .error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                    .placeholder(R.drawable.background_glide_init)  // 이미지 로딩 시작하기 전에 표시할 이미지
                    .centerInside()                                 // scaletype
                    .into(binding.imgMyinfUser)             // 이미지를 넣을 뷰
            }else if(item.userImg.endsWith(".png") ||item.userImg.endsWith(".jpg")||item.userImg.endsWith(".jpeg") ) {
                Glide.with(binding.imgMyinfUser)
                    .load(item.userImg)                         // 불러올 이미지 URL
                    .fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                    .error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                    .placeholder(R.drawable.background_glide_init)  // 이미지 로딩 시작하기 전에 표시할 이미지
                    .centerInside()                                 // scaletype
                    .into(binding.imgMyinfUser)             // 이미지를 넣을 뷰
            }else{
                val encodeByte = Base64.decode(item.userImg, Base64.NO_WRAP)
                val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
                binding.imgMyinfUser.setImageBitmap(bitmap)
            }
        }
    }
}