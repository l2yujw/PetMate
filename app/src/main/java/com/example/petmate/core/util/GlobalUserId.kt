package com.example.petmate.core.util

object GlobalUserId {
    private var userId: Int = 0

    fun setUserId(num: Int) {
        userId = num
    }

    fun getUserId(): Int {
        return userId
    }
}
