package com.example.petmate.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.R

class HomeShelterpetInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_shelterpet)

        val rv_board = findViewById<RecyclerView>(R.id.abandonedinfo_additionalinfo_recyclerview)

        val itemList = ArrayList<HomeShelterpetInfoData>()

        itemList.add(HomeShelterpetInfoData("발생장소", "임은동 806-4"))
        itemList.add(HomeShelterpetInfoData("접수일시", "2023-07-07"))
        itemList.add(HomeShelterpetInfoData("관할기관", "경상북도 구미시"))
        itemList.add(HomeShelterpetInfoData("상태", "보호중"))
        itemList.add(HomeShelterpetInfoData("보호장소", "경상북도 구미시 인동22길 43-4 (진평동)"))
        itemList.add(HomeShelterpetInfoData("보호센터연락처", "054-716-0211"))

        val boardAdapter = HomeShelterpetInfo(itemList)
        boardAdapter.notifyDataSetChanged()

        rv_board.adapter = boardAdapter
        rv_board.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}