package com.nullpointer.runningcompose.ui.states

import android.content.Context
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Stable
class HomeScreenState(
    context: Context,
    scaffoldState: ScaffoldState,
    val navController: NavHostController
) : SimpleScreenState(context, scaffoldState)

@Composable
fun rememberHomeScreenState(
    context: Context = LocalContext.current,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController()
) = remember(scaffoldState, navController) {
    HomeScreenState(context, scaffoldState, navController)
}