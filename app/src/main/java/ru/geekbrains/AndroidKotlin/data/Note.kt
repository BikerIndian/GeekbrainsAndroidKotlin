package ru.geekbrains.AndroidKotlin.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// add in gradle -> id 'kotlin-android'
@Parcelize
data class Note(
    val groupName: String,
    val title: String,
    val note: String,
    val url: String,
    val color: Int = 0x0000000,
) : Parcelable
