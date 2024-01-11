package com.nullpointer.runningcompose.ui.screens.statistics.componets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.ui.preview.config.ThemePreviews
import com.nullpointer.runningcompose.ui.share.mpGraph.MpGraphAndroid

@Composable
fun GraphRuns(
    list: List<RunData>,
    modifier: Modifier = Modifier,
    metricType: MetricType,
) {

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            stringResource(R.string.title_graph_statisctis),
            textAlign = TextAlign.Center,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1F)
        ) {
            Text(
                modifier = Modifier
                    .vertical()
                    .rotate(-90f),
                text = stringResource(R.string.title_avg_speed_run_graph),
                style = MaterialTheme.typography.caption,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            MpGraphAndroid(
                list = list,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                metricType = metricType
            )
        }
        Text(
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(R.string.title_runs_order_date_graph)
        )

    }
}

@ThemePreviews
@Composable
private fun GraphRunsPreview() {
    GraphRuns(
        list = RunData.listRunsExample,
        metricType = MetricType.Meters,
    )
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
