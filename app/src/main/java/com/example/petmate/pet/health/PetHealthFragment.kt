package com.example.petmate.pet.health

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petmate.R
import com.example.petmate.Tool
import com.example.petmate.VerticalItemDecorator
import com.example.petmate.databinding.FragmentPetHealthBinding
import retrofit2.Call
import retrofit2.Callback
import java.util.Calendar

class PetHealthFragment : Fragment() {

    lateinit var binding: FragmentPetHealthBinding
    var petIdx = 0
    private val TAG = "PetHealthFragment123"


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPetHealthBinding.inflate(inflater)

        petIdx = arguments?.getInt("petIdx") ?: 0
        Log.d(TAG, "petIdx : $petIdx")

        requestInfo(petIdx)

        binding.tvHealthVaccination.setOnClickListener{
            val dlg = AlertDialog.Builder(findNavController().context)
            dlg.setTitle("접종").setMessage("예방접종 하셨나요?")
                .setPositiveButton("예"
                ) { dialog, id ->
                    val cal = Calendar.getInstance()
                    val data = DatePickerDialog.OnDateSetListener { view, year, month, day ->
                        binding.tvHealthVaccination.text = "${cal.get(Calendar.YEAR)}-${cal.get(Calendar.MONTH)}-${cal.get(Calendar.DAY_OF_MONTH)}"
                    }
                    DatePickerDialog(findNavController().context, data, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
                        .apply {
                            //1개월 간격으로 접종
                            cal.add(Calendar.MONTH, 1)
                        }
                        .show()
                }
                .setNegativeButton("아니요"
                ) { dialog, id ->
                    Toast.makeText(findNavController().context, "접종 후에 날짜를 변경할 수 있습니다.", Toast.LENGTH_SHORT).show()
                }
            dlg.show()
        }

        binding.tvHealthVaccination.setOnClickListener{
            val dlg = AlertDialog.Builder(findNavController().context)
            dlg.setTitle("복용").setMessage("구충제 복용 하셨나요?")
                .setPositiveButton("예"
                ) { dialog, id ->
                    val cal = Calendar.getInstance()
                    val data = DatePickerDialog.OnDateSetListener { view, year, month, day ->
                        binding.tvHealthVaccination.text = "${cal.get(Calendar.YEAR)}-${cal.get(Calendar.MONTH)}-${cal.get(Calendar.DAY_OF_MONTH)}"
                    }
                    DatePickerDialog(findNavController().context, data, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
                        .apply {
                            //2개월 간격으로 복용
                            cal.add(Calendar.MONTH, 2)
                        }
                        .show()
                }
                .setNegativeButton("아니요"
                ) { _, _ ->
                    Toast.makeText(findNavController().context, "접종 후에 날짜를 변경할 수 있습니다.", Toast.LENGTH_SHORT).show()
                }
            dlg.show()
        }

        return binding.root
    }

    private fun requestInfo(petIdx: Int) {

        val retrofit = Tool.getRetrofit()

        val service = retrofit.create(PetHealthInterface::class.java);
        service.getInfo(petIdx).enqueue(object : Callback<PetHealthInterfaceResponse> {

            override fun onResponse(call: Call<PetHealthInterfaceResponse>, response: retrofit2.Response<PetHealthInterfaceResponse>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    val result: PetHealthInterfaceResponse? = response.body()
                    Log.d(TAG, "onResponse 성공: " + result?.toString())

                    when (result?.code) {
                        200 -> {
                            val item = result.result[0]
                            val reqKcal = calculReqKcal(item)
                            val reqQuantity = (reqKcal / 3.7).toInt() // 성견 사료g당 평균 칼로리인 3.7kcal로 임의 설정// 출처 : 헬스경향(http://www.k-health.com)

                            binding.petHealthPetname.text = item.name
                            binding.petHealthPetdescription.text = "${item.category} ${item.species}"
                            binding.petHealthPetage.text = "${item.age}살"
                            binding.petHealthSexImage.setImageResource(getSexImage(item.gender))
                            binding.tvHealthVaccination.text = item.vaccination
                            binding.tvHealthHelminthic.text = item.helminthic
                            binding.tvHealthWeight.text = item.weight.toString()
                            binding.tvHealthWeightCondition.text = "정상"
                            binding.tvHealthQuantity.text = "${reqQuantity}g"
                            binding.tvHealthKcal.text = "$reqKcal"
                            binding.petHealthKnow.text = "TIP: 치석 제거에는 껌 보다는 양치가 효과적이에요"


                            val encodeByte = Base64.decode(item.image, Base64.NO_WRAP)
                            val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
                            binding.petHealthImage.setImageBitmap(bitmap)

                            binding.rcvHealthRecord.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

                            //item 간격 결정
                            binding.rcvHealthRecord.addItemDecoration(VerticalItemDecorator(10))
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

            override fun onFailure(call: Call<PetHealthInterfaceResponse>, t: Throwable) {
                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                Log.d(TAG, "onFailure 에러: " + t.message.toString())
            }
        })
    }

    // 성별
    private fun getSexImage(sex: String): Int {
        return when (sex) {
            "수컷", "M" -> R.drawable.sex_male
            "암컷", "F" -> R.drawable.sex_female
            else -> R.drawable.sex_male
        }
    }

    private fun calculReqKcal(item: PetHealthInterfaceResponseResult): Int {
        val RER = (item.weight * 30) + 70
        var k = 1.6
        if (item.age < 1) {
            k = 3.0
        } else if (item.age <= 2) {
            //성견 평균 상수값
            //체중감량 1, 과체중,노령견 1.2~1.4, 중성화 1.6, 비 중성화 1.8 인데
            //따로 체크받는게 없어서 임의로 중간값을 사용함
            k = 1.5
        }
        return (k * RER).toInt()
    }
}