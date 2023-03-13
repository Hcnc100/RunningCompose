package com.nullpointer.runningcompose.ui.screens.config

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.nullpointer.runningcompose.presentation.ConfigViewModel
import com.nullpointer.runningcompose.ui.interfaces.ActionRootDestinations
import com.nullpointer.runningcompose.ui.navigation.HomeNavGraph
import com.nullpointer.runningcompose.ui.screens.config.components.MapSettings
import com.nullpointer.runningcompose.ui.screens.config.components.MetricConfig
import com.nullpointer.runningcompose.ui.states.OrientationScreenState
import com.nullpointer.runningcompose.ui.states.rememberOrientationScreenState
import com.ramcosta.composedestinations.annotation.Destination


@HomeNavGraph
@Destination
@Composable
fun ConfigScreen(
    configViewModel: ConfigViewModel,
    actionRootDestinations: ActionRootDestinations,
    configState:OrientationScreenState = rememberOrientationScreenState()
) {
    val mapConfig by configViewModel.mapConfig.collectAsState()
//    val userConfig by configViewModel.userConfig.collectAsState()
    val metricsMap by configViewModel.metrics.collectAsState()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
        ) {
//            userConfig?.let { userConfig ->
//                InfoUserConfig(
//                    userConfig = userConfig,
//                    orientation = configState.orientation,
//                    actionGoEditInfo = {
//                        actionRootDestinations.changeRoot(EditInfoScreenDestination(isAuth = true))
//                    })
//            }
            MapSettings(
                mapConfig = mapConfig,
                orientation = configState.orientation,
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





