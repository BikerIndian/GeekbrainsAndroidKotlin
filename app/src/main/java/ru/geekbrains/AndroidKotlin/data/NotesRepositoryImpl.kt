package ru.geekbrains.AndroidKotlin.data

import ru.geekbrains.AndroidKotlin.data.db.FireBaseDb

class NotesRepositoryImpl : NotesRepository {

    // Генератор id
    val noteId: Long get() = (0 until Long.MAX_VALUE).random().toLong()
    val fireBaseDb =  FireBaseDb()
    private val notes: MutableList<Note> = mutableListOf()

    override fun getAllNotes(): List<Note> {
        return notes
    }

    override fun insert(note: Note) {
        fireBaseDb.insert(note)
    }

    override fun update(newNote: Note) {
        fireBaseDb.update(newNote)
    }

    override fun delete(note: Note) {
        fireBaseDb.delete(note)
    }
}