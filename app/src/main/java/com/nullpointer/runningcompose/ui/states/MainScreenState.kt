package com.nullpointer.runningcompose.ui.states

import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.spec.NavHostEngine

class MainScreenState(
    context: Context,
    scaffoldState: ScaffoldState,
    val navHostEngine: NavHostEngine,
    val navController: NavHostController
) : SimpleScreenState(context, scaffoldState)

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun rememberMainScreenState(
    context: Context = LocalContext.current,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberAnimatedNavController(),
    navHostEngine: NavHostEngine = rememberAnimatedNavHostEngine(
        navHostContentAlignment = Alignment.BottomEnd,
        rootDefaultAnimations = RootNavGraphDefaultAnimations.ACCOMPANIST_FADING,
    )
) = remember(scaffoldState, navController,navHostEngine) {
    MainScreenState(context, scaffoldState, navHostEngine, navController)
}