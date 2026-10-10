package com.nullpointer.runningcompose.ui.screens.home

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.presentation.SelectViewModel
import com.nullpointer.runningcompose.ui.interfaces.ActionRootDestinations
import com.nullpointer.runningcompose.ui.navigation.HomeDestinations
import com.nullpointer.runningcompose.ui.navigation.MainNavGraph
import com.nullpointer.runningcompose.ui.screens.NavGraphs
import com.nullpointer.runningcompose.ui.share.SelectToolbar
import com.nullpointer.runningcompose.ui.share.material3.RunningSnackbarHost
import com.nullpointer.runningcompose.ui.states.HomeScreenState
import com.nullpointer.runningcompose.ui.states.rememberHomeScreenState
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.navigation.navigate

@MainNavGraph
@Destination
@Composable
fun HomeScreen(
    actionRootDestinations: ActionRootDestinations,
    selectViewModel: SelectViewModel = hiltViewModel(),
    mainState: HomeScreenState = rememberHomeScreenState(),
) {
    HomeScreen(
        scaffoldState = mainState.scaffoldState,
        navHostController = mainState.navController,
        sizeSelected = selectViewModel.listRunsSelected.size,
        clearSelected = selectViewModel::clearSelect,
        actionRootDestinations = actionRootDestinations,
        selectViewModel = selectViewModel
    )
}


@Composable
fun HomeScreen(
    sizeSelected: Int,
    clearSelected: () -> Unit,
    scaffoldState: ScaffoldState,
    selectViewModel: SelectViewModel,
    navHostController: NavHostController,
    actionRootDestinations: ActionRootDestinations
) {
    Scaffold(
        snackbarHost = { RunningSnackbarHost(scaffoldState.snackbarHostState) },
        bottomBar = {
            MainButtonNavigation(
                navController = navHostController,
                actionClear = clearSelected
            )
        },
        topBar = {
            SelectToolbar(
                titleDefault = R.string.app_name,
                titleSelection = R.plurals.selected_items,
                numberSelection = sizeSelected,
                actionClear = clearSelected
            )
        }
    ) { innerPadding ->
        DestinationsNavHost(
            navGraph = NavGraphs.home,
            modifier = Modifier.padding(innerPadding).consumeWindowInsets(innerPadding),
            startRoute = NavGraphs.home.startRoute,
            navController = navHostController,
            dependenciesContainerBuilder = {
                dependency(actionRootDestinations)
                dependency(selectViewModel)
            }
        )
    }
}

@Composable
private fun MainButtonNavigation(
    navController: NavController,
    actionClear: () -> Unit,
) {
    val currentDestination = navController.currentBackStackEntryAsState()
        .value?.destination
    HomeNavigationBar(
        selectedRoute = currentDestination?.route,
        onDestinationClick = { destination ->
                    if (currentDestination?.route != destination.destination.route) actionClear()
                    navController.navigate(destination.destination) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
        },
    )
}
