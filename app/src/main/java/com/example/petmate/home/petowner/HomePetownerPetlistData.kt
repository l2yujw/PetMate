package com.example.petmate.home.petowner

import android.graphics.Bitmap

class HomePetownerPetlistData(var petlist_randomtext: String, var petlist_img: Bitmap){
    fun getImg():Bitmap{
        return this.petlist_img
    }
    fun setData(image:Bitmap){
        this.petlist_randomtext="랜덤문구"
        this.petlist_img=image
    }
}