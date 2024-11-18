package com.example.petmate.repository.community

import com.example.petmate.remote.api.community.CommunityService
import com.example.petmate.core.network.NetworkClient
import com.example.petmate.remote.response.community.CommunityResponse
import retrofit2.Callback

class CommunityRepository {
    private val service = NetworkClient.getRetrofit().create(CommunityService::class.java)

    fun getPopularList(today: String, callback: Callback<CommunityResponse>) {
        service.getPopularList(today).enqueue(callback)
    }

    fun getBoardList(callback: Callback<CommunityResponse>) {
        service.getBoardList().enqueue(callback)
    }
}
