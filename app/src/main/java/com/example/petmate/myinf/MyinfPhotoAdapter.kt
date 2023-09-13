package com.example.petmate.myinf

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petmate.R

class MyinfPhotoAdapter(): RecyclerView.Adapter<MyinfPhotoAdapter.ViewHolder>() {

    lateinit var imageList: ArrayList<Uri>
    lateinit var context: Context

    /**
     * 생성자
     */
    constructor(imageList: ArrayList<Uri>, context: Context): this(){

        this.imageList = imageList
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)

        val view: View = inflater.inflate(R.layout.item_myinf_photo, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(imageList[position]) //이미지 위치
            .into(holder.galleryView)//보여줄 위치
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        val galleryView: ImageView = view.findViewById(R.id.img_myinf_photo_post)
    }
}