package com.nullpointer.runningcompose.ui.screens.config.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.ui.screens.config.components.share.SelectOptionConfig
import com.nullpointer.runningcompose.ui.screens.config.components.share.TitleConfig

@Composable
fun MetricConfig() {
    val listMetrics = remember { MetricType.values().toList() }
    TitleConfig(text = "Otras configuraciones")
    SelectOptionConfig(textField = "metricas",
        selected = "Pan",
        listItems = listMetrics,
        listNamed = listMetrics.map {
            stringResource(
                id = it.stringRes)
        },
        onChange = {})
}