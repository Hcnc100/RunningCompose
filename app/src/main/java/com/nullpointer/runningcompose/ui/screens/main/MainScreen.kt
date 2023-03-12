package com.nullpointer.runningcompose.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.nullpointer.runningcompose.core.states.LoginStatus
import com.nullpointer.runningcompose.presentation.ConfigViewModel
import com.nullpointer.runningcompose.ui.interfaces.ActionRootDestinations
import com.nullpointer.runningcompose.ui.screens.NavGraphs
import com.nullpointer.runningcompose.ui.screens.destinations.EditInfoScreenDestination
import com.nullpointer.runningcompose.ui.screens.destinations.HomeScreenDestination
import com.nullpointer.runningcompose.ui.states.MainScreenState
import com.nullpointer.runningcompose.ui.states.rememberMainScreenState
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency

@Composable
fun MainScreen(
    actionSuccess: () -> Unit,
    configViewModel: ConfigViewModel = hiltViewModel(),
    mainScreenState: MainScreenState = rememberMainScreenState()
) {

    val stateAuth by configViewModel.stateAuth.collectAsState()

    MainScreenState(
        stateAuth = stateAuth,
        actionSuccess = actionSuccess,
        configViewModel = configViewModel,
        scaffoldState = mainScreenState.scaffoldState,
        navController = mainScreenState.navController,
        actionsRootDestinations = mainScreenState.rootActions
    )

}

@Composable
fun MainScreenState(
    stateAuth: LoginStatus,
    actionSuccess: () -> Unit,
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    configViewModel: ConfigViewModel,
    actionsRootDestinations: ActionRootDestinations
) {
    Scaffold(
        scaffoldState = scaffoldState
    ) { innerPadding ->
        when (stateAuth) {
            LoginStatus.Authenticating -> null
            LoginStatus.Authenticated -> HomeScreenDestination
            LoginStatus.Unauthenticated -> EditInfoScreenDestination
        }?.let { startDestination ->
            LaunchedEffect(key1 = Unit) {
                actionSuccess()
            }
            DestinationsNavHost(
                navGraph = NavGraphs.main,
                startRoute = startDestination,
                navController = navController,
                modifier = Modifier.padding(innerPadding),
                dependenciesContainerBuilder = {
                    dependency(actionsRootDestinations)
                    dependency(configViewModel)
                }
            )
        }
    }
}