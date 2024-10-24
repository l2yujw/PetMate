package com.example.petmate.ui.myinf.fragment

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.petmate.core.util.GlobalUserIdx
import com.example.petmate.core.decorator.HorizontalItemDecorator
import com.example.petmate.core.util.OnItemClickListener
import com.example.petmate.R
import com.example.petmate.core.network.Tool
import com.example.petmate.core.decorator.VerticalItemDecorator
import com.example.petmate.databinding.FragmentMyinfBinding
import com.example.petmate.remote.api.myinf.MyinfInterface
import com.example.petmate.ui.myinf.activity.MyinfPhotoActivity
import com.example.petmate.remote.response.myinf.MyinfPicInterfaceResponse
import com.example.petmate.remote.response.myinf.MyinfUserInterfaceResponse
import com.example.petmate.remote.response.myinf.MyinfUserListInerfaceResponse
import com.example.petmate.ui.myinf.adapter.MyinfPicListAdapter
import com.example.petmate.ui.myinf.adapter.MyinfUserListAdapter
import com.example.petmate.ui.myinf.data.MyinfUserListData
import retrofit2.Call
import retrofit2.Callback
import kotlin.random.Random

class MyinfFragment : Fragment() {

    lateinit var binding: FragmentMyinfBinding
    private val TAG = "MyinfFragment123"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyinfBinding.inflate(inflater)

        val userIdx = GlobalUserIdx.getUserIdx()

        requestUserInfo(userIdx)

        //binding.rcvMyinfPicList.adapter = adapterMyinfPicList
        binding.rcvMyinfPicList.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rcvMyinfPicList.addItemDecoration(VerticalItemDecorator(2))
        binding.rcvMyinfPicList.addItemDecoration(HorizontalItemDecorator(2))


        binding.rcvMyinfUserList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.btnPost.setOnClickListener {
            val intent = Intent(requireContext(), MyinfPhotoActivity::class.java)
            startActivity(intent)
        }

        requestPicList(userIdx)
        requestUserList(userIdx)

        return binding.root
    }

    private fun requestUserList(userIdx: Int) {

        val retrofit = Tool.getRetrofit()

        val service = retrofit.create(MyinfInterface::class.java)
        service.getUserList(userIdx).enqueue(object : Callback<MyinfUserListInerfaceResponse> {

            override fun onResponse(call: Call<MyinfUserListInerfaceResponse>, response: retrofit2.Response<MyinfUserListInerfaceResponse>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    val result: MyinfUserListInerfaceResponse? = response.body()
                    Log.d(TAG, "PopularList onResponse 성공: " + result?.toString())


                    when (result?.code) {
                        200 -> {
                            val list = ArrayList<MyinfUserListData>()
                            for(item in result.result){
                                list.add(MyinfUserListData(item.image))
                            }
                            binding.btnPost.visibility = View.VISIBLE

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
                Log.d(TAG, "onFailure 에러: " + t.message.toString())
            }
        })
    }

    private fun requestUserInfo(userIdx: Int) {

        val retrofit = Tool.getRetrofit()

        val service = retrofit.create(MyinfInterface::class.java)
        service.getUserInfo(userIdx).enqueue(object : Callback<MyinfUserInterfaceResponse> {

            override fun onResponse(call: Call<MyinfUserInterfaceResponse>, response: retrofit2.Response<MyinfUserInterfaceResponse>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    val result: MyinfUserInterfaceResponse? = response.body()
                    Log.d(TAG, "PopularList onResponse 성공: " + result?.toString())


                    when (result?.code) {
                        200 -> {
                            val item = result.result[0]

                            binding.nameMyinfUser.text = item.name
                            binding.nickNameMyinfUser.text = item.nickName
                            binding.lineInfoMyinfUser.text = item.lineInfo

                            if(item.image.isBlank() || item.image == ""){
                                val tempimagelist = ArrayList<String>()
                                tempimagelist.add("https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg")
                                tempimagelist.add("https://cdn.pixabay.com/photo/2017/02/20/18/03/cat-2083492_640.jpg")
                                tempimagelist.add("https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_640.jpg")
                                Glide.with(binding.imgMyinfUser)
                                    .load(tempimagelist[Random.nextInt(0,3)])                         // 불러올 이미지 URL
                                    .fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                                    .error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                                    .placeholder(R.drawable.background_glide_init)  // 이미지 로딩 시작하기 전에 표시할 이미지
                                    .centerInside()                                 // scaletype
                                    .into(binding.imgMyinfUser)             // 이미지를 넣을 뷰
                            }else if(item.image.endsWith(".png") ||item.image.endsWith(".jpg")||item.image.endsWith(".jpeg") ) {
                                Glide.with(binding.imgMyinfUser)
                                    .load(item.image)                         // 불러올 이미지 URL
                                    .fallback(R.drawable.background_glide_init)                 // 로드할 URL이 비어있을 경우 표시할 이미지
                                    .error(R.drawable.background_glide_init)                    // 로딩 에러 발생 시 표시할 이미지
                                    .placeholder(R.drawable.background_glide_init)  // 이미지 로딩 시작하기 전에 표시할 이미지
                                    .centerInside()                                 // scaletype
                                    .into(binding.imgMyinfUser)             // 이미지를 넣을 뷰
                            }else{
                                val encodeByte = Base64.decode(item.image, Base64.NO_WRAP)
                                val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
                                binding.imgMyinfUser.setImageBitmap(bitmap)
                            }
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
                Log.d(TAG, "onFailure 에러: " + t.message.toString())
            }
        })
    }

    private fun requestPicList(userIdx: Int) {

        val retrofit = Tool.getRetrofit()

        val service = retrofit.create(MyinfInterface::class.java)
        service.getPicList(userIdx).enqueue(object : Callback<MyinfPicInterfaceResponse> {

            override fun onResponse(call: Call<MyinfPicInterfaceResponse>, response: retrofit2.Response<MyinfPicInterfaceResponse>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    val result: MyinfPicInterfaceResponse? = response.body()
                    Log.d(TAG, "PopularList onResponse 성공: " + result?.toString())


                    when (result?.code) {
                        200 -> {
                            val resultlist = result.result

                            val adapterMyinfPicList = MyinfPicListAdapter(resultlist)

                            binding.rcvMyinfPicList.adapter = adapterMyinfPicList
                            binding.rcvMyinfPicList.layoutManager = GridLayoutManager(requireContext(), 3)
                            binding.rcvMyinfPicList.addItemDecoration(VerticalItemDecorator(2))
                            binding.rcvMyinfPicList.addItemDecoration(HorizontalItemDecorator(2))

                            adapterMyinfPicList.setItemClickListener(object : OnItemClickListener {
                                override fun onClick(v: View, position: Int) {
                                    //TODO("bundle 수정해야함 커뮤니티에 있는 post로 넘어가는게 아니라 myinfpost로 이동하는거임")
                                    val bundle = Bundle()
                                    bundle.putParcelableArrayList("Postdata", resultlist)
                                    Log.d(TAG, "onClick bundle item : $resultlist")
                                    findNavController().navigate(R.id.action_myinfFragment_to_myinfPostFragment,bundle)
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
                Log.d(TAG, "onFailure 에러: " + t.message.toString())
            }
        })
    }
}