package com.example.petmate.myinf

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petmate.OnItemClickListener
import com.example.petmate.R
import com.example.petmate.databinding.ItemMyinfPiclistBinding
import kotlin.random.Random

class MyinfPicListAdapter(val itemList: ArrayList<MyInfPicInterfaceResponseResult>) : RecyclerView.Adapter<MyinfPicListAdapter.MyinfPicListViewHolder>(){
    private lateinit var itemClickListener : OnItemClickListener
    lateinit var binding: ItemMyinfPiclistBinding
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyinfPicListViewHolder {
        binding = ItemMyinfPiclistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyinfPicListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MyinfPicListViewHolder, position: Int) {
        val item = itemList[position]
        holder.setItem(item)
        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position)
        }
    }

    inner class MyinfPicListViewHolder(binding: ItemMyinfPiclistBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setItem(item: MyInfPicInterfaceResponseResult) {

            if(item.image.isBlank() || item.image == "" || item.image=="null"){
                val tempimagelist = ArrayList<String>()
                tempimagelist.add("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg")
                tempimagelist.add("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg")
                tempimagelist.add("https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg")
                Glide.with(binding.imgMyinfPic)
                    .load(tempimagelist.get(Random.nextInt(0,3)))                         // 불러올 이미지 URL
                    .fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                    .error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                    .placeholder(R.drawable.background_glide_init)  // 이미지 로딩 시작하기 전에 표시할 이미지
                    .centerInside()                                 // scaletype
                    .into(binding.imgMyinfPic)             // 이미지를 넣을 뷰
            }else{
                Log.d("MyinfPicListAdapter", "setItem: ${item}")
                val encodeByte = Base64.decode(item.image, Base64.NO_WRAP)
                val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
                binding.imgMyinfPic.setImageBitmap(bitmap)
                Glide.with(binding.imgMyinfPic)
                    .load(item.image)                         // 불러올 이미지 URL
                    .fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                    .error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                    .placeholder(R.drawable.background_glide_init)  // 이미지 로딩 시작하기 전에 표시할 이미지
                    .centerInside()                                 // scaletype
                    .into(binding.imgMyinfPic)             // 이미지를 넣을 뷰
            }
            val encodeByte = Base64.decode(item.userImage, Base64.NO_WRAP)
            val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)

        }
    }
}