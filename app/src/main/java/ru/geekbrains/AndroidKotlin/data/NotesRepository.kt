package ru.geekbrains.AndroidKotlin.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.channels.ReceiveChannel
import ru.geekbrains.AndroidKotlin.model.User

interface NotesRepository {
    fun selectAll(): ReceiveChannel<NotesResult>
    fun getCurrentUser (): User?
    suspend fun insert(note: Note)
    suspend fun update(note: Note)
    suspend fun delete(note: Note)
}
