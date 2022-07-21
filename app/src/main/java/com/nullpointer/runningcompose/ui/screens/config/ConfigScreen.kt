package com.nullpointer.runningcompose.ui.screens.config

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.nullpointer.runningcompose.presentation.ConfigViewModel
import com.nullpointer.runningcompose.ui.navigation.HomeNavGraph
import com.nullpointer.runningcompose.ui.navigation.RootNavGraph
import com.nullpointer.runningcompose.ui.screens.config.components.InfoUserConfig
import com.nullpointer.runningcompose.ui.screens.config.components.MapSettings
import com.nullpointer.runningcompose.ui.screens.config.components.MetricConfig
import com.nullpointer.runningcompose.ui.screens.destinations.EditInfoScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@HomeNavGraph
@Destination
@Composable
fun ConfigScreen(
    configViewModel: ConfigViewModel,
    navigator: DestinationsNavigator,
) {
    val mapConfig by configViewModel.mapConfig.collectAsState()
    val userConfig by configViewModel.userConfig.collectAsState()
    val metricsMap by configViewModel.metrics.collectAsState()
    val orientation = LocalConfiguration.current.orientation

    Scaffold {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            userConfig?.let { it1 ->
                InfoUserConfig(
                    orientation = orientation,
                    userConfig = it1,
                    actionGoEditInfo = {
//                        navigator.navigate(EditInfoScreenDestination)
                    })
            }
            MapSettings(
                orientation = orientation,
                mapConfig = mapConfig,
                changeWeight = { configViewModel.changeMapConfig(weight = it) },
                changeStyleMap = { configViewModel.changeMapConfig(style = it) },
                changeColorMap = { configViewModel.changeMapConfig(color = it) }
            )
            MetricConfig(
                metrics = metricsMap,
                changeMetric = { configViewModel.changeMetrics(it) })
        }
    }
}





