package com.example.petmate.myinf

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.petmate.R
import com.example.petmate.community.CommunityBoardData
import com.example.petmate.databinding.ItemCommunityBoardBinding
import com.example.petmate.databinding.ItemMyinfPostBinding
import com.example.petmate.home.petseeker.HomePetseekerRecommendPetListInterfaceResponseResponseResult
import kotlin.random.Random

class MyinfPostAdapter(val itemList: ArrayList<MyInfPicInterfaceResponseResult>) : RecyclerView.Adapter<MyinfPostAdapter.MyinfPostViewHolder>()  {

    lateinit var binding: ItemMyinfPostBinding
    private val TAG = "MyinfPostAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyinfPostAdapter.MyinfPostViewHolder {
        binding = ItemMyinfPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d("MyinfPostAdapter", "onCreateViewHolder: parent context : ${parent.context}")
        return MyinfPostViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MyinfPostAdapter.MyinfPostViewHolder, position: Int) {
        var itemSubList = ArrayList<MyInfPicInterfaceResponseResult>()
        Log.d("MyinfPostAdapter", "onBindViewHolder: ${itemSubList}")
        holder.setItem(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class MyinfPostViewHolder(binding: ItemMyinfPostBinding) : RecyclerView.ViewHolder(binding.root){
        fun setItem(item: MyInfPicInterfaceResponseResult) {

            if(item.image.isBlank() || item.image == ""){
                val tempimagelist = ArrayList<String>()
                tempimagelist.add("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg")
                tempimagelist.add("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg")
                tempimagelist.add("https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg")
                Glide.with(binding.imageMyinfPost)
                    .load(tempimagelist.get(Random.nextInt(0,3)))                         // 불러올 이미지 URL
                    .fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                    .error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                    .placeholder(R.drawable.background_glide_init)  // 이미지 로딩 시작하기 전에 표시할 이미지
                    .centerInside()                                 // scaletype
                    .into(binding.imageMyinfPost)             // 이미지를 넣을
            }else if(item.image.endsWith(".png") ||item.image.endsWith(".jpg")||item.image.endsWith(".jpeg") ) {
                Glide.with(binding.userImageMyinfPost)
                    .load(item.image)                         // 불러올 이미지 URL
                    .fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                    .error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                    .placeholder(R.drawable.background_glide_init)  // 이미지 로딩 시작하기 전에 표시할 이미지
                    .centerInside()                                 // scaletype
                    .into(binding.imageMyinfPost)             // 이미지를 넣을 뷰
            }else{
                val encodeByte = Base64.decode(item.image, Base64.NO_WRAP)
                val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
                Log.d(TAG, "item.userImage : ${item.image}")
                Log.d(TAG, "encodeByte : ${encodeByte}")
                Log.d(TAG, "bitmap : ${bitmap}")
                binding.imageMyinfPost.setImageBitmap(bitmap)
            }

            if(item.userImage.isBlank() || item.userImage == ""){
                val tempimagelist = ArrayList<String>()
                tempimagelist.add("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg")
                tempimagelist.add("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg")
                tempimagelist.add("https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg")
                Glide.with(binding.userImageMyinfPost)
                    .load(tempimagelist.get(Random.nextInt(0,3)))                         // 불러올 이미지 URL
                    .fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                    .error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                    .placeholder(R.drawable.background_glide_init)  // 이미지 로딩 시작하기 전에 표시할 이미지
                    .centerInside()                                 // scaletype
                    .into(binding.userImageMyinfPost)             // 이미지를 넣을 뷰
            }else if(item.userImage.endsWith(".png") ||item.userImage.endsWith(".jpg")||item.userImage.endsWith(".jpeg") ) {
                Glide.with(binding.userImageMyinfPost)
                    .load(item.userImage)                         // 불러올 이미지 URL
                    .fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                    .error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                    .placeholder(R.drawable.background_glide_init)  // 이미지 로딩 시작하기 전에 표시할 이미지
                    .centerInside()                                 // scaletype
                    .into(binding.userImageMyinfPost)             // 이미지를 넣을 뷰
            }else{
                val encodeByte = Base64.decode(item.userImage, Base64.NO_WRAP)
                val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
                binding.userImageMyinfPost.setImageBitmap(bitmap)
            }




            binding.nickNameMyinfPost.text = item.nickName
            binding.detailMyinfPost.text = item.detail
            binding.titleMyinfPost.text = item.title




        }

    }
}