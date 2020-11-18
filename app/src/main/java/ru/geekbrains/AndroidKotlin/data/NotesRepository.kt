package com.supercat.notes.data

interface NotesRepository {
    fun getAllNotes(): List<Note>
}
