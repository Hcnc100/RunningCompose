package com.nullpointer.runningcompose.ui.activitys

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.presentation.ConfigViewModel
import com.nullpointer.runningcompose.presentation.RunsViewModel
import com.nullpointer.runningcompose.presentation.SelectViewModel
import com.nullpointer.runningcompose.ui.navigation.HomeNavigation
import com.nullpointer.runningcompose.ui.screens.NavGraphs
import com.nullpointer.runningcompose.ui.screens.destinations.EditInfoScreenDestination
import com.nullpointer.runningcompose.ui.screens.destinations.RunsScreensDestination
import com.nullpointer.runningcompose.ui.screens.navDestination
import com.nullpointer.runningcompose.ui.share.SelectToolbar
import com.nullpointer.runningcompose.ui.theme.RunningComposeTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.navigation.navigateTo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val selectViewModel: SelectViewModel by viewModels()
    private val runsViewModel: RunsViewModel by viewModels()
    private val configViewModel: ConfigViewModel by viewModels()
    private var isLoading = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                isLoading
            }
        }
        setContent {
            RunningComposeTheme {

                LaunchedEffect(key1 = Unit) {
                   configViewModel.isAuth.first { it != null }
                    isLoading = false
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    MainScreen(selectViewModel, runsViewModel, configViewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    selectViewModel: SelectViewModel,
    runsViewModel: RunsViewModel,
    configViewModel: ConfigViewModel,
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    var isHomeRoute by remember { mutableStateOf(false) }

    navController.addOnDestinationChangedListener { _, navDestination: NavDestination, _ ->
        isHomeRoute = HomeNavigation.isHomeRoute(navDestination.route)
    }

    Scaffold(
        topBar = {
            if (isHomeRoute)
                SelectToolbar(titleDefault = stringResource(id = R.string.app_name),
                    titleSelection = context.resources.getQuantityString(
                        R.plurals.selected_items,
                        selectViewModel.sizeSelected,
                        selectViewModel.sizeSelected),
                    numberSelection = selectViewModel.sizeSelected,
                    actionClear = selectViewModel::clearSelect)
        },
        bottomBar = {
            if (isHomeRoute)
                BottomBar(navController, selectViewModel::clearSelect)
        }
    ) {

        val isAuth by configViewModel.isAuth.collectAsState()

        if (isAuth != null) {
            val startRoute = if (isAuth as Boolean) NavGraphs.root.startRoute else EditInfoScreenDestination
            DestinationsNavHost(
                startRoute = startRoute,
                modifier = Modifier.padding(it),
                navGraph = NavGraphs.root,
                navController = navController,
                dependenciesContainerBuilder = {
                    dependency(selectViewModel)
                    dependency(runsViewModel)
                    dependency(configViewModel)
                })
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavController,
    clearSelection: () -> Unit,
) {
    val currentDestination = navController.currentBackStackEntryAsState()
        .value?.navDestination
    BottomNavigation {
        HomeNavigation.values().forEach { destination ->
            BottomNavigationItem(
                selected = currentDestination == destination.destination,
                onClick = {
                    // * clear selection if destination change
                    if (currentDestination != destination.destination)
                        clearSelection()

                    navController.navigateTo(destination.destination) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(painterResource(id = destination.iconNavigation),
                        contentDescription = stringResource(destination.titleShow))
                },
                label = { Text(stringResource(destination.titleShow)) },
            )
        }
    }
}
