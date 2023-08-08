package com.example.petmate.walk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.R

class WalkRecordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_walk_record)

        val rv_board = findViewById<RecyclerView>(R.id.walk_record_recyclerview)

        val itemList = ArrayList<WalkRecordData>()

        itemList.add(WalkRecordData("보호자1","00:29:02", "310","32","65","1","00:32"))
        itemList.add(WalkRecordData("보호자1","00:29:02", "310","32","65","1","00:32"))
        itemList.add(WalkRecordData("보호자1","00:29:02", "310","32","65","1","00:32"))
        itemList.add(WalkRecordData("보호자1","00:29:02", "310","32","65","1","00:32"))
        itemList.add(WalkRecordData("보호자1","00:29:02", "310","32","65","1","00:32"))

        val boardAdapter = WalkRecord(itemList)
        boardAdapter.notifyDataSetChanged()

        rv_board.adapter = boardAdapter
        rv_board.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

}