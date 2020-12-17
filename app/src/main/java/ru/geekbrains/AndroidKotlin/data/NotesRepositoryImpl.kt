package ru.geekbrains.AndroidKotlin.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.channels.ReceiveChannel
import ru.geekbrains.AndroidKotlin.data.db.RemoteDataProvider
import ru.geekbrains.AndroidKotlin.model.User

class NotesRepositoryImpl(val remoteProvider : RemoteDataProvider) : NotesRepository {

    // Генератор id
    val noteId: Long get() = (0 until Long.MAX_VALUE).random().toLong()
    private val notes: MutableList<Note> = mutableListOf()

    override fun selectAll(): ReceiveChannel<NotesResult> {
        return remoteProvider.selectAll()
    }

    override fun getCurrentUser(): User? {
        return remoteProvider.getCurrentUser()
    }

    override suspend fun insert(note: Note) {
        remoteProvider.insert(note)
    }

    override suspend fun update(newNote: Note) {
        remoteProvider.update(newNote)
    }

    override suspend fun delete(note: Note) {
        remoteProvider.delete(note)
    }
}