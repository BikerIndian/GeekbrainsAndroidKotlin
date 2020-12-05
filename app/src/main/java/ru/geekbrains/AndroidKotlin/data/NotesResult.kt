package ru.geekbrains.AndroidKotlin.data

sealed class NotesResult {
    data class Success<out T>(val data: T) : NotesResult()
    data class Error(val error: Throwable) : NotesResult()
}