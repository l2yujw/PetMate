package com.example.petmate.myinf

import android.content.Intent
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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petmate.GlobalUserIdx
import com.example.petmate.HorizontalItemDecorator
import com.example.petmate.OnItemClickListener
import com.example.petmate.R
import com.example.petmate.RightItemDecorator
import com.example.petmate.VerticalItemDecorator
import com.example.petmate.community.CommunityInterface
import com.example.petmate.community.CommunityInterfaceResponse
import com.example.petmate.community.CommunityPopularAdapter
import com.example.petmate.community.CommunityPopularData
import com.example.petmate.databinding.FragmentMyinfBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.Date
import kotlin.concurrent.thread

class MyinfFragment : Fragment() {

    lateinit var binding: FragmentMyinfBinding
    private val TAG = "MyinfFragment123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyinfBinding.inflate(inflater)

        val userIdx = GlobalUserIdx.getUserIdx()

        requestUserInfo(userIdx)

        var adapterMyinfPicList = MyinfPicListAdapter(getPicList())
        adapterMyinfPicList.notifyDataSetChanged()
        var adapterMyinfUserList = MyinfUserListAdapter(getUserList())
        adapterMyinfUserList.notifyDataSetChanged()

        binding.rcvMyinfPicList.adapter = adapterMyinfPicList
        binding.rcvMyinfPicList.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rcvMyinfPicList.addItemDecoration(VerticalItemDecorator(2))
        binding.rcvMyinfPicList.addItemDecoration(HorizontalItemDecorator(2))

        adapterMyinfPicList.setItemClickListener(object : OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                v.findNavController().navigate(R.id.action_myinfFragment_to_myinfPostFragment)
            }
        })

        binding.rcvMyinfUserList.adapter = adapterMyinfUserList
        binding.rcvMyinfUserList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.btnPost.setOnClickListener {
            val intent = Intent(requireContext(), MyinfPhotoActivity::class.java)
            startActivity(intent)
        }

        requestPicList(userIdx)
        requestUserList(userIdx)

        return binding.getRoot()
    }

    private fun requestUserList(userIdx: Int) {
//고정
        val retrofit = Retrofit.Builder()
            .baseUrl("http://13.124.16.204:3000/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        //

        val service = retrofit.create(MyinfInterface::class.java);
        service.getUserList(userIdx).enqueue(object : Callback<MyinfUserListInerfaceResponse> {

            override fun onResponse(call: Call<MyinfUserListInerfaceResponse>, response: retrofit2.Response<MyinfUserListInerfaceResponse>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    val result: MyinfUserListInerfaceResponse? = response.body()
                    Log.d(TAG, "PopularList onResponse 성공: " + result?.toString());


                    when (result?.code) {
                        200 -> {
                            val list = ArrayList<MyinfUserListData>()
                            for(item in result.result){
                                list.add(MyinfUserListData(item.image))
                            }

                            val adapterMyinfUserList = MyinfUserListAdapter(list)

                            binding.rcvMyinfUserList.adapter = adapterMyinfUserList
                            binding.rcvMyinfUserList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

                            adapterMyinfUserList.notifyDataSetChanged()

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

            override fun onFailure(call: Call<MyinfUserListInerfaceResponse>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d(TAG, "onFailure 에러: " + t.message.toString());
            }
        })
    }

    private fun requestUserInfo(userIdx: Int) {
        //고정
        val retrofit = Retrofit.Builder()
            .baseUrl("http://13.124.16.204:3000/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        //

        val service = retrofit.create(MyinfInterface::class.java);
        service.getUserInfo(userIdx).enqueue(object : Callback<MyinfUserInterfaceResponse> {

            override fun onResponse(call: Call<MyinfUserInterfaceResponse>, response: retrofit2.Response<MyinfUserInterfaceResponse>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    val result: MyinfUserInterfaceResponse? = response.body()
                    Log.d(TAG, "PopularList onResponse 성공: " + result?.toString());


                    when (result?.code) {
                        200 -> {
                            val item = result.result[0]

                            binding.nameMyinfUser.text = item.name
                            val encodeByte = Base64.decode(item.image, Base64.NO_WRAP)
                            val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
                            binding.imgMyinfUser.setImageBitmap(bitmap)
                            binding.nickNameMyinfUser.text = item.nickName
                            binding.lineInfoMyinfUser.text = item.lineInfo
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

            override fun onFailure(call: Call<MyinfUserInterfaceResponse>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d(TAG, "onFailure 에러: " + t.message.toString());
            }
        })
    }

    private fun requestPicList(userIdx: Int) {
//고정
        val retrofit = Retrofit.Builder()
            .baseUrl("http://13.124.16.204:3000/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        //

        val service = retrofit.create(MyinfInterface::class.java);
        service.getPicList(userIdx).enqueue(object : Callback<MyinfPicInterfaceResponse> {

            override fun onResponse(call: Call<MyinfPicInterfaceResponse>, response: retrofit2.Response<MyinfPicInterfaceResponse>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    val result: MyinfPicInterfaceResponse? = response.body()
                    Log.d(TAG, "PopularList onResponse 성공: " + result?.toString());


                    when (result?.code) {
                        200 -> {
                            val list = ArrayList<MyinfPicListData>()
                            val resultlist = result.result
                            thread {
                                for (item in result.result) {
                                    list.add(MyinfPicListData(item.image))
                                }
                            }.join()

                            val adapterMyinfPicList = MyinfPicListAdapter(list)

                            binding.rcvMyinfPicList.adapter = adapterMyinfPicList
                            binding.rcvMyinfPicList.layoutManager = GridLayoutManager(requireContext(), 3)
                            binding.rcvMyinfPicList.addItemDecoration(VerticalItemDecorator(2))
                            binding.rcvMyinfPicList.addItemDecoration(HorizontalItemDecorator(2))

                            adapterMyinfPicList.setItemClickListener(object : OnItemClickListener {
                                override fun onClick(v: View, position: Int) {
                                    val bundle = Bundle()
                                    bundle.putParcelable("Postdata", resultlist[position])
                                    Log.d(TAG, "onClick bundle item : ${resultlist[position]}")
                                    findNavController().navigate(R.id.action_communityFragment_to_communityPostFragment, bundle)
                                }
                            })
                            adapterMyinfPicList.notifyDataSetChanged()
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

            override fun onFailure(call: Call<MyinfPicInterfaceResponse>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d(TAG, "onFailure 에러: " + t.message.toString());
            }
        })
    }

    private fun getPicList(): ArrayList<MyinfPicListData> {
        val picList = ArrayList<MyinfPicListData>()

        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        picList.add(MyinfPicListData("https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))

        return picList
    }

    private fun getUserList(): ArrayList<MyinfUserListData> {
        val userList = ArrayList<MyinfUserListData>()

        userList.add(MyinfUserListData("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg"))
        userList.add(MyinfUserListData("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg"))
        userList.add(MyinfUserListData("https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg"))

        return userList
    }
}