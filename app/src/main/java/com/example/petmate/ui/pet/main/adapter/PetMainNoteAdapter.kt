package com.example.petmate.ui.pet.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petmate.R
import com.example.petmate.ui.pet.main.data.PetMainNoteData

class PetMainNoteAdapter(val itemList: ArrayList<PetMainNoteData>) : RecyclerView.Adapter<PetMainNoteAdapter.PetMainNoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetMainNoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pet_main_note, parent, false)
        return PetMainNoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: PetMainNoteViewHolder, position: Int) {
        holder.note.text = itemList[position].note
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }

    inner class PetMainNoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val note: TextView = itemView.findViewById<TextView>(R.id.main_note_text)
    }
}