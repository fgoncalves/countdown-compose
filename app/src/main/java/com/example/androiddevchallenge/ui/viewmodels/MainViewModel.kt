package com.example.androiddevchallenge.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


enum class CounterState {
    STOPPED,
    PAUSED,
    RUNNING,
}

data class AppState(
    // Time in a weird way...
    val timeRep: Int = 0,
    val counterState: CounterState = CounterState.STOPPED,
)

abstract class MainViewModel : ViewModel() {
    abstract val appState: LiveData<AppState>

    abstract fun onDigitClicked(digit: Int)

    abstract fun onBackSpaceClicked()
}

class MainViewModelImpl : MainViewModel() {
    private val appStateMutable = MutableLiveData(AppState())

    override val appState: LiveData<AppState> = appStateMutable

    override fun onDigitClicked(digit: Int) {
        val value = appStateMutable.value!!
        appStateMutable.value = value.copy(
            timeRep = value.timeRep * 10 + digit
        )
    }

    override fun onBackSpaceClicked() {
        val value = appStateMutable.value!!
        appStateMutable.value = value.copy(
            timeRep = value.timeRep / 10
        )
    }
}