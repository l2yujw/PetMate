package com.example.petmate

object GlobalPetIdxList {
    private var list = ArrayList<Int>()

    fun clearlist(){
        list = ArrayList<Int>()
    }
    fun addlist(petIdx:Int){
        list.add(petIdx)
    }

    fun getlist():ArrayList<Int>{
        return list
    }
}