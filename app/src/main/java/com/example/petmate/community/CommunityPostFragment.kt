package com.example.petmate.community

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.petmate.R
import com.example.petmate.databinding.FragmentCommunityPostBinding
import me.relex.circleindicator.CircleIndicator3


class CommunityPostFragment : Fragment() {

    lateinit var binding: FragmentCommunityPostBinding
    lateinit var indicator: CircleIndicator3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_communityPostFragment_to_communityFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommunityPostBinding.inflate(inflater)
        indicator = binding.circleindicatorCommunityPost
        indicator.setViewPager(binding.viewpagerCommunityPost)

        val adapterPostList = CommunityPostAdapter(getPostList())
        adapterPostList.notifyDataSetChanged()

        indicator.createIndicators(getPostList().size, 0)

        binding.viewpagerCommunityPost.adapter = CommunityPostAdapter(getPostList())
        binding.viewpagerCommunityPost.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewpagerCommunityPost.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                indicator.animatePageSelected(position)
                //Toast.makeText(requireContext(), "${position + 1} 페이지 선택됨", Toast.LENGTH_SHORT).show()
            }
        })

        return binding.getRoot()
    }

    private fun getPostList(): ArrayList<CommunityPostData> {//혹시 모를 일을위해 여기를 먼저 깔아놓고 불러온걸 덮어쓰면 시간이 맞을듯?

        val postList = ArrayList<CommunityPostData>()
        val am = resources.assets
        postList.add(CommunityPostData(BitmapFactory.decodeStream(am.open("pet1.jpg"))))
        postList.add(CommunityPostData(BitmapFactory.decodeStream(am.open("pet1.jpg"))))

        return postList
    }
}