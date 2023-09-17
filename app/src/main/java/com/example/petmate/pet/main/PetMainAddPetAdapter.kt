package com.example.petmate.pet.main

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petmate.R
import com.example.petmate.myinf.MyinfPhotoAdapter
import kotlinx.coroutines.NonDisposableHandle.parent

class PetMainAddPetAdapter(): RecyclerView.Adapter<PetMainAddPetAdapter.PetMainAddPetViewHolder>() {

    lateinit var imageList: ArrayList<Uri>
    lateinit var context: Context

    constructor(imageList: ArrayList<Uri>, context: Context): this(){

        this.imageList = imageList
        this.context = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetMainAddPetViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)

        val view: View = inflater.inflate(R.layout.item_petmain_addpet, parent, false)
        return PetMainAddPetViewHolder(view)
    }

    override fun onBindViewHolder(holder: PetMainAddPetViewHolder, position: Int) {
        Glide.with(context)
            .load(imageList[position]) //이미지 위치
            .into(holder.galleryView)//보여줄 위치
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    class PetMainAddPetViewHolder(view: View): RecyclerView.ViewHolder(view){
        val galleryView: ImageView = view.findViewById(R.id.img_petMain_photo_post)
    }

}