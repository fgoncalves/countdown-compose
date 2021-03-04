package com.example.androiddevchallenge.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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