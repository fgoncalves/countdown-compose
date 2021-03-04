package com.example.androiddevchallenge.ui.utils

fun Int.asTimeString(): String =
    "%02dh %02dm %02ds".format(
        this / 10000 % 100,
        this / 100 % 100,
        this % 100,
    )
