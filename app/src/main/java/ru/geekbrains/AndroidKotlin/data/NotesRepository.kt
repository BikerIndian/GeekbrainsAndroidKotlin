package ru.geekbrains.AndroidKotlin.data

interface NotesRepository {
    fun getAllNotes(): List<Note>
    fun insert(note: Note)
    fun update(note: Note)
    fun delete(note: Note)
}
