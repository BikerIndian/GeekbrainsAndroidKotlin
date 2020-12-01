package ru.geekbrains.AndroidKotlin.ant

import android.content.Context

fun Context.dip(value: Int) = (value * resources.displayMetrics.density).toInt()
fun Context.dip(value: Float) = (value * resources.displayMetrics.density).toInt()