package ru.geekbrains.AndroidKotlin.data.db

import androidx.lifecycle.LiveData
import ru.geekbrains.AndroidKotlin.data.Note

interface RemoteDataProvider {

    fun selectAll(): LiveData<ResultFireBaseDb>
    fun selectById(id: String): LiveData<ResultFireBaseDb>
    fun insert(note: Note): LiveData<ResultFireBaseDb>
    fun update(note: Note): LiveData<ResultFireBaseDb>
    fun delete(note: Note): LiveData<ResultFireBaseDb>
}