package ru.geekbrains.AndroidKotlin.data.db

import androidx.lifecycle.LiveData
import ru.geekbrains.AndroidKotlin.data.Note
import ru.geekbrains.AndroidKotlin.data.NotesResult
import ru.geekbrains.AndroidKotlin.model.User

interface RemoteDataProvider {

    fun getCurrentUser (): User?
    fun selectAll(): LiveData<NotesResult>
    fun insert(note: Note): LiveData<NotesResult>
    fun update(note: Note): LiveData<NotesResult>
    fun delete(note: Note): LiveData<NotesResult>
}