package com.example.petmate.ui.pet.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.databinding.ItemPetMainNoteBinding
import com.example.petmate.ui.pet.main.data.PetMainNoteData

class PetMainNoteAdapter(private val noteList: List<PetMainNoteData>) :
    RecyclerView.Adapter<PetMainNoteAdapter.PetMainNoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetMainNoteViewHolder {
        val binding = ItemPetMainNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetMainNoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PetMainNoteViewHolder, position: Int) {
        holder.bind(noteList[position])
    }

    override fun getItemCount(): Int = noteList.size

    inner class PetMainNoteViewHolder(private val binding: ItemPetMainNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PetMainNoteData) {
            binding.mainNoteText.text = item.note
        }
    }
}
