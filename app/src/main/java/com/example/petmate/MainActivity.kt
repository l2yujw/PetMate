package com.example.petmate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_home_abandonedinfo)

        val rv_board = findViewById<RecyclerView>(R.id.abandonedinfo_additionalinfo_recyclerview)

        val itemList = ArrayList<MainHomeAbandonedinfoActivityData>()

        itemList.add(MainHomeAbandonedinfoActivityData("발생장소", "임은동 806-4"))
        itemList.add(MainHomeAbandonedinfoActivityData("접수일시", "2023-07-07"))
        itemList.add(MainHomeAbandonedinfoActivityData("관할기관", "경상북도 구미시"))
        itemList.add(MainHomeAbandonedinfoActivityData("상태", "보호중"))
        itemList.add(MainHomeAbandonedinfoActivityData("보호장소", "경상북도 구미시 인동22길 43-4 (진평동)"))
        itemList.add(MainHomeAbandonedinfoActivityData("보호센터연락처", "054-716-0211"))

        val boardAdapter=MainHomeAbandonedinfoActivity(itemList)
        boardAdapter.notifyDataSetChanged()

        rv_board.adapter=boardAdapter
        rv_board.layoutManager= LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }
    //411*846
    //기본보다 -28dp 탑 마진
}