package com.nullpointer.runningcompose.ui.states

import android.content.Context
import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.nullpointer.runningcompose.ui.interfaces.ActionRootDestinations
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.NavHostEngine
import kotlinx.coroutines.CoroutineScope

class RootAppState(
    context: Context,
    scaffoldState: ScaffoldState,
    val scope: CoroutineScope,
    val navHostEngine: NavHostEngine,
    val navController: NavHostController
) : SimpleScreenState(context, scaffoldState) {
    val rootActions = object : ActionRootDestinations {
        override fun backDestination() = navController.popBackStack()
        override fun changeRoot(direction: Direction) = navController.navigate(direction)
        override fun changeRoot(route: Uri) = navController.navigate(route)
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun rememberRootAppState(
    context: Context = LocalContext.current,
    scope: CoroutineScope = rememberCoroutineScope(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberAnimatedNavController(),
    navHostEngine: NavHostEngine = rememberAnimatedNavHostEngine(
        navHostContentAlignment = Alignment.BottomEnd,
        rootDefaultAnimations = RootNavGraphDefaultAnimations.ACCOMPANIST_FADING,
    ),
) = remember(scaffoldState, navController,scope,navHostEngine) {
    RootAppState(context, scaffoldState, scope, navHostEngine, navController)
}