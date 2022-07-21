package com.nullpointer.runningcompose.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.presentation.ConfigViewModel
import com.nullpointer.runningcompose.presentation.SelectViewModel
import com.nullpointer.runningcompose.ui.interfaces.ActionRootDestinations
import com.nullpointer.runningcompose.ui.navigation.HomeDestinations
import com.nullpointer.runningcompose.ui.navigation.RootNavGraph
import com.nullpointer.runningcompose.ui.screens.NavGraphs
import com.nullpointer.runningcompose.ui.share.SelectToolbar
import com.nullpointer.runningcompose.ui.states.MainScreenState
import com.nullpointer.runningcompose.ui.states.rememberMainScreenState
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.navigation.navigate

@RootNavGraph
@Destination
@Composable
fun MainScreen(
    actionRootDestinations: ActionRootDestinations,
    mainState: MainScreenState = rememberMainScreenState(),
    configViewModel: ConfigViewModel,
    selectViewModel: SelectViewModel,
) {
    Scaffold(
        bottomBar = {
            MainButtonNavigation(
                navController = mainState.navController,
                actionClear = selectViewModel::clearSelect
            )
        },
        topBar = {
            SelectToolbar(
                titleDefault = R.string.app_name,
                titleSelection = R.plurals.selected_items,
                numberSelection = selectViewModel.sizeSelected,
                actionClear = selectViewModel::clearSelect
            )
        }
    ) { innerPadding ->
        DestinationsNavHost(
            navGraph = NavGraphs.home,
            modifier = Modifier.padding(innerPadding),
            startRoute = NavGraphs.home.startRoute,
            navController = mainState.navController,
            engine = mainState.navHostEngine,
            dependenciesContainerBuilder = {
                dependency(actionRootDestinations)
                dependency(configViewModel)
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
    BottomNavigation {
        HomeDestinations.values().forEach { destination ->
            BottomNavigationItem(
                selected = currentDestination?.route == destination.destination.route,
                onClick = {
                    if (currentDestination?.route != destination.destination.route) {
                        actionClear()
                    }

                    navController.navigate(destination.destination) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painterResource(id = destination.iconNavigation),
                        stringResource(id = destination.titleShow)
                    )
                },
                label = { Text(stringResource(id = destination.titleShow)) },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = LocalContentColor.current,
                alwaysShowLabel = false
            )
        }
    }
}
