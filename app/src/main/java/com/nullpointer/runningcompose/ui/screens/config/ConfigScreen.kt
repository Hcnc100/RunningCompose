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
import com.nullpointer.runningcompose.ui.screens.config.components.InfoUserConfig
import com.nullpointer.runningcompose.ui.screens.config.components.MapSettings
import com.nullpointer.runningcompose.ui.screens.config.components.MetricConfig
import com.nullpointer.runningcompose.ui.screens.destinations.EditInfoScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun ConfigScreen(
    configViewModel: ConfigViewModel,
    navigator: DestinationsNavigator,
) {
    val configMap by configViewModel.mapConfig.collectAsState()
    val configUser by configViewModel.userConfig.collectAsState()
    val orientation = LocalConfiguration.current.orientation

    Scaffold {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            InfoUserConfig(
                orientation = orientation,
                configUserConfig = configUser,
                actionGoEditInfo = {
                    navigator.navigate(EditInfoScreenDestination)
                })
            MapSettings(
                orientation = orientation,
                configMap = configMap,
                changeWeight = { configViewModel.changeMapConfig(weight = it) },
                changeStyleMap = { configViewModel.changeMapConfig(style = it) },
                changeColorMap = { configViewModel.changeMapConfig(color = it) }
            )
            MetricConfig(
                configMap = configMap,
                changeMetric = { configViewModel.changeMapConfig(metricType = it) })
        }
    }
}





