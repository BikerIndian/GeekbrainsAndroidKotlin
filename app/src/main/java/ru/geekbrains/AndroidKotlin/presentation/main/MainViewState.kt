package ru.geekbrains.AndroidKotlin.presentation.main

import ru.geekbrains.AndroidKotlin.data.Note

sealed class MainViewState (){
    data class Value(val notes: List<Note>) : MainViewState()
    object EMPTY : MainViewState()
    data class Error(val error: Throwable) : MainViewState()
}