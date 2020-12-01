package ru.geekbrains.AndroidKotlin.data.db

import androidx.lifecycle.LiveData
import ru.geekbrains.AndroidKotlin.data.Note
import ru.geekbrains.AndroidKotlin.model.User

interface RemoteDataProvider {

    fun getCurrentUser (): User?
    fun selectAll(): LiveData<ResultFireBaseDb>
    fun insert(note: Note): LiveData<ResultFireBaseDb>
    fun update(note: Note): LiveData<ResultFireBaseDb>
    fun delete(note: Note): LiveData<ResultFireBaseDb>
}