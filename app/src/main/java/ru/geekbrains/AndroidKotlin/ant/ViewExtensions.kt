package ru.geekbrains.AndroidKotlin.ant

import android.view.View

fun View.dip(value: Int) = (value * resources.displayMetrics.density).toInt()