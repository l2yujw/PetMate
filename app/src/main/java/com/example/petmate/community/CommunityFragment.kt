package com.example.petmate.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petmate.OnItemClickListener
import com.example.petmate.RightItemDecorator
import com.example.petmate.databinding.FragmentCommunityBinding

class CommunityFragment : Fragment() {

    lateinit var binding: FragmentCommunityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCommunityBinding.inflate(inflater)

        var adapterCommunityPopular = CommunityPopularAdapter(getPopularList())
        adapterCommunityPopular.notifyDataSetChanged()
        var adapterCommunityBoard = CommunityBoardAdapter(getBoardList())
        adapterCommunityBoard.notifyDataSetChanged()

        binding.rcvCommunityPopular.adapter = adapterCommunityPopular
        binding.rcvCommunityPopular.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcvCommunityPopular.addItemDecoration(RightItemDecorator(20))

        adapterCommunityPopular.setItemClickListener(object : OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                TODO("Not yet implemented")
            }
        })

        binding.rcvCommunityBoard.adapter = adapterCommunityBoard
        binding.rcvCommunityBoard.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        adapterCommunityBoard.setItemClickListener(object : OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                TODO("Not yet implemented")
            }
        })

        return binding.getRoot()
    }
    private fun getPopularList(): ArrayList<CommunityPopularData>{
        val popularList = ArrayList<CommunityPopularData>()

        popularList.add(CommunityPopularData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        popularList.add(CommunityPopularData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        popularList.add(CommunityPopularData( "https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))

        return popularList
    }

    private fun getBoardList(): ArrayList<CommunityBoardData>{
        val boardList = ArrayList<CommunityBoardData>()

        boardList.add(CommunityBoardData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        boardList.add(CommunityBoardData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        boardList.add(CommunityBoardData( "https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))

        return boardList
    }
}