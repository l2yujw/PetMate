package com.example.petmate.home.petseeker

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.petmate.OnItemClickListener
import com.example.petmate.R
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

        val boardAdapterPetList = HomePetseekerListAdapter(getPetList())
        boardAdapterPetList.notifyDataSetChanged()
        val boardAdapterRecommend = HomePetseekerRecommendAdapter(getRecommendList())
        boardAdapterRecommend.notifyDataSetChanged()

        val indicatorList = binding.circleindicatorPetseekerPetlist
        val indicatorReccomend = binding.circleindicatorPetseekerRecommend

        indicatorList.setViewPager(binding.viewpagerPetseekerList)
        indicatorList.createIndicators(getPetList().size/3, 0)
        indicatorReccomend.setViewPager(binding.viewpagerPetseekerRecommend)
        indicatorReccomend.createIndicators(getRecommendList().size, 0)

        binding.viewpagerPetseekerList.adapter = boardAdapterPetList
        binding.viewpagerPetseekerList.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewpagerPetseekerList.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                indicatorList.animatePageSelected(position)
                Toast.makeText(requireContext(), "${position + 1} 페이지 선택됨", Toast.LENGTH_SHORT).show()
            }
        })

        binding.viewpagerPetseekerRecommend.adapter = boardAdapterRecommend
        binding.viewpagerPetseekerRecommend.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewpagerPetseekerRecommend.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                indicatorReccomend.animatePageSelected(position)
                Toast.makeText(requireContext(), "${position + 1} 페이지 선택됨", Toast.LENGTH_SHORT).show()
            }
        })
        binding.btnSurvey.setOnClickListener {
            Toast.makeText(requireContext(), "산책 버튼 눌림", Toast.LENGTH_SHORT).show()
        }

        val SpinnerItems = arrayOf("센터1","센터2","센터3")
        val adapterSpinner = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, SpinnerItems)
        binding.spinnerPetseeker.adapter = adapterSpinner
        binding.spinnerPetseeker.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작하게 됩니다.
                when (position) {
                    0 -> {
                    }
                    1 -> {
                    }
                    //...
                    else -> {
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        return binding.getRoot()
    }

    private fun getPetList(): ArrayList<HomePetseekerListData> {
        val petList = ArrayList<HomePetseekerListData>()

        petList.add(
            HomePetseekerListData(
                "https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg", "a", "수컷", "흰색, 갈색", "a",
            )
        )
        petList.add(
            HomePetseekerListData(
                "https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg", "믹스견", "암컷", "흰색", "b",
            )
        )
        petList.add(
            HomePetseekerListData(
                "https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg", "믹스견", "수컷", "갈색", "c"
            )
        )
        petList.add(
            HomePetseekerListData(
                "https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg", "믹스견", "수컷", "흰색, 갈색", "d",
            )
        )
        petList.add(
            HomePetseekerListData(
                "https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg", "믹스견", "암컷", "흰색", "e",
            )
        )
        petList.add(
            HomePetseekerListData(
                "https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg", "믹스견", "수컷", "갈색", "f"
            )
        )
        return petList
    }

    private fun getRecommendList(): ArrayList<HomePetseekerRecommendData> {
        val recommendList = ArrayList<HomePetseekerRecommendData>()

        recommendList.add(HomePetseekerRecommendData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg", "한국 고양이", "수컷", "흰색, 갈색", "사람을 좋아하고 얌전함"))
        recommendList.add(HomePetseekerRecommendData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg", "한국 고양이", "수컷", "흰색, 갈색", "사람을 좋아하고 얌전함"))

        return recommendList
    }
}