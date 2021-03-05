/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
