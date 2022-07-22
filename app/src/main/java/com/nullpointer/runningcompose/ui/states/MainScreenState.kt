package com.nullpointer.runningcompose.ui.states

import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.spec.NavHostEngine

class MainScreenState(
    scaffoldState: ScaffoldState,
    context: Context,
    focusManager: FocusManager,
    val navController: NavHostController,
    val navHostEngine: NavHostEngine
) : SimpleScreenState(scaffoldState, context, focusManager)

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun rememberMainScreenState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberAnimatedNavController(),
    context: Context = LocalContext.current,
    focusManager: FocusManager = LocalFocusManager.current,
    navHostEngine: NavHostEngine = rememberAnimatedNavHostEngine(
        navHostContentAlignment = Alignment.BottomEnd,
        rootDefaultAnimations = RootNavGraphDefaultAnimations.ACCOMPANIST_FADING,
    )
) = remember(scaffoldState, navController,navHostEngine) {
    MainScreenState(scaffoldState, context, focusManager, navController,navHostEngine)
}