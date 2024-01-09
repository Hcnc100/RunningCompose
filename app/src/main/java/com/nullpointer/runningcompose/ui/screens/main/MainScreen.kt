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
import com.nullpointer.runningcompose.core.states.Resource
import com.nullpointer.runningcompose.models.data.AuthData
import com.nullpointer.runningcompose.ui.screens.main.viewModel.AuthViewModel
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
    authViewModel: AuthViewModel = hiltViewModel(),
    mainScreenState: MainScreenState = rememberMainScreenState(),
) {

    val stateAuth by authViewModel.authData.collectAsState()

    MainScreen(
        authDataState = stateAuth,
        actionSuccess = actionSuccess,
        scaffoldState = mainScreenState.scaffoldState,
        navController = mainScreenState.navController,
        actionsRootDestinations = mainScreenState.rootActions,
    )

}

@Composable
fun MainScreen(
    authDataState: Resource<AuthData?>,
    actionSuccess: () -> Unit,
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    actionsRootDestinations: ActionRootDestinations,
) {
    Scaffold(
        scaffoldState = scaffoldState
    ) { innerPadding ->
        when (authDataState) {
            Resource.Loading -> null
            Resource.Failure -> EditInfoScreenDestination
            is Resource.Success -> if (authDataState.data?.isAuth == true) HomeScreenDestination else EditInfoScreenDestination
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
                }
            )
        }
    }
}