package com.example.petmate.community

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.petmate.R
import com.example.petmate.databinding.FragmentCommunityPostBinding
import me.relex.circleindicator.CircleIndicator3
import kotlin.random.Random


class CommunityPostFragment : Fragment() {

    lateinit var binding: FragmentCommunityPostBinding
    lateinit var indicator: CircleIndicator3
    private val TAG = "CommunityFragment123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommunityPostBinding.inflate(inflater)
        indicator = binding.circleindicatorCommunityPost
        indicator.setViewPager(binding.viewpagerCommunityPost)


        val bundle = arguments
        val obj = bundle?.getParcelable<CommunityInterfaceResponseResult>("Postdata")

        val list = ArrayList<CommunityInterfaceResponseResult>()
        if (obj != null) {
            list.add(obj)
            if(obj.nickName.isBlank()){
                binding.nickNameCommunityPost.text = "익명"
            }else{
                binding.nickNameCommunityPost.text = obj.nickName
            }
            binding.titleCommunityPost.text = obj.title
            binding.tvCommunityPostContext.text = obj.detail
            Log.d(TAG, "onCreateView: ${obj}")
            if(obj.userImage.isBlank() || obj.userImage == ""){
                val tempuserImagelist = ArrayList<String>()
                tempuserImagelist.add("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg")
                tempuserImagelist.add("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg")
                tempuserImagelist.add("https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg")
                Glide.with(binding.userImageCocmmunityPost)
                    .load(tempuserImagelist.get(Random.nextInt(0,3)))                         // 불러올 이미지 URL
                    .fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                    .error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                    .placeholder(R.drawable.background_glide_init)  // 이미지 로딩 시작하기 전에 표시할 이미지
                    .centerInside()                                 // scaletype
                    .into(binding.userImageCocmmunityPost)             // 이미지를 넣을 뷰
            }else if(obj.userImage.endsWith(".png") ||obj.userImage.endsWith(".jpg")||obj.userImage.endsWith(".jpeg") ) {
                Glide.with(binding.userImageCocmmunityPost)
                    .load(obj.userImage)                         // 불러올 이미지 URL
                    .fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                    .error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                    .placeholder(R.drawable.background_glide_init)  // 이미지 로딩 시작하기 전에 표시할 이미지
                    .centerInside()                                 // scaletype
                    .into(binding.userImageCocmmunityPost)             // 이미지를 넣을 뷰
            }else{
                val encodeByte = Base64.decode(obj.userImage, Base64.NO_WRAP)
                val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
                binding.userImageCocmmunityPost.setImageBitmap(bitmap)
            }


        }


        val adapterPostList = CommunityPostAdapter(list)
        adapterPostList.notifyDataSetChanged()

        indicator.createIndicators(list.size, 0)

        binding.viewpagerCommunityPost.adapter = CommunityPostAdapter(list)
        binding.viewpagerCommunityPost.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewpagerCommunityPost.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                indicator.animatePageSelected(position)
            }
        })


        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_communityPostFragment_to_communityFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }
}