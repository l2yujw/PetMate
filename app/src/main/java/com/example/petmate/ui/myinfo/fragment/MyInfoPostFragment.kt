package com.example.petmate.ui.myinfo.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petmate.R
import com.example.petmate.core.decorator.VerticalDividerItemDecorator
import com.example.petmate.databinding.FragmentMyinfPostBinding
import com.example.petmate.remote.response.myinfo.MyInfoImageResult
import com.example.petmate.ui.myinfo.adapter.MyInfoPostAdapter

class MyInfoPostFragment : Fragment() {

    private lateinit var binding: FragmentMyinfPostBinding
    private val TAG = "MyInfoPostFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyinfPostBinding.inflate(inflater)

        val postData = arguments?.getParcelableArrayList<MyInfoImageResult>("Postdata")
        Log.d(TAG, "PostData received: $postData")

        postData?.let {
            setupRecyclerView(it)
        }

        return binding.root
    }

    private fun setupRecyclerView(postData: List<MyInfoImageResult>) {
        val postAdapter = MyInfoPostAdapter(ArrayList(postData))
        binding.rvMyinfPost.apply {
            adapter = postAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(VerticalDividerItemDecorator(30))
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_myinfPostFragment_to_myinfFragment)
                }
            }
        )
    }
}
