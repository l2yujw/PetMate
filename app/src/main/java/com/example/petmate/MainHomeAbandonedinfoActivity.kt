package com.example.petmate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainHomeAbandonedinfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_home_abandonedinfo)

        val rv_board = findViewById<RecyclerView>(R.id.abandonedinfo_additionalinfo_recyclerview)

        val itemList = ArrayList<MainHomeAbandonedinfoData>()

        itemList.add(MainHomeAbandonedinfoData("발생장소", "임은동 806-4"))
        itemList.add(MainHomeAbandonedinfoData("접수일시", "2023-07-07"))
        itemList.add(MainHomeAbandonedinfoData("관할기관", "경상북도 구미시"))
        itemList.add(MainHomeAbandonedinfoData("상태", "보호중"))
        itemList.add(MainHomeAbandonedinfoData("보호장소", "경상북도 구미시 인동22길 43-4 (진평동)"))
        itemList.add(MainHomeAbandonedinfoData("보호센터연락처", "054-716-0211"))

        val boardAdapter = MainHomeAbandonedinfo(itemList)
        boardAdapter.notifyDataSetChanged()

        rv_board.adapter = boardAdapter
        rv_board.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}