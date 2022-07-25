package com.nullpointer.runningcompose.ui.states

import android.content.Context
import android.content.res.Configuration
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import kotlinx.coroutines.CoroutineScope

class OrientationScreenState(
    scaffoldState: ScaffoldState,
    context: Context,
    focusManager: FocusManager,
    val scope:CoroutineScope,
    private val configuration: Configuration
) : SimpleScreenState(scaffoldState, context, focusManager) {
    val orientation get() = configuration.orientation
}

@Composable
fun rememberOrientationScreenState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    focusManager: FocusManager = LocalFocusManager.current,
    context: Context = LocalContext.current,
    configuration: Configuration = LocalConfiguration.current,
    scope: CoroutineScope= rememberCoroutineScope()
) = remember(scaffoldState,scope) {
    OrientationScreenState(scaffoldState, context, focusManager, scope,configuration)
}