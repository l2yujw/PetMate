package com.example.petmate.myinf

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petmate.VerticalItemDecorator
import com.example.petmate.databinding.FragmentMyinfPostBinding

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

}