package ru.geekbrains.AndroidKotlin.presentation.main

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


    private fun generateNote(): Note {
        return Note()
    }

    // обновить / изменить заметку
    fun update(){
        note?.let {
            NotesRepositoryImpl.update(it)
        }
    }

    // удалить заметку
    fun delete(){
        note?.let {
            NotesRepositoryImpl.delete(it)
        }
    }
}
