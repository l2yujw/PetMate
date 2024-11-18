package com.example.petmate.ui.walk.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petmate.databinding.ActivityWalkRecordBinding
import com.example.petmate.ui.walk.adapter.WalkRecordAdapter
import com.example.petmate.ui.walk.data.WalkRecordData

class WalkRecordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWalkRecordBinding
    private lateinit var adapter: WalkRecordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalkRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = WalkRecordAdapter(getWalkRecordData())
        binding.walkRecordRecyclerview.apply {
            adapter = this@WalkRecordActivity.adapter
            layoutManager = LinearLayoutManager(this@WalkRecordActivity)
        }
    }

    private fun getWalkRecordData(): List<WalkRecordData> {
        return listOf(
            WalkRecordData("보호자1", "00:29:02", "310", "32", "65", "1", "00:32"),
            WalkRecordData("보호자2", "00:45:15", "450", "40", "90", "2", "00:45"),
            WalkRecordData("보호자3", "01:10:50", "600", "60", "120", "3", "01:10")
        )
    }
}
