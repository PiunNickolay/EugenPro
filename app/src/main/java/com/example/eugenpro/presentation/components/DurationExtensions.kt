package com.example.eugenpro.presentation.components

fun Int.toDurationString(): String {
    if (this <= 0) return "0 сек"

    val minutes = this / 60
    val seconds = this % 60

    return when {
        minutes == 0 -> "$seconds сек"
        seconds == 0 -> "$minutes мин"
        else -> "$minutes мин $seconds сек"
    }
}