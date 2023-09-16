package com.example.petmate.community

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.petmate.R
import com.example.petmate.databinding.FragmentCommunityPostBinding
import com.example.petmate.pet.training.PetTrainingInterfaeResponseResult
import me.relex.circleindicator.CircleIndicator3
import kotlin.random.Random


class CommunityPostFragment : Fragment() {

    lateinit var binding: FragmentCommunityPostBinding
    lateinit var indicator: CircleIndicator3
    private val TAG = "CommunityFragment123"

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

        Log.d(TAG, "CommunityPostFragment123")

        val bundle = arguments
        val obj = bundle?.getParcelable<CommunityInterfaceResponseResult>("Postdata")

        val list = ArrayList<CommunityInterfaceResponseResult>()
        if (obj != null) {
            list.add(obj)
            if(obj.nickName.isNullOrBlank()){
                binding.nickNameCommunityPost.text = "익명"
            }else{
                binding.nickNameCommunityPost.text = obj.nickName
            }
            binding.titleCommunityPost.text = obj.title
            binding.tvCommunityPostContext.text = obj.detail
            Log.d(TAG, "onCreateView: ${obj}")
            if(obj.userImage.isNullOrBlank() || obj.userImage == ""){
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