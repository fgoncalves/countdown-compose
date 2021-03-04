package com.example.androiddevchallenge.ui.screens

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
import com.example.androiddevchallenge.ui.components.DigiPad
import com.example.androiddevchallenge.ui.components.Timer
import com.example.androiddevchallenge.ui.theme.Size
import com.example.androiddevchallenge.ui.theme.h5Bold
import com.example.androiddevchallenge.ui.utils.DEFAULT_ANIMATION_DURATION
import com.example.androiddevchallenge.ui.utils.asTimeString
import com.example.androiddevchallenge.ui.viewmodels.AppState
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
            Fab(appState!!.hasTime())
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Timer(
                value = appState!!.timeRep.asTimeString(),
                onBackSpaceClicked = viewModel::onBackSpaceClicked,
                modifier = Modifier.padding(top = Size.xxlarge)
            )

            Divider(color = MaterialTheme.colors.primary, modifier = Modifier.padding(Size.medium))

            DigiPad(
                onDigitClicked = viewModel::onDigitClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            )
        }

    }
}

@Composable
private fun Fab(enabled: Boolean = true) {
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
        onClick = {},
        modifier = Modifier.size(size)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = Size.medium),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_play_arrow),
                contentDescription = null,
                tint = Color.White,
            )
        }
    }
}

private fun AppState.hasTime() =
    timeRep != 0