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
        fun setItem(item: CommunityInterfaceResponseResult) {

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
                val encodeByte = Base64.decode(item.userImage, Base64.NO_WRAP)
                val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
                binding.imgCommunityBoard.setImageBitmap(bitmap)
                binding.userImageCommunityBoard.setImageBitmap(bitmap)

            }else{
                val encodeByte = Base64.decode(item.userImage, Base64.NO_WRAP)
                val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
                binding.imgCommunityBoard.setImageBitmap(bitmap)
                Glide.with(binding.imgCommunityBoard)
                    .load(item.image)                         // 불러올 이미지 URL
                    .fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                    .error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                    .placeholder(R.drawable.background_glide_init)  // 이미지 로딩 시작하기 전에 표시할 이미지
                    .centerInside()                                 // scaletype
                    .into(binding.imgCommunityBoard)             // 이미지를 넣을 뷰

                binding.userImageCommunityBoard.setImageBitmap(bitmap)
            }
            binding.nickNameCommunityBoard.text = item.nickName
            binding.titleCommunityBoard.text = item.title
        }

    }
    fun bitmapToUrl(bitmap: Bitmap): String {
        // Bitmap을 ByteArray로 변환
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val byteArray = outputStream.toByteArray()

        // ByteArray를 Base64로 인코딩
        val base64Encoded = Base64.encodeToString(byteArray, Base64.DEFAULT)

        // Base64 문자열을 URL 데이터로 사용할 수 있도록 인코딩 (예: data:image/png;base64,xxxxxx)
        val imageUrl = "data:image/png;base64,$base64Encoded"

        return imageUrl
    }
}