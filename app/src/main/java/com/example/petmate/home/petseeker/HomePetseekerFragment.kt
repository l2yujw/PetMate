package com.example.petmate.home.petseeker

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import java.util.*
import com.example.petmate.databinding.FragmentHomePetseekerBinding

class HomePetseekerFragment : Fragment() {

    lateinit var binding: FragmentHomePetseekerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    @SuppressLint("SetTextI18n", "MissingPermission")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomePetseekerBinding.inflate(inflater)

        val pettList = ArrayList<HomePetseekerListData>()
        val recommend = ArrayList<HomePetseekerRecommendData>()

        pettList.add(
            HomePetseekerListData(
                "https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg", "믹스견", "수컷", "흰색, 갈색", "생후 60일 추정",
                "https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg", "믹스견", "암컷", "흰색", "겁이 많음",
                "https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg", "믹스견", "수컷", "갈색", "얌전함"
            )
        )
        pettList.add(
            HomePetseekerListData(
                "https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg", "믹스견", "수컷", "흰색, 갈색", "생후 60일 추정",
                "https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg", "믹스견", "암컷", "흰색", "겁이 많음",
                "https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg", "믹스견", "수컷", "갈색", "얌전함"
            )
        )
        pettList.add(
            HomePetseekerListData(
                "https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg", "믹스견", "수컷", "흰색, 갈색", "생후 60일 추정",
                "https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg", "믹스견", "암컷", "흰색", "겁이 많음",
                "https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg", "믹스견", "수컷", "갈색", "얌전함"
            )
        )

        recommend.add(HomePetseekerRecommendData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg", "한국 고양이", "수컷", "흰색, 갈색", "사람을 좋아하고 얌전함"))
        recommend.add(HomePetseekerRecommendData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg", "한국 고양이", "수컷", "흰색, 갈색", "사람을 좋아하고 얌전함"))

        val boardAdapterPetList = HomePetseekerListAdapter(pettList)
        boardAdapterPetList.notifyDataSetChanged()
        val boardAdapterRecommend = HomePetseekerRecommendAdapter(recommend)
        boardAdapterRecommend.notifyDataSetChanged()

        val indicatorList = binding.circleindicatorPetseekerPetlist
        val indicatorReccomend = binding.circleindicatorPetseekerRecommend

        binding.viewpagerPetseekerList.adapter = HomePetseekerListAdapter(pettList)
        binding.viewpagerPetseekerList.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.viewpagerPetseekerRecommend.adapter = HomePetseekerRecommendAdapter(recommend)
        binding.viewpagerPetseekerRecommend.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        indicatorList.setViewPager(binding.viewpagerPetseekerList)
        indicatorList.createIndicators(pettList.size, 0)

        indicatorReccomend.setViewPager(binding.viewpagerPetseekerRecommend)
        indicatorReccomend.createIndicators(recommend.size, 0)

        //현재 page 받아옴 (fragment 혹은 image 어떤걸로 할지 고려중)
        binding.viewpagerPetseekerList.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                indicatorList.animatePageSelected(position)
                Toast.makeText(requireContext(), "${position + 1} 페이지 선택됨", Toast.LENGTH_SHORT).show()
            }
        })

        return binding.getRoot()
    }


    inner class HorizontalSpaceItemDecoration(private val horizontalSpaceHeight: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            outRect.right = horizontalSpaceHeight
        }
    }


}