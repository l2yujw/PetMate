package com.example.petmate.ui.myinfo.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petmate.R
import com.example.petmate.core.decorator.HorizontalDividerItemDecorator
import com.example.petmate.core.decorator.VerticalDividerItemDecorator
import com.example.petmate.core.network.NetworkClient
import com.example.petmate.core.util.GlobalUserId
import com.example.petmate.core.util.OnItemClickListener
import com.example.petmate.databinding.FragmentMyinfBinding
import com.example.petmate.remote.api.myinfo.MyInfoService
import com.example.petmate.remote.response.myinfo.MyInfoImageResponse
import com.example.petmate.remote.response.myinfo.MyInfoUserListResponse
import com.example.petmate.remote.response.myinfo.MyInfoUserResponse
import com.example.petmate.ui.myinfo.activity.MyInfoPhotoActivity
import com.example.petmate.ui.myinfo.adapter.MyInfoImageAdapter
import com.example.petmate.ui.myinfo.adapter.MyInfoUserListAdapter
import com.example.petmate.ui.myinfo.data.MyInfoUsersData
import com.example.petmate.util.GlideHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyInfoFragment : Fragment() {

    private lateinit var binding: FragmentMyinfBinding
    private val TAG = "MyInfoFragment"  // TAG 수정

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyinfBinding.inflate(inflater)

        val userIdx = GlobalUserId.getUserId()

        setupRecyclerViews()
        requestUserInfo(userIdx)
        requestPicList(userIdx)
        requestUserList(userIdx)

        binding.btnPost.setOnClickListener {
            startActivity(Intent(requireContext(), MyInfoPhotoActivity::class.java))
        }

        return binding.root
    }

    private fun setupRecyclerViews() {
        // RecyclerView 설정 코드 중복 제거
        binding.rvMyinfPictureList.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            addItemDecoration(VerticalDividerItemDecorator(2))
            addItemDecoration(HorizontalDividerItemDecorator(2))
        }

        binding.rvMyinfUserList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun requestUserList(userIdx: Int) {
        val service = NetworkClient.getRetrofit().create(MyInfoService::class.java)
        service.getUsers(userIdx).enqueue(object : Callback<MyInfoUserListResponse> {
            override fun onResponse(call: Call<MyInfoUserListResponse>, response: Response<MyInfoUserListResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { result ->
                        if (result.code == 200) {
                            val userList = result.result.map { MyInfoUsersData(it.image) }
                            val userListAdapter = MyInfoUserListAdapter(ArrayList(userList))

                            binding.rvMyinfUserList.adapter = userListAdapter
                            binding.btnPost.visibility = View.VISIBLE
                            userListAdapter.notifyDataSetChanged()
                        } else {
                            Log.d(TAG, "onResponse: 서버 응답 오류")
                        }
                    }
                } else {
                    Log.d(TAG, "onResponse 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MyInfoUserListResponse>, t: Throwable) {
                Log.d(TAG, "onFailure 에러: ${t.message}")
            }
        })
    }

    private fun requestUserInfo(userIdx: Int) {
        val service = NetworkClient.getRetrofit().create(MyInfoService::class.java)
        service.getUser(userIdx).enqueue(object : Callback<MyInfoUserResponse> {
            override fun onResponse(call: Call<MyInfoUserResponse>, response: Response<MyInfoUserResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { result ->
                        if (result.code == 200) {
                            val userInfo = result.result[0]
                            binding.tvMyinfUserName.text = userInfo.name
                            binding.tvMyinfNickname.text = userInfo.name
                            binding.tvMyinfLineInf.text = userInfo.lineInfo

                            // 이미지 로딩 추출
                            GlideHelper.loadImage(binding.imgMyinfUser, userInfo.image)
                        } else {
                            Log.d(TAG, "onResponse: 서버 응답 오류")
                        }
                    }
                } else {
                    Log.d(TAG, "onResponse 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MyInfoUserResponse>, t: Throwable) {
                Log.d(TAG, "onFailure 에러: ${t.message}")
            }
        })
    }

    private fun requestPicList(userIdx: Int) {
        val service = NetworkClient.getRetrofit().create(MyInfoService::class.java)
        service.getPictures(userIdx).enqueue(object : Callback<MyInfoImageResponse> {
            override fun onResponse(call: Call<MyInfoImageResponse>, response: Response<MyInfoImageResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { result ->
                        if (result.code == 200) {
                            val picListAdapter = MyInfoImageAdapter(result.result)

                            binding.rvMyinfPictureList.adapter = picListAdapter

                            picListAdapter.setItemClickListener(object : OnItemClickListener {
                                override fun onClick(v: View, position: Int) {
                                    val bundle = Bundle().apply {
                                        putParcelableArrayList("Postdata", result.result)
                                    }
                                    findNavController().navigate(R.id.action_myinfFragment_to_myinfPostFragment, bundle)
                                }
                            })

                            picListAdapter.notifyDataSetChanged()
                        } else {
                            Log.d(TAG, "onResponse: 서버 응답 오류")
                        }
                    }
                } else {
                    Log.d(TAG, "onResponse 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MyInfoImageResponse>, t: Throwable) {
                Log.d(TAG, "onFailure 에러: ${t.message}")
            }
        })
    }
}
