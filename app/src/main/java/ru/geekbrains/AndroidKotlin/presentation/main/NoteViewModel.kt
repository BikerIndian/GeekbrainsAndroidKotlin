package ru.geekbrains.AndroidKotlin.presentation.main

import androidx.lifecycle.ViewModel
import ru.geekbrains.AndroidKotlin.data.Color
import ru.geekbrains.AndroidKotlin.data.Note
import ru.geekbrains.AndroidKotlin.data.NotesRepository

class NoteViewModel(var note: Note?, var repository : NotesRepository) : ViewModel() {

    fun updateNote(text: String) {
        note = (note ?: generateNote()).copy(note = text)
    }

    fun updateTitle(text: String) {
        note = (note ?: generateNote()).copy(title = text)
    }

    fun setColor(color: Color) {
        note = (note ?: generateNote()).copy(color = color)
    }

    private fun generateNote(): Note {
        return Note()
    }

    // обновить / изменить заметку
    fun update(){
        note?.let {
            repository.update(it)
        }
    }

    // удалить заметку
    fun delete(){
        note?.let {
            repository.delete(it)
        }
    }
}
