package com.example.petmate.community

import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.petmate.GlobalPetIdxList
import com.example.petmate.OnItemClickListener
import com.example.petmate.R
import com.example.petmate.RightItemDecorator
import com.example.petmate.VerticalItemDecorator
import com.example.petmate.databinding.FragmentCommunityBinding
import com.example.petmate.home.petowner.HomePetownerInterface
import com.example.petmate.home.petowner.HomePetownerInterfaceResponse
import com.example.petmate.home.petowner.HomePetownerPetlistAdapter
import com.example.petmate.home.petowner.HomePetownerPetlistData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.Date
import kotlin.concurrent.thread

class CommunityFragment : Fragment() {

    lateinit var binding: FragmentCommunityBinding
    private val TAG = "CommunityFragment123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCommunityBinding.inflate(inflater)

        requestPopularList()
        requestBoardList()

        return binding.getRoot()
    }

    private fun requestPopularList(){
        //고정
        val retrofit = Retrofit.Builder()
            .baseUrl("http://13.124.16.204:3000/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        //
        val now = System.currentTimeMillis()
        val date = Date(now);
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        //val today = sdf.format(date).toString()
        val today = "2023-08-28"
        val service = retrofit.create(CommunityInterface::class.java);
        service.getPopularList(today).enqueue(object : Callback<CommunityInterfaceResponse> {

            override fun onResponse(call: Call<CommunityInterfaceResponse>, response: retrofit2.Response<CommunityInterfaceResponse>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    val result: CommunityInterfaceResponse? = response.body()
                    Log.d(TAG, "PopularList onResponse 성공: " + result?.toString());

                    when (result?.code) {
                        200 -> {
                            val list = ArrayList<CommunityPopularData>()
                            val resultlist = result.result
                            thread {
                                for (item in result.result) {
                                    list.add(CommunityPopularData(item.image))
                                }
                            }.join()

                            val adapterCommunityPopular = CommunityPopularAdapter(list)

                            binding.rcvCommunityPopular.adapter = adapterCommunityPopular
                            binding.rcvCommunityPopular.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                            binding.rcvCommunityPopular.addItemDecoration(RightItemDecorator(20))

                            adapterCommunityPopular.setItemClickListener(object : OnItemClickListener {
                                override fun onClick(v: View, position: Int) {
                                    Toast.makeText(v.context, "onClick item", Toast.LENGTH_SHORT).show()
                                    val bundle = Bundle()
                                    bundle.putParcelable("Postdata",resultlist[position])
                                    Log.d(TAG, "onClick bundle item : ${resultlist[position]}")
                                    findNavController().navigate(R.id.action_communityFragment_to_communityPostFragment,bundle)
                                }
                            })


                            adapterCommunityPopular.notifyDataSetChanged()
                        }

                        else -> {
                            Log.d(TAG, "onResponse: ㅈ버그발생 보내는 데이터가 문제임 ")
                        }
                    }

                } else {
                    // 통신이 실패한 경우(응답 코드 3xx, 4xx 등)
                    Log.d(TAG, "onResponse 실패" + response.code())
                }
            }

            override fun onFailure(call: Call<CommunityInterfaceResponse>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d(TAG, "onFailure 에러: " + t.message.toString());
            }
        })

    }

    private fun requestBoardList(){
        //고정
        val retrofit = Retrofit.Builder()
            .baseUrl("http://13.124.16.204:3000/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        //
        val now = System.currentTimeMillis()
        val date = Date(now);
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        //val today = sdf.format(date).toString()
        val today = "2023-08-28"
        val service = retrofit.create(CommunityInterface::class.java);
        service.getBoardList().enqueue(object : Callback<CommunityInterfaceResponse> {

            override fun onResponse(call: Call<CommunityInterfaceResponse>, response: retrofit2.Response<CommunityInterfaceResponse>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    val result: CommunityInterfaceResponse? = response.body()
                    Log.d(TAG, "BoardList onResponse 성공: " + result?.toString());

                    when (result?.code) {
                        200 -> {
                            val resultlist = result.result
                            val list = ArrayList<CommunityBoardData>()
                            for(item in result.result){
                                list.add(CommunityBoardData(item.image))
                            }

                            val adapterCommunityBoard = CommunityBoardAdapter(resultlist)

                            binding.rcvCommunityBoard.adapter = adapterCommunityBoard
                            binding.rcvCommunityBoard.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                            binding.rcvCommunityBoard.addItemDecoration(VerticalItemDecorator(40))

                            adapterCommunityBoard.setItemClickListener(object : OnItemClickListener {
                                override fun onClick(v: View, position: Int) {
                                    val bundle = Bundle()
                                    bundle.putParcelable("Postdata",resultlist[position])
                                    findNavController().navigate(R.id.action_communityFragment_to_communityPostFragment,bundle)
                                }
                            })

                            adapterCommunityBoard.notifyDataSetChanged()
                        }

                        else -> {
                            Log.d(TAG, "onResponse: ㅈ버그발생 보내는 데이터가 문제임 ")
                        }
                    }

                } else {
                    // 통신이 실패한 경우(응답 코드 3xx, 4xx 등)
                    Log.d(TAG, "onResponse 실패" + response.code())
                }
            }

            override fun onFailure(call: Call<CommunityInterfaceResponse>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d(TAG, "onFailure 에러: " + t.message.toString());
            }
        })

    }
    private fun getPopularList(): ArrayList<CommunityPopularData>{
        val popularList = ArrayList<CommunityPopularData>()

        popularList.add(CommunityPopularData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        popularList.add(CommunityPopularData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        popularList.add(CommunityPopularData( "https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))

        return popularList
    }

    private fun getBoardList(): ArrayList<CommunityBoardData>{
        val boardList = ArrayList<CommunityBoardData>()

        boardList.add(CommunityBoardData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        boardList.add(CommunityBoardData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        boardList.add(CommunityBoardData( "https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))

        return boardList
    }
}