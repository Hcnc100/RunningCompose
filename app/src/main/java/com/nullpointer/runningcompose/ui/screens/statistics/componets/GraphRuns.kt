package com.nullpointer.runningcompose.ui.screens.statistics.componets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.ui.share.mpGraph.MpGraphAndroid

@Composable
fun GraphRuns(
    list: List<Run>,
    modifier: Modifier = Modifier,
) {
    Column {
        Text(
            stringResource(R.string.title_graph_statisctis),
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center)
        MpGraphAndroid(list = list, modifier = modifier)
    }
}
