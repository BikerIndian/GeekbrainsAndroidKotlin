package ru.geekbrains.AndroidKotlin.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.AndroidKotlin.data.Note
import ru.geekbrains.AndroidKotlin.data.mapToColor
import ru.geekbrains.AndroidKotlin.databinding.ItemNoteBinding

// RecyclerView
class MainAdapter(private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<MainAdapter.NoteViewHolder>() {
    var notes: List<Note> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(parent)
    }

    override fun getItemCount() = notes.size
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int): Unit {
        holder.bind(notes[position])
    }

    inner class NoteViewHolder(
            parent: ViewGroup,
            private val binding: ItemNoteBinding = ItemNoteBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
            )
    ) : RecyclerView.ViewHolder(
            binding.root
    ) {

        fun bind(note: Note) {
            with(binding) {
                titleId.text = note.title
                bodyId.text = note.note
                noteLayoutId.background.setTint(note.color.mapToColor(itemView.context))
                root.setOnClickListener { onItemClickListener.onItemClick(note) } // редактирования заметки
            }
        }
    }
}

fun interface OnItemClickListener {
    fun onItemClick(note: Note)
}




