package com.example.androiddevchallenge.ui.utils

fun Int.asTimeString(): String =
    "%02dh %02dm %02ds".format(
        this / 10000,
        this / 100 % 100,
        this % 100,
    )

fun Int.asMillis(): Int {
    val hours = this / 10000
    val minutes = this / 100 % 100
    val seconds = this % 100

    return (seconds + minutes * 60 + hours * 3600) * 1000
}

/**
 * from proper millis to the weird time
 */
fun Int.asTime(): Int {
    val hours = this / 1000 / 3600
    val minutes = this / 1000 / 60 % 60
    val seconds = this / 1000 % 60

    return hours * 10000 + minutes * 100 + seconds
}
