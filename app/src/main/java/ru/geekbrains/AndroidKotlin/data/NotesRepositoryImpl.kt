package com.supercat.notes.data

import ru.geekbrains.AndroidKotlin.data.Note
import ru.geekbrains.AndroidKotlin.data.NotesRepository

object NotesRepositoryImpl : NotesRepository {
    private val notes: List<Note> = listOf(
            Note(
                    groupName = "Kotlin Code",
                    title = "Android Data Binding",
                    note = "buttonId.setOnClickListener { textId.text = \"Ok\" }",
                    url = "https://startandroid.ru/ru/courses/architecture-components/27-course/architecture-components/551-urok-18-data-binding-osnovy.html",
                    color = 0xfff06292.toInt()
            ),
            Note(
                    groupName = "Kotlin Code",
                    title = "Аннотация @JvmOverloads",
                    note = "Аннотация информирует компилятор, что следует создать конструктор на основе предыдущего с дополнительным параметром с значением по умолчанию.",
                    url = "http://developer.alexanderklimov.ru/android/kotlin/jvmoverloads.php",
                    color = 0xff9575cd.toInt()
            ),
            Note(
                    groupName = "XML",
                    title = "tools:listitem / listheader / listfooter",
                    note = "настроить внешний вид компонентов на основе AdapterView - ListView, GridView, ExpandableListView >> tools:listitem=\"@layout/item_note\"",
                    url = "http://developer.alexanderklimov.ru/android/studio/tools.php",
                    color = 0xff64b5f6.toInt()
            ),
            Note(
                    groupName = "",
                    title = "Моя 4 заметка",
                    note = "Kotlin очень краткий, но при этом выразительный язык",
                    url = "",
                    color = 0xff4db6ac.toInt()
            ),
            Note(
                    groupName = "",
                    title = "Моя 5 заметка",
                    note = "Kotlin очень краткий, но при этом выразительный язык",
                    url = "",
                    color = 0xffb2ff59.toInt()
            ),
            Note(
                    groupName = "",
                    title = "Моя 6 заметка",
                    note = "Kotlin очень краткий, но при этом выразительный язык",
                    url = "",
                    color = 0xffffeb3b.toInt()
            ),
            Note(
                    groupName = "",
                    title = "Моя 7 заметка",
                    note = "Kotlin очень краткий, но при этом выразительный язык",
                    url = "",
                    color = 0xffff6e40.toInt()
            )
    )

    override fun getAllNotes(): List<Note> {
        return notes
    }

    override fun addOrReplaceNote(newNote: Note) {
      //
    }
}
