package com.nullpointer.runningcompose.ui.screens.config.components.selectors

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.utils.getGrayColor

@Composable
fun SelectMapColor(
    currentColor: Color,
    modifier: Modifier = Modifier,
    showDialogColor: () -> Unit,
) {

    Row(
        modifier = modifier
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(
            text = stringResource(R.string.title_color_line),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(.5f)
        )
        Box(modifier = Modifier
            .weight(.5f)
            .fillMaxHeight()
            .border(
                width = 2.dp,
                color = getGrayColor(),
                shape = RoundedCornerShape(5.dp)
            )
            .padding(2.dp)
            .background(currentColor)
            .clickable { showDialogColor() }
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFF
)
@Composable
private fun SelectMapColorPreview() {
    SelectMapColor(
        currentColor = Color.Cyan,
        showDialogColor = {}
    )
}