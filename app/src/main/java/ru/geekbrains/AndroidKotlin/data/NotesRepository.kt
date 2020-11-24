package ru.geekbrains.AndroidKotlin.data

interface NotesRepository {
    fun getAllNotes(): List<Note>
}
