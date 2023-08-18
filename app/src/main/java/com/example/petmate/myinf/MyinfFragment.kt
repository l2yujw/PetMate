package com.example.petmate.myinf

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.OnItemClickListener
import com.example.petmate.R

class MyinfFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var rootView = inflater.inflate(R.layout.fragment_myinf, container, false)

        var rcv_myinf_picList = rootView.findViewById<RecyclerView>(R.id.rcv_myinf_picList)
        var rcv_myinf_userList = rootView.findViewById<RecyclerView>(R.id.rcv_myinf_userList)

        var adapterMyinfPicList = MyinfPicListAdapter(getPetImageList())
        adapterMyinfPicList.notifyDataSetChanged()
        var adapterMyinfUserList = MyinfUserListAdapter(getPetImageList())
        adapterMyinfUserList.notifyDataSetChanged()

        rcv_myinf_picList.adapter = adapterMyinfPicList
        rcv_myinf_picList.layoutManager = GridLayoutManager(requireContext(), 3)

        adapterMyinfPicList.setItemClickListener(object : OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                TODO("Not yet implemented")
            }
        })

        rcv_myinf_userList.adapter = adapterMyinfUserList
        rcv_myinf_userList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        return rootView
    }

    private fun getPetImageList(): ArrayList<Int>{
        return arrayListOf<Int>(R.drawable.cat1_temp, R.drawable.cat2_temp, R.drawable.cat1_temp)
    }
}