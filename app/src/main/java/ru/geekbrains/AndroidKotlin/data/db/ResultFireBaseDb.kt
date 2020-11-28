package ru.geekbrains.AndroidKotlin.data.db

sealed class ResultFireBaseDb {
    data class Success<out T>(val data: T) : ResultFireBaseDb()
    data class Error(val error: Throwable) : ResultFireBaseDb()
}