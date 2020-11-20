package ru.geekbrains.AndroidKotlin.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import ru.geekbrains.AndroidKotlin.R
import ru.geekbrains.AndroidKotlin.data.Note

class AddNoteFragment : Fragment(R.layout.fragment_add_note) {

    companion object {
        const val NOTE_KEY = "Note"

        fun create(note: Note? = null): AddNoteFragment {
            val fragment = AddNoteFragment()
            val arguments = Bundle()
            arguments.putParcelable(NOTE_KEY, note)
            fragment.arguments = arguments

            return fragment
        }
    }
}