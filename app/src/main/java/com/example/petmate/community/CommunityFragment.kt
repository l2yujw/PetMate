package com.example.petmate.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.OnItemClickListener
import com.example.petmate.R
import com.example.petmate.HorizontalItemDecorator
import com.example.petmate.RightItemDecorator

class CommunityFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_community, container, false)

        var rcv_community_popular = rootView.findViewById<RecyclerView>(R.id.rcv_community_popular)
        var rcv_community_board = rootView.findViewById<RecyclerView>(R.id.rcv_community_board)

        var adapterCommunityPopular = CommunityPopularAdapter(getImageList())
        adapterCommunityPopular.notifyDataSetChanged()
        var adapterCommunityBoard = CommunityBoardAdapter(getImageList())
        adapterCommunityBoard.notifyDataSetChanged()

        rcv_community_popular.adapter = adapterCommunityPopular
        rcv_community_popular.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rcv_community_popular.addItemDecoration(RightItemDecorator(5))

        adapterCommunityPopular.setItemClickListener(object : OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                TODO("Not yet implemented")
            }
        })

        rcv_community_board.adapter = adapterCommunityBoard
        rcv_community_board.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        adapterCommunityBoard.setItemClickListener(object : OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                TODO("Not yet implemented")
            }
        })


        return rootView
    }

    private fun getImageList(): ArrayList<Int>{
        return arrayListOf<Int>(R.drawable.cat1_temp, R.drawable.cat2_temp, R.drawable.cat1_temp)
    }
}