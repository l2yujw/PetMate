package com.example.petmate.ui.pet.health.fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petmate.R
import com.example.petmate.core.decorator.VerticalDividerItemDecorator
import com.example.petmate.core.network.NetworkClient
import com.example.petmate.databinding.FragmentPetHealthBinding
import com.example.petmate.remote.api.pet.health.PetHealthService
import com.example.petmate.remote.response.pet.health.PetHealthResponse
import com.example.petmate.remote.response.pet.health.PetHealthResult
import com.example.petmate.ui.pet.health.adapter.PetHealthAdapter
import com.example.petmate.ui.pet.health.data.PetHealthData
import retrofit2.Call
import retrofit2.Callback
import java.util.Calendar

class PetHealthFragment : Fragment() {

    lateinit var binding: FragmentPetHealthBinding
    private var petId = 0
    private val TAG = "PetHealthFragment123"
    private val MALE = "수컷"
    private val FEMALE = "암컷"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPetHealthBinding.inflate(inflater)

        petId = arguments?.getInt("petIdx") ?: 0
        Log.d(TAG, "petIdx : $petId")

        setupClickListeners()
        getInfoList()
        requestInfo(petId)

        return binding.root
    }

    private fun setupClickListeners() {
        binding.tvHealthVaccination.setOnClickListener {
            showDatePickerDialog("예방접종 하셨나요?", "접종 후에 날짜를 변경할 수 있습니다.", 1)
        }

        binding.tvHealthHelminthic.setOnClickListener {
            showDatePickerDialog("구충제 복용 하셨나요?", "복용 후에 날짜를 변경할 수 있습니다.", 2)
        }
    }

    private fun showDatePickerDialog(title: String, message: String, monthIncrement: Int) {
        val dlg = AlertDialog.Builder(requireContext())
        dlg.setTitle(title)
            .setPositiveButton("예") { _, _ ->
                val cal = Calendar.getInstance()
                val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
                    binding.tvHealthVaccination.text = "$year-${month + 1}-$day"
                }
                DatePickerDialog(requireContext(), dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).apply {
                    cal.add(Calendar.MONTH, monthIncrement)
                }.show()
            }
            .setNegativeButton("아니요") { _, _ ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }.show()
    }

    private fun requestInfo(petIdx: Int) {
        val retrofit = NetworkClient.getRetrofit()
        val service = retrofit.create(PetHealthService::class.java)
        service.getPetHealthDetails(petIdx).enqueue(object : Callback<PetHealthResponse> {
            override fun onResponse(call: Call<PetHealthResponse>, response: retrofit2.Response<PetHealthResponse>) {
                if (response.isSuccessful) {
                    val result: PetHealthResponse? = response.body()
                    Log.d(TAG, "onResponse 성공: $result")

                    result?.let {
                        if (it.code == 200) {
                            val item = it.result[0]
                            updateUI(item)
                        } else {
                            Log.d(TAG, "onResponse: 에러 발생")
                        }
                    }
                } else {
                    Log.d(TAG, "onResponse 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<PetHealthResponse>, t: Throwable) {
                Log.d(TAG, "onFailure 에러: ${t.message}")
            }
        })
    }

    private fun updateUI(item: PetHealthResult) {
        val reqKcal = calculateReqKcal(item)
        val reqQuantity = (reqKcal / 3.7).toInt()

        binding.tvHealthPetName.text = item.name
        binding.tvHealthPetDescription.text = "${item.category} ${item.species}"
        binding.tvHealthPetAge.text = "${item.age}살"
        binding.ivHealthSexImage.setImageResource(getSexImage(item.gender))
        binding.tvHealthVaccination.text = item.vaccination
        binding.tvHealthHelminthic.text = item.helminthic
        binding.tvHealthWeight.text = item.weight.toString()
        binding.tvHealthWeightCondition.text = "정상"
        binding.tvHealthQuantity.text = "${reqQuantity}g"
        binding.tvHealthKcal.text = "$reqKcal"
        binding.tvHealthKnow.text = "TIP: 치석 제거에는 껌 보다는 양치가 효과적이에요"

        val encodeByte = Base64.decode(item.image, Base64.NO_WRAP)
        val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        binding.ivHealthImage.setImageBitmap(bitmap)

        binding.rcvHealthRecord.adapter = PetHealthAdapter(getRecordList())
    }

    private fun calculateReqKcal(item: PetHealthResult): Int {
        val RER = (item.weight * 30) + 70
        val k = when {
            item.age < 1 -> 3.0
            item.age <= 2 -> 1.5
            else -> 1.6
        }
        return (k * RER).toInt()
    }

    private fun getSexImage(sex: String): Int {
        return when (sex) {
            MALE, "M" -> R.drawable.sex_male
            FEMALE, "F" -> R.drawable.sex_female
            else -> R.drawable.sex_male
        }
    }

    private fun getInfoList() {
        binding.rcvHealthRecord.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcvHealthRecord.addItemDecoration(VerticalDividerItemDecorator(10))
    }

    private fun getRecordList(): ArrayList<PetHealthData> {
        return arrayListOf(
            PetHealthData("2021.05.30"),
            PetHealthData("2022.05.30"),
            PetHealthData("2023.05.30")
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_petHealthFragment_to_petMainFragment)
            }
        })
    }
}
