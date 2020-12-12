package ru.geekbrains.AndroidKotlin.data.db

import androidx.lifecycle.LiveData
import kotlinx.coroutines.channels.ReceiveChannel
import ru.geekbrains.AndroidKotlin.data.Note
import ru.geekbrains.AndroidKotlin.data.NotesResult
import ru.geekbrains.AndroidKotlin.model.User

interface RemoteDataProvider {

    fun getCurrentUser (): User?
    fun selectAll(): ReceiveChannel<NotesResult>
    suspend fun insert(note: Note):  Note
    suspend fun update(note: Note): Note
    suspend fun delete(note: Note): Note
}