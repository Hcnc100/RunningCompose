package com.nullpointer.runningcompose.ui.screens.statistics.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.ui.share.mpGraph.MpGraphAndroid

@Composable
fun GraphRuns(
    list: List<Run>,
    modifier: Modifier = Modifier,
    metricType: MetricType,
) {
    Column(modifier = modifier) {
        Text(
            stringResource(R.string.title_graph_statisctis),
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier
                    .vertical()
                    .rotate(-90f)
                    .padding(1.dp).fillMaxWidth(.4f),
                text = stringResource(R.string.title_avg_speed_run_graph),
                style = MaterialTheme.typography.caption,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            MpGraphAndroid(list = list,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.9f),
                metricType = metricType)
        }
        Text(text = stringResource(R.string.title_runs_order_date_graph),
            style = MaterialTheme.typography.caption,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
            )

    }
}


fun Modifier.vertical() =
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        layout(placeable.height, placeable.width) {
            placeable.place(
                x = -(placeable.width / 2 - placeable.height / 2),
                y = -(placeable.height / 2 - placeable.width / 2)
            )
        }
    }
