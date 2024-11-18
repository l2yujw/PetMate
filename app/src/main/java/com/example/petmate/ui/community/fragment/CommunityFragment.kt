package com.example.petmate.ui.community.fragment

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.core.decorator.RightDividerItemDecorator
import com.example.petmate.core.decorator.VerticalDividerItemDecorator
import com.example.petmate.databinding.FragmentCommunityBinding
import com.example.petmate.remote.response.community.CommunityResponse
import com.example.petmate.repository.community.CommunityRepository
import com.example.petmate.ui.community.adapter.CommunityBoardAdapter
import com.example.petmate.ui.community.adapter.CommunityPopularAdapter
import com.example.petmate.ui.community.data.CommunityPopularData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class
CommunityFragment : Fragment() {

    private lateinit var binding: FragmentCommunityBinding
    private val repository = CommunityRepository()
    private val TAG = "CommunityFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCommunityBinding.inflate(inflater)

        requestPopularList()
        requestBoardList()

        return binding.root
    }

    private fun requestPopularList() {
        val today = getCurrentDate()
        repository.getPopularList(today, object : Callback<CommunityResponse> {
            override fun onResponse(call: Call<CommunityResponse>, response: Response<CommunityResponse>) {
                handleResponse(response, binding.rvCommunityPopular, true)
            }

            override fun onFailure(call: Call<CommunityResponse>, t: Throwable) {
                Log.e(TAG, "Popular List Fetch Failed: ${t.message}")
            }
        })
    }

    private fun requestBoardList() {
        repository.getBoardList(object : Callback<CommunityResponse> {
            override fun onResponse(call: Call<CommunityResponse>, response: Response<CommunityResponse>) {
                handleResponse(response, binding.rvCommunityBoard, false)
            }

            override fun onFailure(call: Call<CommunityResponse>, t: Throwable) {
                Log.e(TAG, "Board List Fetch Failed: ${t.message}")
            }
        })
    }

    private fun handleResponse(response: Response<CommunityResponse>, recyclerView: RecyclerView, isPopular: Boolean) {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                if (result.code == 200) {
                    // 데이터 처리
                    val adapter = if (isPopular) {
                        val dataList = result.result.map { CommunityPopularData(it.image) }
                        CommunityPopularAdapter(dataList)
                    } else {
                        CommunityBoardAdapter(result.result)
                    }

                    // 리사이클러뷰 설정
                    recyclerView.adapter = adapter
                    if (recyclerView.layoutManager == null) {  // 최초에만 레이아웃 매니저 설정
                        recyclerView.layoutManager = if (isPopular) {
                            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                        } else {
                            LinearLayoutManager(requireContext())
                        }
                    }
                    recyclerView.addItemDecoration(if (isPopular) RightDividerItemDecorator(20) else VerticalDividerItemDecorator(40))
                    adapter.notifyDataSetChanged()
                }
            }
        } else {
            Log.d(TAG, "Response Failed: ${response.code()}")
        }
    }


    private fun getCurrentDate(): String {
        val now = System.currentTimeMillis()
        val date = Date(now)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(date)
    }
}
