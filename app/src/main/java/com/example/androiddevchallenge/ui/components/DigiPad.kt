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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.example.androiddevchallenge.ui.theme.Size
import com.example.androiddevchallenge.ui.theme.h2SemiBold

@Preview(name = "DigiPad", showBackground = true)
@Composable
fun DigiPad(
    modifier: Modifier = Modifier,
    onDigitClicked: (Int) -> Unit = {},
) {
    Column(modifier = modifier) {
        DigitRow(onDigitClicked, 1, 2, 3)
        DigitRow(onDigitClicked, 4, 5, 6)
        DigitRow(onDigitClicked, 7, 8, 9)
        DigitRow(onDigitClicked, 0)
    }
}

@Composable
fun DigitRow(
    onDigitClicked: (Int) -> Unit = {},
    vararg digits: Int = intArrayOf(),
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth(),
    ) {
        digits.forEach {
            Digit(it, onDigitClicked)
        }
    }
}

@Preview(name = "Digit", showBackground = true)
@Composable
private fun Digit(
    digit: Int = 0,
    onDigitClicked: (Int) -> Unit = {},
) {
    Text(
        text = digit.toString(),
        style = MaterialTheme.typography.h2SemiBold.copy(
            color = MaterialTheme.colors.onPrimary
        ),
        modifier = Modifier
            .padding(Size.xxxlarge)
            .clip(CircleShape)
            .clickable { onDigitClicked(digit) }
    )
}
