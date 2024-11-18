package com.example.petmate.ui.training.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.core.util.OnItemClickListener
import com.example.petmate.databinding.ItemPetTrainingListBinding
import com.example.petmate.ui.training.data.PetTrainingListData
import com.example.petmate.util.GlideHelper

class PetTrainingListAdapter(
    private var trainingItems: ArrayList<PetTrainingListData> // 더 명확한 변수명
) : RecyclerView.Adapter<PetTrainingListAdapter.PetTrainingListViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    // 클릭 리스너 설정
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    // 데이터 갱신 메서드 (효율적인 데이터 변경 처리)
    fun updateTrainingItems(newItems: List<PetTrainingListData>) {
        trainingItems.clear()
        trainingItems.addAll(newItems)
        notifyDataSetChanged() // 전체 데이터 변경 시 RecyclerView 갱신
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetTrainingListViewHolder {
        val binding = ItemPetTrainingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetTrainingListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PetTrainingListViewHolder, position: Int) {
        holder.bind(trainingItems[position])
        holder.itemView.setOnClickListener { view ->
            onItemClickListener?.onClick(view, position)
        }
    }

    override fun getItemCount(): Int = trainingItems.size

    // ViewHolder 클래스
    inner class PetTrainingListViewHolder(private val binding: ItemPetTrainingListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // 개별 아이템 데이터 바인딩
        fun bind(trainingItem: PetTrainingListData) {
            loadImage(trainingItem.imageUrl)
            setStarVisibility(trainingItem.isStar)
        }

        // Glide를 사용해 이미지 로드
        private fun loadImage(imageUrl: String) {
            GlideHelper.loadImage(binding.imgPetTraining, imageUrl)
        }

        // 즐겨찾기 여부에 따라 별 아이콘 표시
        private fun setStarVisibility(isStarred: Boolean) {
            binding.imgPetTrainingStar.visibility = if (isStarred) View.VISIBLE else View.GONE
        }
    }
}
