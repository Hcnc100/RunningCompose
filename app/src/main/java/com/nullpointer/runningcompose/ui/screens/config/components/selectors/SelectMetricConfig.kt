package com.nullpointer.runningcompose.ui.screens.config.components.selectors

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.ui.screens.config.components.share.SelectOptionConfig
import com.nullpointer.runningcompose.ui.screens.config.components.share.TitleConfig


private val mapsMetric = MetricType.values().associateBy({ it }, { it.stringRes })
@Composable
fun SelectMetricConfig(
    metrics: MetricType,
    changeMetric: (MetricType) -> Unit,
) {

    Column {
        TitleConfig(text = stringResource(R.string.title_others_config))
        SelectOptionConfig(
            onChange = changeMetric,
            listItemsAndNames = mapsMetric,
            titleField = stringResource(R.string.title_metrics),
            textCurrentSelected = stringResource(id = metrics.stringRes),
        )
    }

}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFF
)
@Composable
fun SelectMetricConfigPreview() {
    SelectMetricConfig(
        metrics = MetricType.Meters,
        changeMetric = {}
    )
}

