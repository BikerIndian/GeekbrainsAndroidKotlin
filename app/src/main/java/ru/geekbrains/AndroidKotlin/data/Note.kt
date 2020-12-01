package ru.geekbrains.AndroidKotlin.data

import android.content.Context
import android.os.Parcelable
import androidx.core.content.ContextCompat
import com.supercat.notes.data.NotesRepositoryImpl.noteId
import kotlinx.android.parcel.Parcelize
import ru.geekbrains.AndroidKotlin.R
import ru.geekbrains.AndroidKotlin.data.Color.*

// add in gradle -> id 'kotlin-android'
@Parcelize
data class Note(
        val id: Long = noteId,
        val title: String = "",
        val note: String = "",
        val color: Color = getRandomColor(),
) : Parcelable


enum class Color {
    WHITE,
    YELLOW,
    GREEN,
    BLUE,
    RED,
    VIOLET,
    PINK;

    fun getColorRes(): Int = when (this) {
        WHITE -> R.color.color_white
        VIOLET -> R.color.color_violet
        YELLOW -> R.color.color_yellow
        RED -> R.color.color_red
        PINK -> R.color.color_pink
        GREEN -> R.color.color_green
        BLUE -> R.color.color_blue
    }
}

fun Color.mapToColor(context: Context): Int {
    return ContextCompat.getColor(context, getColorRes())
}

// Генератор цветов
fun getRandomColor(): Color {
    return values()[(0 until values().size - 1).random()]
}
