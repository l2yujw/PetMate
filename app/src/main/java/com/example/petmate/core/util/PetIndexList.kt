package com.example.petmate.core.util

object PetIndexList {
    private var petIndices = mutableListOf<Int>()

    fun clear() {
        petIndices.clear()
    }

    fun add(index: Int) {
        petIndices.add(index)
    }

    fun get(): List<Int> {
        return petIndices.toList()
    }
}
