package com.nullpointer.runningcompose.ui.states

import android.content.Context
import android.content.res.Configuration
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope

@Stable
class OrientationScreenState(
    context: Context,
    val scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    private val configuration: Configuration
) : SimpleScreenState(context, scaffoldState) {
    val orientation get() = configuration.orientation
}

@Composable
fun rememberOrientationScreenState(
    context: Context = LocalContext.current,
    scope: CoroutineScope = rememberCoroutineScope(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    configuration: Configuration = LocalConfiguration.current
) = remember(scaffoldState,scope) {
    OrientationScreenState(context, scope, scaffoldState, configuration)
}