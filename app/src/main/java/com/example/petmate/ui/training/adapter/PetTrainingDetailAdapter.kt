package com.example.petmate.ui.training.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.R
import com.example.petmate.ui.training.data.PetTrainingDetailData

class PetTrainingDetailAdapter(
    private val trainingDetails: List<PetTrainingDetailData> // 더 명확한 변수명
) : RecyclerView.Adapter<PetTrainingDetailAdapter.TrainingDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingDetailViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pet_training_detail_ways, parent, false)
        return TrainingDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrainingDetailViewHolder, position: Int) {
        holder.bind(trainingDetails[position])
    }

    override fun getItemCount(): Int = trainingDetails.size

    // ViewHolder 클래스
    inner class TrainingDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val trainingLevelTextView: TextView = itemView.findViewById(R.id.pet_tarining_level)
        private val trainingDetailTextView: TextView = itemView.findViewById(R.id.pet_tarining_detail)

        // 개별 아이템 데이터 바인딩
        fun bind(trainingDetail: PetTrainingDetailData) {
            bindLevel(trainingDetail.level)
            bindDetail(trainingDetail.detail)
        }

        // 트레이닝 레벨 설정
        private fun bindLevel(level: String) {
            trainingLevelTextView.text = level.ifBlank { "Unknown Level" } // 기본 값 설정
        }

        // 트레이닝 세부 정보 설정
        private fun bindDetail(detail: String) {
            trainingDetailTextView.text = detail.ifBlank { "No details available" } // 기본 값 설정
        }
    }
}
