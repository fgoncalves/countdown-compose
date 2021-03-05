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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.ui.theme.Size
import com.example.androiddevchallenge.ui.theme.h3Bold

@Preview(showBackground = true)
@Composable
fun Timer(
    modifier: Modifier = Modifier,
    value: String = "00h 12m 59s",
    fontSize: TextUnit = MaterialTheme.typography.h3Bold.fontSize,
    onBackSpaceClicked: () -> Unit = {},
) {
    val enabled = value != "00h 00m 00s"

    val deletionEnable = modifier.foldIn(true) { acc, el ->
        if (el is DeletionModifier)
            el.enabled
        else
            acc
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Text(
            text = value,
            style = if (enabled)
                MaterialTheme.typography.h3Bold.copy(
                    color = MaterialTheme.colors.primary,
                    fontSize = fontSize,
                )
            else
                MaterialTheme.typography.h3Bold.copy(
                    color = Color.LightGray,
                    fontSize = fontSize,
                ),
            modifier = Modifier.padding(end = Size.medium)
        )

        if (deletionEnable)
            Icon(
                painter = painterResource(id = R.drawable.ic_backspace),
                contentDescription = stringResource(id = R.string.delete),
                tint = if (enabled)
                    MaterialTheme.colors.primary
                else
                    Color.LightGray,
                modifier = Modifier
                    .size(Size.xxxlarge)
                    .clickable {
                        onBackSpaceClicked()
                    },
            )
    }
}

class DeletionModifier(val enabled: Boolean) : Modifier.Element

fun Modifier.enableDeletion(enable: Boolean) = then(DeletionModifier(enabled = enable))
