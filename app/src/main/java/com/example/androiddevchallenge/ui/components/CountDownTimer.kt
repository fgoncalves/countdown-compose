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
package com.example.androiddevchallenge.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.example.androiddevchallenge.ui.utils.asTime
import com.example.androiddevchallenge.ui.utils.asTimeString
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import com.example.androiddevchallenge.ui.theme.Size as Dimens

@Preview(showBackground = true)
@Composable
fun CountDownTimer(
    modifier: Modifier = Modifier,
    startTimeMillis: Int = 1,
    currentTimeMillis: Int = 0,
) {
    ConstraintLayout(
        modifier = modifier,
        constraintSet = constraintSet()
    ) {
        Timer(
            value = currentTimeMillis.asTime().asTimeString(),
            fontSize = 34.sp,
            modifier = Modifier
                .layoutId(TIMER_LAYOUT_ID)
                .enableDeletion(false),
        )

        CountDownTimerArc(
            progress = currentTimeMillis.toFloat() / startTimeMillis,
            modifier = Modifier
                .layoutId(ARC_LAYOUT_ID)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CountDownTimerArc(
    modifier: Modifier = Modifier,
    progress: Float = 0f,
) {
    val progressColor = MaterialTheme.colors.secondary
    val backgroundColor = MaterialTheme.colors.onBackground

    val startAngle = 120f
    val endAngle = 300f

    Canvas(modifier = modifier) {
        drawArc(
            backgroundColor,
            startAngle,
            endAngle,
            useCenter = false,
            style = Stroke(
                width = Dimens.xxxlarge.value,
                cap = StrokeCap.Round,
            ),
            size = this.size,
        )

        drawArc(
            progressColor,
            startAngle,
            endAngle * progress,
            useCenter = false,
            style = Stroke(
                width = Dimens.xxxlarge.value,
                cap = StrokeCap.Round,
            ),
            size = this.size,
        )

        if (progress > 0f) {
            val x =
                this.size.width / 2 * cos((startAngle + endAngle * progress).toRadians()) + center.x
            val y =
                this.size.height / 2 * sin((startAngle + endAngle * progress).toRadians()) + center.y
            drawCircle(
                progressColor,
                radius = Dimens.xxxxlarge.value,
                center = Offset(x, y),
            )
        }
    }
}

private const val ARC_LAYOUT_ID = "arc"
private const val TIMER_LAYOUT_ID = "timer"

private fun constraintSet() =
    ConstraintSet {
        val arc = createRefFor(ARC_LAYOUT_ID)
        val timer = createRefFor(TIMER_LAYOUT_ID)

        constrain(arc) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)

            width = Dimension.value(300.dp)
            height = Dimension.value(300.dp)
        }

        constrain(timer) {
            top.linkTo(arc.top)
            start.linkTo(arc.start)
            end.linkTo(arc.end)
            bottom.linkTo(arc.bottom)

            width = Dimension.wrapContent
            height = Dimension.wrapContent
        }
    }

private fun Float.toRadians(): Float =
    (this * (PI / 180)).toFloat()
