package com.example.petmate.pet.training

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.petmate.GlobalPetIdxList
import com.example.petmate.VerticalItemDecorator
import com.example.petmate.OnItemClickListener
import com.example.petmate.R
import com.example.petmate.HorizontalItemDecorator
import com.example.petmate.databinding.FragmentPetTrainingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.URL
import kotlin.concurrent.thread

class PetTrainingFragment : Fragment() {

    lateinit var binding: FragmentPetTrainingBinding
    private val TAG = "PetTrainingFragment123"
    lateinit var adapterTrainingList : PetTrainingListAdapter
    lateinit var trainingList : ArrayList<PetTrainingInterfaceResponseResult>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this){
            findNavController().navigate(R.id.action_petTrainingFragment_to_petMainFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPetTrainingBinding.inflate(inflater)
        val petIdx = GlobalPetIdxList.getlist()[0]

        adapterTrainingList = PetTrainingListAdapter(getPetImageList())
        adapterTrainingList.notifyDataSetChanged()

        binding.rcvPetTrainingList.adapter = adapterTrainingList
        binding.rcvPetTrainingList.layoutManager = GridLayoutManager(requireContext(), 2)


        readAll(petIdx)
        binding.trainingReadAll.setOnClickListener { readAll(petIdx) }
        binding.trainingRead1.setOnClickListener { readWithLevel(1,petIdx) }
        binding.trainingRead2.setOnClickListener { readWithLevel(2,petIdx) }
        binding.trainingRead3.setOnClickListener { readWithLevel(3,petIdx) }
        binding.trainingSearchBtn.setOnClickListener {
            var text = binding.trainingSearch.text.toString()
            readWithname(text,petIdx)
        }






        //item 간격 결정
        binding.rcvPetTrainingList.addItemDecoration(HorizontalItemDecorator(20))
        binding.rcvPetTrainingList.addItemDecoration(VerticalItemDecorator(20))

        adapterTrainingList.setItemClickListener(object : OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val bundle = Bundle()
                bundle.putParcelable("trainingInfo",trainingList[position])
                Log.d(TAG, "PetTrainingFragment : ${trainingList[position]}")
                findNavController().navigate(R.id.action_petTrainingFragment_to_petTrainingDetailFragment,bundle)

            }
        })
        return binding.getRoot()
    }

    private fun getPetImageList(): ArrayList<PetTrainingListData>{
        val petTrainingList = ArrayList<PetTrainingListData>()
        val am = resources.assets
        petTrainingList.add(PetTrainingListData(BitmapFactory.decodeStream(am.open("pet1.jpg")),true))
        petTrainingList.add(PetTrainingListData(BitmapFactory.decodeStream(am.open("pet1.jpg")),false))

        return petTrainingList
    }
    private fun readAll(petIdx: Int) {
//고정
        val retrofit = Retrofit.Builder()
            .baseUrl("http://13.124.16.204:3000/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        //
        val list = ArrayList<PetTrainingListData>()
        adapterTrainingList = PetTrainingListAdapter(list)
        Log.d(TAG, "readAll: 어디감")

        binding.rcvPetTrainingList.adapter = adapterTrainingList
        binding.rcvPetTrainingList.layoutManager = GridLayoutManager(requireContext(), 2)
        val service = retrofit.create(PetTrainingInterface::class.java);
        service.readAll(petIdx).enqueue(object : Callback<PetTrainingInterfaceResponse> {

            override fun onResponse(call: Call<PetTrainingInterfaceResponse>, response: retrofit2.Response<PetTrainingInterfaceResponse>) {
                if (response.isSuccessful) {
                    val result: PetTrainingInterfaceResponse? = response.body()
                    Log.d(TAG, "onResponse 성공: " + result?.toString());

                    when (result?.code) {
                        200 -> {
                            trainingList = result.result
                            Log.d(TAG, "불러온 아이템 갯수 : ${trainingList.size}")
                            thread {
                                for (item in result.result) {
                                    var imageUrl = item.url
                                    imageUrl = imageUrl.split("/watch?v=")[1]
                                    val url = URL("http://img.youtube.com/vi/${imageUrl}/0.jpg")
                                    val image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                                    val isStar = item.petIdx != 0
                                    list.add(PetTrainingListData(image, isStar))
                                }
                            }.join()

                            adapterTrainingList.notifyDataSetChanged()

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
            override fun onFailure(call: Call<PetTrainingInterfaceResponse>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d(TAG, "onFailure 에러: " + t.message.toString());
            }
        })
    }

    private fun readWithLevel(level: Int, petIdx:Int) {
//고정
        val retrofit = Retrofit.Builder()
            .baseUrl("http://13.124.16.204:3000/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        //
        val list = ArrayList<PetTrainingListData>()
        adapterTrainingList = PetTrainingListAdapter(list)
        Log.d(TAG, "readAll: 어디감")

        binding.rcvPetTrainingList.adapter = adapterTrainingList
        binding.rcvPetTrainingList.layoutManager = GridLayoutManager(requireContext(), 2)
        val service = retrofit.create(PetTrainingInterface::class.java);
        service.readWithLevel(level,petIdx).enqueue(object : Callback<PetTrainingInterfaceResponse> {

            override fun onResponse(call: Call<PetTrainingInterfaceResponse>, response: retrofit2.Response<PetTrainingInterfaceResponse>) {
                if (response.isSuccessful) {
                    val result: PetTrainingInterfaceResponse? = response.body()
                    Log.d(TAG, "onResponse 성공: " + result?.toString());

                    when (result?.code) {
                        200 -> {
                            trainingList = result.result
                            Log.d(TAG, "불러온 아이템 갯수 : ${trainingList.size}")
                            thread {
                                for (item in result.result) {
                                    var imageUrl = item.url
                                    imageUrl = imageUrl.split("/watch?v=")[1]
                                    val url = URL("http://img.youtube.com/vi/${imageUrl}/0.jpg")
                                    val image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                                    val isStar = item.petIdx != 0
                                    list.add(PetTrainingListData(image, isStar))
                                }
                            }.join()

                            adapterTrainingList.notifyDataSetChanged()

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
            override fun onFailure(call: Call<PetTrainingInterfaceResponse>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d(TAG, "onFailure 에러: " + t.message.toString());
            }
        })
    }


    private fun readWithname(name:String, petIdx:Int) {
//고정
        val retrofit = Retrofit.Builder()
            .baseUrl("http://13.124.16.204:3000/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        //
        val list = ArrayList<PetTrainingListData>()
        adapterTrainingList = PetTrainingListAdapter(list)
        Log.d(TAG, "readAll: 어디감")

        binding.rcvPetTrainingList.adapter = adapterTrainingList
        binding.rcvPetTrainingList.layoutManager = GridLayoutManager(requireContext(), 2)
        val service = retrofit.create(PetTrainingInterface::class.java);
        service.readWithName(name,petIdx).enqueue(object : Callback<PetTrainingInterfaceResponse> {

            override fun onResponse(call: Call<PetTrainingInterfaceResponse>, response: retrofit2.Response<PetTrainingInterfaceResponse>) {
                if (response.isSuccessful) {
                    val result: PetTrainingInterfaceResponse? = response.body()
                    Log.d(TAG, "onResponse 성공: " + result?.toString());

                    when (result?.code) {
                        200 -> {
                            trainingList = result.result
                            Log.d(TAG, "불러온 아이템 갯수 : ${trainingList.size}")
                            thread {
                                for (item in result.result) {
                                    var imageUrl = item.url
                                    imageUrl = imageUrl.split("/watch?v=")[1]
                                    val url = URL("http://img.youtube.com/vi/${imageUrl}/0.jpg")
                                    val image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                                    val isStar = item.petIdx != 0
                                    list.add(PetTrainingListData(image, isStar))
                                }
                            }.join()

                            adapterTrainingList.notifyDataSetChanged()

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
            override fun onFailure(call: Call<PetTrainingInterfaceResponse>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d(TAG, "onFailure 에러: " + t.message.toString());
            }
        })
    }
}