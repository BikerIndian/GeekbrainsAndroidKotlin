package ru.geekbrains.AndroidKotlin.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrains.AndroidKotlin.R
import ru.geekbrains.AndroidKotlin.data.Note
import kotlinx.android.synthetic.main.item_note.view.*

// RecyclerView
class MainAdapter : RecyclerView.Adapter<NoteViewHolder>() {
    var notes: List<Note> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount() = notes.size
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int): Unit {

        holder.bind(notes[position])
    }
    fun getNotesList(): List<Note> {
       return this.notes
    }
}

class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(note: Note) {
        itemView.titleId.text = note.title
        itemView.bodyId.text = note.note
        itemView.setBackgroundColor(note.color)
    }
}



