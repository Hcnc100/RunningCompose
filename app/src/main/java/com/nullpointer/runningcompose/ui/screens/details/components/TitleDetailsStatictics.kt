package com.nullpointer.runningcompose.ui.screens.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.ui.preview.config.SimplePreview

@Composable
fun TitleStatistics(
    isExpanded: Boolean,
    fontSizeTitle: TextUnit,
    canExpanded: Boolean
) {
    val iconExpanded = if (isExpanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down


    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(5.dp),
            text = stringResource(id = R.string.title_screen_statistics),
            style = MaterialTheme.typography.h5.copy(fontSize = fontSizeTitle)
        )
        if (canExpanded) {
            Spacer(modifier = Modifier.width(5.dp))
            Icon(
                painter = painterResource(id = iconExpanded),
                contentDescription = stringResource(R.string.description_arrow_statistics)
            )
        }
    }
}


@SimplePreview
@Composable
fun TitleStatisticCanExpandedPreview() {
    TitleStatistics(
        fontSizeTitle = 12.sp,
        canExpanded = true,
        isExpanded = true
    )
}

@SimplePreview
@Composable
fun TitleStatisticCantExpandedPreview() {
    TitleStatistics(
        fontSizeTitle = 12.sp,
        canExpanded = false,
        isExpanded = true
    )
}