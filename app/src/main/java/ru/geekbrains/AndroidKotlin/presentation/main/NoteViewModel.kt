package ru.geekbrains.AndroidKotlin.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.geekbrains.AndroidKotlin.data.Color
import ru.geekbrains.AndroidKotlin.data.Note
import ru.geekbrains.AndroidKotlin.data.NotesRepository

class NoteViewModel(var note: Note?, var repository : NotesRepository) : ViewModel() {

    private val showErrorLiveData = MutableLiveData<Boolean>()

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
        viewModelScope.launch {
            val noteValue = note ?: return@launch
            try {
            repository.update(noteValue)
            } catch (th: Throwable) {
                showErrorLiveData.value = true
            }
        }
    }

    // удалить заметку
    fun delete(){
        viewModelScope.launch {
            val noteValue = note ?: return@launch

            try {
            repository.delete(noteValue)
            } catch (th: Throwable) {
                showErrorLiveData.value = true
            }
        }
    }
}
