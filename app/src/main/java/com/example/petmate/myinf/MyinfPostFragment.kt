package com.example.petmate.myinf

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petmate.R
import com.example.petmate.databinding.FragmentMyinfPostBinding

class MyinfPostFragment : Fragment() {

    lateinit var binding: FragmentMyinfPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_myinfPostFragment_to_myinfFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyinfPostBinding.inflate(inflater)

        var adapterMyinfPostAdapter = MyinfPostAdapter(getPostList())
        adapterMyinfPostAdapter.notifyDataSetChanged()

        binding.rcvMyinfPost.adapter = adapterMyinfPostAdapter
        binding.rcvMyinfPost.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        return binding.getRoot()
    }

    private fun getPostList(): ArrayList<MyinfPostData>{
        val postList = ArrayList<MyinfPostData>()

        postList.add(MyinfPostData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        postList.add(MyinfPostData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        postList.add(MyinfPostData( "https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))

        return postList
    }

}