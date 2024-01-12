package com.nullpointer.runningcompose.ui.screens.config.components.selectors

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.ui.preview.config.SimplePreview
import com.nullpointer.runningcompose.ui.screens.config.components.share.SelectOptionConfig


private val mapsMetric = MetricType.values().associateBy({ it }, { it.stringRes })
@Composable
fun SelectMetricConfig(
    metrics: MetricType,
    changeMetric: (MetricType) -> Unit,
) {
        SelectOptionConfig(
            onChange = changeMetric,
            listItemsAndNames = mapsMetric,
            titleField = stringResource(R.string.title_metrics),
            textCurrentSelected = stringResource(id = metrics.stringRes),
        )
}

@SimplePreview
@Composable
fun SelectMetricConfigPreview() {
    SelectMetricConfig(
        metrics = MetricType.Meters,
        changeMetric = {}
    )
}

