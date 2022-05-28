package com.nullpointer.runningcompose.ui.screens.config.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.ui.screens.config.components.share.SelectOptionConfig
import com.nullpointer.runningcompose.ui.screens.config.components.share.TitleConfig

@Composable
fun MetricConfig(metrics: MetricType, changeMetric: (MetricType) -> Unit) {
    val listMetrics = remember { MetricType.values().toList() }
    TitleConfig(text = stringResource(R.string.title_others_config))
    SelectOptionConfig(textField = stringResource(R.string.title_metrics),
        selected = stringResource(id = metrics.stringRes),
        listItems = listMetrics,
        listNamed = listMetrics.map { stringResource(id = it.stringRes) },
        onChange = changeMetric)
}