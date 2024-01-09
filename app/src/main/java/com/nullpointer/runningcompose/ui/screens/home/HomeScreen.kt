package com.nullpointer.runningcompose.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.presentation.ConfigViewModel
import com.nullpointer.runningcompose.presentation.SelectViewModel
import com.nullpointer.runningcompose.ui.interfaces.ActionRootDestinations
import com.nullpointer.runningcompose.ui.navigation.HomeDestinations
import com.nullpointer.runningcompose.ui.navigation.MainNavGraph
import com.nullpointer.runningcompose.ui.screens.NavGraphs
import com.nullpointer.runningcompose.ui.share.SelectToolbar
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
    configViewModel: ConfigViewModel,
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
        configViewModel = configViewModel,
        selectViewModel = selectViewModel
    )
}


@Composable
fun HomeScreen(
    sizeSelected: Int,
    clearSelected: () -> Unit,
    scaffoldState: ScaffoldState,
    configViewModel: ConfigViewModel,
    selectViewModel: SelectViewModel,
    navHostController: NavHostController,
    actionRootDestinations: ActionRootDestinations
) {
    Scaffold(
        scaffoldState = scaffoldState,
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
            modifier = Modifier.padding(innerPadding),
            startRoute = NavGraphs.home.startRoute,
            navController = navHostController,
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
                    if (currentDestination?.route != destination.destination.route) actionClear()
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
                        imageVector = ImageVector.vectorResource(id = destination.iconNavigation),
                        contentDescription = stringResource(id = destination.titleShow)
                    )
                },
                label = { Text(stringResource(id = destination.titleShow)) },
                selectedContentColor = MaterialTheme.colors.secondary.copy(alpha = 0.7f),
                unselectedContentColor = Color.White,
                alwaysShowLabel = false,
                modifier = Modifier.background(MaterialTheme.colors.primary)
            )
        }
    }
}
