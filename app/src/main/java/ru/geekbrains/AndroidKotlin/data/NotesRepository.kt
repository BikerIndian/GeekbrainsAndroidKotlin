package ru.geekbrains.AndroidKotlin.data

import androidx.lifecycle.LiveData
import ru.geekbrains.AndroidKotlin.model.User

interface NotesRepository {
    fun selectAll(): LiveData<NotesResult>
    fun getCurrentUser (): User?
    fun insert(note: Note)
    fun update(note: Note)
    fun delete(note: Note)
}
