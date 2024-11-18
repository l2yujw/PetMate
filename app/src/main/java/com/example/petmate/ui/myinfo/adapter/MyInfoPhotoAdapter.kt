package com.example.petmate.ui.myinfo.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petmate.R

class MyInfoPhotoAdapter(
    private val imageList: ArrayList<Uri>,
    private val context: Context
) : RecyclerView.Adapter<MyInfoPhotoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_myinf_photo, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = imageList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(imageList[position]) // 이미지 위치
            .placeholder(R.drawable.background_glide_init) // 로딩 중 표시할 이미지
            .error(R.drawable.background_glide_init) // 로딩 실패 시 표시할 이미지
            .into(holder.imageView) // 이미지가 로드될 위치
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.img_myinf_photo_post)
    }
}
