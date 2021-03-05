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