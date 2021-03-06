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
package com.example.androiddevchallenge.ui.viewmodels

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.ui.utils.asMillis
import com.example.androiddevchallenge.ui.utils.asTime

sealed class CounterState {
    abstract val startTime: Int

    data class Stopped(
        override val startTime: Int
    ) : CounterState()

    data class Paused(
        override val startTime: Int
    ) : CounterState()

    data class Running(
        override val startTime: Int
    ) : CounterState()
}

data class AppState(
    // Time in a weird way...
    val timeRep: Int = 0,
    val counterState: CounterState = CounterState.Stopped(-1),
)

abstract class MainViewModel : ViewModel() {
    abstract val appState: LiveData<AppState>

    abstract fun onDigitClicked(digit: Int)

    abstract fun onBackSpaceClicked()

    abstract fun onFabClicked()

    abstract fun onStopButtonClicked()
}

class MainViewModelImpl : MainViewModel() {
    private var currentTimer: CountDownTimer? = null
    private val appStateMutable = MutableLiveData(AppState())

    override val appState: LiveData<AppState> = appStateMutable

    override fun onDigitClicked(digit: Int) {
        val value = appStateMutable.value!!
        appStateMutable.value = value.copy(
            timeRep = (value.timeRep * 10 + digit).coerceAtMost(999999)
        )
    }

    override fun onBackSpaceClicked() {
        val value = appStateMutable.value!!
        appStateMutable.value = value.copy(
            timeRep = value.timeRep / 10
        )
    }

    override fun onFabClicked() {
        val value = appState.value!!
        when (value.counterState) {
            is CounterState.Stopped -> {
                appStateMutable.value = value.copy(
                    counterState = CounterState.Running(value.timeRep.asMillis()),
                )

                currentTimer = newTimer()
                currentTimer?.start()
            }

            is CounterState.Paused -> {
                appStateMutable.value = value.copy(
                    counterState = CounterState.Running(value.counterState.startTime),
                )

                currentTimer = newTimer()
                currentTimer?.start()
            }

            is CounterState.Running -> {
                appStateMutable.value = value.copy(
                    counterState = CounterState.Paused(value.counterState.startTime),
                )
                currentTimer?.cancel()
                currentTimer = null
            }
        }
    }

    override fun onStopButtonClicked() {
        val value = appState.value!!
        appStateMutable.value = value.copy(
            counterState = CounterState.Stopped(0),
        )
        currentTimer?.cancel()
        currentTimer = null
    }

    private fun newTimer() =
        object : CountDownTimer(appState.value!!.timeRep.asMillis().toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                appStateMutable.value = appStateMutable.value!!.copy(
                    timeRep = millisUntilFinished.toInt().asTime(),
                )
            }

            override fun onFinish() {
                appStateMutable.value = appStateMutable.value!!.copy(
                    timeRep = 0,
                    counterState = CounterState.Stopped(-1),
                )
            }
        }
}
