package com.example.petmate.myinf

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.OnItemClickListener
import com.example.petmate.R
import com.example.petmate.databinding.FragmentMyinfBinding
import com.example.petmate.pet.training.PetTrainingListData

class MyinfFragment : Fragment() {

    lateinit var binding: FragmentMyinfBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyinfBinding.inflate(inflater)

        var adapterMyinfPicList = MyinfPicListAdapter(getPicList())
        adapterMyinfPicList.notifyDataSetChanged()
        var adapterMyinfUserList = MyinfUserListAdapter(getUserList())
        adapterMyinfUserList.notifyDataSetChanged()

        binding.rcvMyinfPicList.adapter = adapterMyinfPicList
        binding.rcvMyinfPicList.layoutManager = GridLayoutManager(requireContext(), 3)

        adapterMyinfPicList.setItemClickListener(object : OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                TODO("Not yet implemented")
            }
        })

        binding.rcvMyinfUserList.adapter = adapterMyinfUserList
        binding.rcvMyinfUserList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        return binding.getRoot()
    }

    private fun getPicList(): ArrayList<MyinfPicListData>{
        val picList = ArrayList<MyinfPicListData>()

        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        picList.add(MyinfPicListData( "https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        picList.add(MyinfPicListData( "https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        picList.add(MyinfPicListData( "https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        picList.add(MyinfPicListData( "https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        picList.add(MyinfPicListData( "https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        picList.add(MyinfPicListData( "https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))

        return picList
    }

    private fun getUserList(): ArrayList<MyinfUserListData>{
        val userList = ArrayList<MyinfUserListData>()

        userList.add(MyinfUserListData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        userList.add(MyinfUserListData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        userList.add(MyinfUserListData( "https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))

        return userList
    }
}