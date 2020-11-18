package com.supercat.notes.data

data class Note(
    val groupName: String,
    val title: String,
    val note: String,
    val url: String,
    val color: Int = 0x0000000,
)
