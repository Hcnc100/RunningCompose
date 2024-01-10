package com.nullpointer.runningcompose.ui.states

import android.content.Context
import android.net.Uri
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nullpointer.runningcompose.ui.interfaces.ActionRootDestinations
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.Direction
import kotlinx.coroutines.CoroutineScope

@Stable
class MainScreenState(
    context: Context,
    scaffoldState: ScaffoldState,
    val scope: CoroutineScope,
    val navController: NavHostController
) : SimpleScreenState(context, scaffoldState) {
    val rootActions = object : ActionRootDestinations {
        override fun backDestination() = navController.popBackStack()
        override fun changeRoot(direction: Direction) = navController.navigate(direction)
        override fun changeRoot(route: Uri) = navController.navigate(route)
    }
}

@Composable
fun rememberMainScreenState(
    context: Context = LocalContext.current,
    scope: CoroutineScope = rememberCoroutineScope(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController()
) = remember(scaffoldState, navController, scope) {
    MainScreenState(context, scaffoldState, scope, navController)
}