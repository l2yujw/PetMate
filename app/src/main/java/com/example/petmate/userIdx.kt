package com.example.petmate

object userIdx {
    private var userIdx:Int? = 0

    fun setUserIdx(num:Int){
        userIdx = num
    }

    fun getUserIdx():Int{
        return userIdx!!
    }
}