package com.supercat.notes.data

import android.util.Log
import ru.geekbrains.AndroidKotlin.data.Color
import ru.geekbrains.AndroidKotlin.data.Note
import ru.geekbrains.AndroidKotlin.data.NotesRepository
import ru.geekbrains.AndroidKotlin.data.db.FireBaseDb

object NotesRepositoryImpl : NotesRepository {

    // Генератор id
    val noteId: Long get() = (0 until Long.MAX_VALUE).random().toLong()
    val fireBaseDb =  FireBaseDb()
    private val notes: MutableList<Note> = mutableListOf(
            Note(
                    id = 1,
                    groupName = "Kotlin Code",
                    title = "Android Data Binding",
                    note = "buttonId.setOnClickListener { textId.text = \"Ok\" }",
                    url = "https://startandroid.ru/ru/courses/architecture-components/27-course/architecture-components/551-urok-18-data-binding-osnovy.html",
                    color = Color.BLUE
            ),
            Note(
                    id = 2,
                    groupName = "Kotlin Code",
                    title = "Аннотация @JvmOverloads",
                    note = "Аннотация информирует компилятор, что следует создать конструктор на основе предыдущего с дополнительным параметром с значением по умолчанию.",
                    url = "http://developer.alexanderklimov.ru/android/kotlin/jvmoverloads.php",
                    color = Color.GREEN
            ),
            Note(
                    id = 3,
                    groupName = "XML",
                    title = "tools:listitem / listheader / listfooter",
                    note = "настроить внешний вид компонентов на основе AdapterView - ListView, GridView, ExpandableListView >> tools:listitem=\"@layout/item_note\"",
                    url = "http://developer.alexanderklimov.ru/android/studio/tools.php",
                    color = Color.PINK
            ),
            Note(
                    id = 4,
                    groupName = "",
                    title = "Моя 4 заметка",
                    note = "Kotlin очень краткий, но при этом выразительный язык",
                    url = "",
                    color = Color.RED
            ),
            Note(
                    id = 5,
                    groupName = "",
                    title = "Моя 5 заметка",
                    note = "Kotlin очень краткий, но при этом выразительный язык",
                    url = "",
                    color = Color.VIOLET
            ),
            Note(
                    id = 6,
                    groupName = "",
                    title = "Моя 6 заметка",
                    note = "Kotlin очень краткий, но при этом выразительный язык",
                    url = "",
                    color = Color.YELLOW
            ),
            Note(
                    id = 7,
                    groupName = "",
                    title = "Моя 7 заметка",
                    note = "Kotlin очень краткий, но при этом выразительный язык",
                    url = "",
                    color = Color.WHITE
            )
    )

    override fun getAllNotes(): List<Note> {
        return notes
    }

    override fun insert(note: Note) {
        // добавить в начало
        //notes.add(0, note)
        fireBaseDb.insert(note)
    }

    override fun update(newNote: Note) {
        fireBaseDb.update(newNote)
    }

    override fun delete(note: Note) {
        fireBaseDb.delete(note)
    }
}
