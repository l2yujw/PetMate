package com.example.petmate

object GlobalPetIdxList {
    private var list = ArrayList<Int>(1)

    fun addlist(petIdx:Int){
        list.add(petIdx)
    }

    fun getlist():ArrayList<Int>{
        return list
    }
}