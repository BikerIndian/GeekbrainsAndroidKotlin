package ru.geekbrains.AndroidKotlin.data

import androidx.lifecycle.LiveData
import ru.geekbrains.AndroidKotlin.data.db.RemoteDataProvider
import ru.geekbrains.AndroidKotlin.model.User

class NotesRepositoryImpl(val remoteProvider : RemoteDataProvider) : NotesRepository {

    // Генератор id
    val noteId: Long get() = (0 until Long.MAX_VALUE).random().toLong()
    private val notes: MutableList<Note> = mutableListOf()

    override fun selectAll(): LiveData<NotesResult> {
        return remoteProvider.selectAll()
    }

    override fun getCurrentUser(): User? {
        return remoteProvider.getCurrentUser()
    }

    override fun insert(note: Note) {
        remoteProvider.insert(note)
    }

    override fun update(newNote: Note) {
        remoteProvider.update(newNote)
    }

    override fun delete(note: Note) {
        remoteProvider.delete(note)
    }
}