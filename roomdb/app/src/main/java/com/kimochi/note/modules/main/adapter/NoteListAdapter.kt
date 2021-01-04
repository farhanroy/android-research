package com.kimochi.note.modules.main.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kimochi.note.R
import com.kimochi.note.databinding.ItemNoteBinding
import com.kimochi.note.db.entity.NoteEntity
import com.kimochi.note.utils.setLayoutBinding
import java.util.*

class NoteListAdapter(private val list : List<NoteEntity> = listOf(),
                      private val clickListener: (NoteEntity) -> Unit) : RecyclerView.Adapter<NoteListAdapter.NoteListVH>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListVH {
        val binding = parent.setLayoutBinding<ItemNoteBinding>(R.layout.item_note, parent)
        return NoteListVH(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: NoteListVH, position: Int) {
        val item = list[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            clickListener(item)
        }
    }

    inner class NoteListVH (var binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item : NoteEntity){
            binding.data = item
        }
    }
}