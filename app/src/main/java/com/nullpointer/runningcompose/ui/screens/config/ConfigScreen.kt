package com.nullpointer.runningcompose.ui.screens.config

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.ui.interfaces.ActionRootDestinations
import com.nullpointer.runningcompose.ui.navigation.HomeNavGraph
import com.nullpointer.runningcompose.ui.screens.config.components.InfoUserConfig
import com.nullpointer.runningcompose.ui.screens.config.components.mapConfig.MapSettings
import com.nullpointer.runningcompose.ui.screens.config.components.selectors.SelectMetricConfig
import com.nullpointer.runningcompose.ui.screens.config.components.selectors.SelectNumberRunsGraph
import com.nullpointer.runningcompose.ui.screens.config.components.share.TitleConfig
import com.nullpointer.runningcompose.ui.screens.config.viewModel.ConfigViewModel
import com.nullpointer.runningcompose.ui.screens.destinations.EditInfoScreenDestination
import com.nullpointer.runningcompose.ui.states.OrientationScreenState
import com.nullpointer.runningcompose.ui.states.rememberOrientationScreenState
import com.ramcosta.composedestinations.annotation.Destination


@HomeNavGraph
@Destination
@Composable
fun ConfigScreen(
    actionRootDestinations: ActionRootDestinations,
    configViewModel: ConfigViewModel = hiltViewModel(),
    configState: OrientationScreenState = rememberOrientationScreenState()
) {
    val mapConfig by configViewModel.mapConfig.collectAsState()
    val authData by configViewModel.authData.collectAsState()
    val metricsMap by configViewModel.metrics.collectAsState()
    val numberRunsGraph by configViewModel.numberRunsGraph.collectAsState()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(horizontal = 10.dp, vertical = 20.dp)
        ) {
            InfoUserConfig(
                authData = authData,
                orientation = configState.orientation,
                actionGoEditInfo = {
                    actionRootDestinations.changeRoot(EditInfoScreenDestination(isAuth = true))
                })
            MapSettings(
                mapConfig = mapConfig,
                orientation = configState.orientation,
                changeWeight = { configViewModel.changeMapConfig(weight = it) },
                changeStyleMap = { configViewModel.changeMapConfig(style = it) },
                changeColorMap = { configViewModel.changeMapConfig(color = it) }
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                TitleConfig(text = stringResource(R.string.title_others_config))
                SelectMetricConfig(
                    metrics = metricsMap,
                    changeMetric = { configViewModel.changeMetrics(it) })
                SelectNumberRunsGraph(
                    numberRunsGraph = numberRunsGraph,
                    changeNumberRunsGraph =  configViewModel::changeNumberRunsGraph
                )
            }
        }
    }
}





