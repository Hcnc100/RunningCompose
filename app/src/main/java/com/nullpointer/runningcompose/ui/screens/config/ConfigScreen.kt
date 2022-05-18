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
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.nullpointer.runningcompose.presentation.ConfigViewModel
import com.nullpointer.runningcompose.ui.screens.config.components.InfoUserConfig
import com.nullpointer.runningcompose.ui.screens.config.components.MapSettings
import com.nullpointer.runningcompose.ui.screens.config.components.MetricConfig
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun ConfigScreen(
    configViewModel: ConfigViewModel = hiltViewModel(),
) {
    val configMap by configViewModel.mapConfig.collectAsState()
    val orientation = LocalConfiguration.current.orientation

    Scaffold {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            InfoUserConfig(orientation)
            MapSettings(orientation, configMap)
            MetricConfig()
        }
    }
}





