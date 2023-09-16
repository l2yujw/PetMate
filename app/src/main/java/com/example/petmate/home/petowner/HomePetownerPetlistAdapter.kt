package com.example.petmate.home.petowner

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petmate.R
import com.example.petmate.databinding.ItemHomePetownerPetlistBinding
import kotlin.random.Random

class HomePetownerPetlistAdapter(var petList: ArrayList<HomePetownerPetlistData>) : RecyclerView.Adapter<HomePetownerPetlistAdapter.PetownerPetlistViewHolder>() {

    lateinit var binding: ItemHomePetownerPetlistBinding
    private val TAG="HomePetownerPetlistAdapter123"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetownerPetlistViewHolder {
        binding = ItemHomePetownerPetlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PetownerPetlistViewHolder(binding)
    }

    override fun getItemCount(): Int = petList.size

    override fun onBindViewHolder(holder: PetownerPetlistViewHolder, position: Int) {
        val item = petList[position]
        holder.setItem(item)

    }


    inner class PetownerPetlistViewHolder(binding: ItemHomePetownerPetlistBinding) : RecyclerView.ViewHolder(binding.root) {



        fun setItem(item: HomePetownerPetlistData) {
            binding.petownerPetlistRandomtext.text = item.petlist_randomtext


            if(item.petlist_img.isBlank() || item.petlist_img == ""){
                val tempimagelist = ArrayList<String>()
                tempimagelist.add("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg")
                tempimagelist.add("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg")
                tempimagelist.add("https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg")
                Glide.with(binding.petownerPetlistImage)
                    .load(tempimagelist.get(Random.nextInt(0,3)))                         // 불러올 이미지 URL
                    .fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                    .error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                    .placeholder(R.drawable.background_glide_init)  // 이미지 로딩 시작하기 전에 표시할 이미지
                    .centerInside()                                 // scaletype
                    .into(binding.petownerPetlistImage)             // 이미지를 넣을 뷰
            }else if(item.petlist_img.endsWith(".png") ||item.petlist_img.endsWith(".jpg")||item.petlist_img.endsWith(".jpeg") ) {
                Glide.with(binding.petownerPetlistImage)
                    .load(item.petlist_img)                         // 불러올 이미지 URL
                    .fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                    .error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                    .placeholder(R.drawable.background_glide_init)  // 이미지 로딩 시작하기 전에 표시할 이미지
                    .centerInside()                                 // scaletype
                    .into(binding.petownerPetlistImage)             // 이미지를 넣을 뷰
            }else{
                val encodeByte = Base64.decode(item.petlist_img, Base64.NO_WRAP)
                val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
                binding.petownerPetlistImage.setImageBitmap(bitmap)
            }
        }
    }
}