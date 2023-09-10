package com.example.petmate

object petIdxList {
    private var list = ArrayList<Int>()

    fun addlist(petIdx:Int){
        list.add(petIdx)
    }

    fun getlist():ArrayList<Int>{
        return list
    }
}