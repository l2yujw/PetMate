package com.example.petmate.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.petmate.R

object GlideHelper {

    private val defaultImages = listOf(
        "https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg",
        "https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg",
        "https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"
    )

    fun loadImage(imageView: ImageView, imageUrl: String) {
        when {
            imageUrl.isBlank() -> loadDefaultImage(imageView)
            imageUrl.endsWith(".png") || imageUrl.endsWith(".jpg") || imageUrl.endsWith(".jpeg") -> loadNetworkImage(imageView, imageUrl)
            else -> loadBase64Image(imageView, imageUrl)
        }
    }

    private fun loadDefaultImage(imageView: ImageView) {
        Glide.with(imageView.context)
            .load(defaultImages.random())
            .fallback(R.drawable.background_glide_init)
            .error(R.drawable.background_glide_init)
            .placeholder(R.drawable.background_glide_init)
            .centerInside()
            .into(imageView)
    }

    private fun loadNetworkImage(imageView: ImageView, imageUrl: String) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .fallback(R.drawable.background_glide_init)
            .error(R.drawable.background_glide_init)
            .placeholder(R.drawable.background_glide_init)
            .centerInside()
            .into(imageView)
    }

    private fun loadBase64Image(imageView: ImageView, base64String: String) {
        val encodeByte: ByteArray = Base64.decode(base64String, Base64.NO_WRAP)
        val bitmap: Bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        imageView.setImageBitmap(bitmap)
    }
}
