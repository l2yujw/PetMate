package com.example.petmate.ui.community.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.petmate.databinding.FragmentCommunityPostBinding
import com.example.petmate.remote.response.community.CommunityResult
import com.example.petmate.ui.community.adapter.CommunityPostAdapter
import com.example.petmate.util.GlideHelper
import me.relex.circleindicator.CircleIndicator3

class CommunityPostFragment : Fragment() {

    private var _binding: FragmentCommunityPostBinding? = null
    private val binding get() = _binding!!
    private lateinit var indicator: CircleIndicator3
    private val TAG = CommunityPostFragment::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommunityPostBinding.inflate(inflater)
        indicator = binding.ciImageIndicator

        val bundle = arguments
        val postData = bundle?.getParcelable<CommunityResult>("Postdata")

        if (postData == null) {
            // Postdata가 없을 때 처리
            findNavController().navigateUp()
            return binding.root
        }

        val postList = ArrayList<CommunityResult>()
        postList.add(postData)
        setNickname(postData.nickName)
        setPostDetails(postData)
        GlideHelper.loadImage(binding.ivCommunityPost, postData.userImage)

        setupViewPager(postList)

        return binding.root
    }

    private fun setNickname(nickName: String) {
        binding.tvUserId.text = if (nickName.isBlank()) "익명" else nickName
    }

    private fun setPostDetails(postData: CommunityResult) {
        binding.tvPetMateTitle.text = postData.title
        binding.tvPostContent.text = postData.detail
    }

    private fun setupViewPager(postList: List<CommunityResult>) {
        if (postList.isNotEmpty()) {
            val adapterPostList = CommunityPostAdapter(postList)
            indicator.createIndicators(postList.size, 0)

            binding.vpImageCarousel.adapter = adapterPostList
            binding.vpImageCarousel.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            binding.vpImageCarousel.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    indicator.animatePageSelected(position)
                }
            })
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()  // 이전 화면으로 돌아가기
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
