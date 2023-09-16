package com.example.petmate.community

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petmate.OnItemClickListener
import com.example.petmate.R
import com.example.petmate.databinding.ItemCommunityBoardBinding
import java.io.ByteArrayOutputStream
import kotlin.random.Random


class CommunityBoardAdapter(val itemList: ArrayList<CommunityInterfaceResponseResult>) : RecyclerView.Adapter<CommunityBoardAdapter.CommunityBoardViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener
    lateinit var binding: ItemCommunityBoardBinding

    fun setItemClickListener(onItemClickListener: OnItemClickListener){
        this.itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityBoardViewHolder {
        binding = ItemCommunityBoardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommunityBoardViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: CommunityBoardViewHolder, position: Int) {
        val item = itemList[position]
        holder.setItem(item)

        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position)
        }
    }

    inner class CommunityBoardViewHolder(binding: ItemCommunityBoardBinding) : RecyclerView.ViewHolder(binding.root){
        fun setItem(item: CommunityInterfaceResponseResult) {//게시글 사진
            if(item.image.isBlank() || item.image == ""){
                val tempimagelist = ArrayList<String>()
                tempimagelist.add("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg")
                tempimagelist.add("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg")
                tempimagelist.add("https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg")
                Glide.with(binding.imgCommunityBoard)
                    .load(tempimagelist.get(Random.nextInt(0,3)))                         // 불러올 이미지 URL
                    .fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                    .error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                    .placeholder(R.drawable.background_glide_init)  // 이미지 로딩 시작하기 전에 표시할 이미지
                    .centerInside()                                 // scaletype
                    .into(binding.imgCommunityBoard)             // 이미지를 넣을 뷰
            }else if(item.image.endsWith(".png") ||item.image.endsWith(".jpg")||item.image.endsWith(".jpeg") ) {
                Glide.with(binding.imgCommunityBoard)
                    .load(item.image)                         // 불러올 이미지 URL
                    .fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                    .error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                    .placeholder(R.drawable.background_glide_init)  // 이미지 로딩 시작하기 전에 표시할 이미지
                    .centerInside()                                 // scaletype
                    .into(binding.imgCommunityBoard)             // 이미지를 넣을 뷰
            }else{
                val encodeByte = Base64.decode(item.image, Base64.NO_WRAP)
                val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
                binding.imgCommunityBoard.setImageBitmap(bitmap)
            }

            if(item.userImage.isNullOrBlank()|| item.userImage=="" || item.userImage=="NULL") {//유저 이미지
                Glide.with(binding.userImageCommunityBoard)
                    .load("https://cdn-icons-png.flaticon.com/128/4863/4863153.png")                         // 불러올 이미지 URL
                    .fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                    .error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                    .placeholder(R.drawable.background_glide_init)  // 이미지 로딩 시작하기 전에 표시할 이미지
                    .centerInside()                                 // scaletype
                    .into(binding.userImageCommunityBoard)             // 이미지를 넣을 뷰
            }else if(item.userImage.endsWith(".png") || item.userImage.endsWith(".jpg")){
                Glide.with(binding.userImageCommunityBoard)
                    .load(item.userImage)                         // 불러올 이미지 URL
                    .fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                    .error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                    .placeholder(R.drawable.background_glide_init)  // 이미지 로딩 시작하기 전에 표시할 이미지
                    .centerInside()                                 // scaletype
                    .into(binding.userImageCommunityBoard)             // 이미지를 넣을 뷰
            }else{
                val encodeByte = Base64.decode(item.userImage, Base64.NO_WRAP)
                val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
                binding.userImageCommunityBoard.setImageBitmap(bitmap)
            }

            if(item.nickName.isNullOrBlank()) {
                binding.nickNameCommunityBoard.text = "익명"
            }else{
                binding.nickNameCommunityBoard.text = item.nickName
            }

            binding.titleCommunityBoard.text = item.title
        }

    }
}