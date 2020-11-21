package ru.geekbrains.AndroidKotlin.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.supercat.notes.data.NotesRepositoryImpl
import ru.geekbrains.AndroidKotlin.data.Note

class NoteViewModel(var note: Note?) : ViewModel() {

    fun updateNote(text: String) {
        note = (note ?: generateNote()).copy(note = text)
    }

    fun updateTitle(text: String) {
        note = (note ?: generateNote()).copy(title = text)
    }

    override fun onCleared() {
        super.onCleared()

        note?.let {
            NotesRepositoryImpl.addOrReplaceNote(it)
        }
    }

    private fun generateNote(): Note {
        return Note()
    }
}
