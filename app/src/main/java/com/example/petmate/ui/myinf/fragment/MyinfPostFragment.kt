package com.example.petmate.ui.myinf.fragment

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
import com.example.petmate.core.decorator.VerticalItemDecorator
import com.example.petmate.databinding.FragmentMyinfPostBinding
import com.example.petmate.remote.response.myinf.MyInfPicInterfaceResponseResult
import com.example.petmate.ui.myinf.adapter.MyinfPostAdapter

class MyinfPostFragment : Fragment() {

    lateinit var binding: FragmentMyinfPostBinding
    private val TAG="MyinfFragment123"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyinfPostBinding.inflate(inflater)


        val bundle = arguments
        val obj = bundle?.getParcelableArrayList<MyInfPicInterfaceResponseResult>("Postdata")

        Log.d(TAG, "bundle123 : $obj")
        val adapterMyinfPostAdapter = obj?.let {
            MyinfPostAdapter(it)
        }
        adapterMyinfPostAdapter?.notifyDataSetChanged()

        binding.rcvMyinfPost.adapter = adapterMyinfPostAdapter
        binding.rcvMyinfPost.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcvMyinfPost.addItemDecoration(VerticalItemDecorator(30))

        return binding.root
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_myinfPostFragment_to_myinfFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }
}