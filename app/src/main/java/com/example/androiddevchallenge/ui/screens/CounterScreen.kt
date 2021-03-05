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
package com.example.androiddevchallenge.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.ui.components.CountDownTimer
import com.example.androiddevchallenge.ui.components.DigiPad
import com.example.androiddevchallenge.ui.components.Timer
import com.example.androiddevchallenge.ui.theme.Size
import com.example.androiddevchallenge.ui.theme.h5Bold
import com.example.androiddevchallenge.ui.utils.DEFAULT_ANIMATION_DURATION
import com.example.androiddevchallenge.ui.utils.asMillis
import com.example.androiddevchallenge.ui.utils.asTimeString
import com.example.androiddevchallenge.ui.viewmodels.AppState
import com.example.androiddevchallenge.ui.viewmodels.CounterState
import com.example.androiddevchallenge.ui.viewmodels.MainViewModel

@Composable
fun CounterScreen(
    viewModel: MainViewModel,
) {
    val appState by viewModel.appState.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.background,
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.h5Bold,
                    )
                },
            )
        },
        floatingActionButton = {
            Fab(
                icon = if (appState!!.counterState is CounterState.Running)
                    R.drawable.ic_pause
                else
                    R.drawable.ic_play_arrow,
                enabled = appState!!.hasTime(),
                onClick = viewModel::onFabClicked,
            )
        }
    ) {
        if (appState!!.counterState is CounterState.Stopped)
            TimerSetup(
                appState = appState,
                onBackSpaceClicked = viewModel::onBackSpaceClicked,
                onDigitClicked = viewModel::onDigitClicked,
            )
        else
            RunningTimer(
                appState = appState!!,
                onStopClicked = viewModel::onStopButtonClicked,
            )
    }
}

@Composable
private fun RunningTimer(
    appState: AppState,
    onStopClicked: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = Size.xxlarge),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CountDownTimer(
            startTimeMillis = appState.counterState.startTime,
            currentTimeMillis = appState.timeRep.asMillis(),
            modifier = Modifier.fillMaxWidth(),
        )

        val isPaused = appState.counterState is CounterState.Paused
        val modifier = if (isPaused)
            Modifier
                .padding(top = Size.xxxxlarge)
                .clickable {
                    onStopClicked()
                }
        else
            Modifier.padding(top = Size.xxxxlarge)

        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = stringResource(id = R.string.stop),
                style = MaterialTheme.typography.subtitle1.copy(
                    color = if (isPaused)
                        MaterialTheme.colors.secondary
                    else
                    // Don't really know how to grab this from the theme
                        Color.LightGray,
                ),
            )
        }
    }
}

@Composable
private fun TimerSetup(
    appState: AppState?,
    onBackSpaceClicked: () -> Unit = {},
    onDigitClicked: (Int) -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Timer(
            value = appState!!.timeRep.asTimeString(),
            onBackSpaceClicked = onBackSpaceClicked,
            modifier = Modifier.padding(top = Size.xxlarge)
        )

        Divider(color = MaterialTheme.colors.primary, modifier = Modifier.padding(Size.medium))

        DigiPad(
            onDigitClicked = onDigitClicked,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        )
    }
}

@Composable
private fun Fab(
    @DrawableRes
    icon: Int = 0,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
) {
    val transitionState = remember {
        MutableTransitionState(enabled).apply {
            targetState = !enabled
        }
    }
    val transition = updateTransition(transitionState)
    val size by transition.animateDp({ tween(durationMillis = DEFAULT_ANIMATION_DURATION) }) {
        if (enabled)
            52.dp
        else
            0.dp
    }

    FloatingActionButton(
        backgroundColor = MaterialTheme.colors.secondary,
        onClick = onClick,
        modifier = Modifier.size(size)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = Size.medium),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Color.White,
            )
        }
    }
}

private fun AppState.hasTime() =
    timeRep != 0
