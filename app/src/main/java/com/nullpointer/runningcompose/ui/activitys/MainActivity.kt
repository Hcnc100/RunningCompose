package com.nullpointer.runningcompose.ui.activitys

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.presentation.RunsViewModel
import com.nullpointer.runningcompose.presentation.SelectViewModel
import com.nullpointer.runningcompose.ui.screens.NavGraph
import com.nullpointer.runningcompose.ui.screens.NavGraphs
import com.nullpointer.runningcompose.ui.screens.runs.RunsScreens
import com.nullpointer.runningcompose.ui.screens.statistics.StatisticsScreen
import com.nullpointer.runningcompose.ui.share.SelectToolbar
import com.nullpointer.runningcompose.ui.theme.RunningComposeTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val selectViewModel: SelectViewModel by viewModels()
    private val runsViewModel: RunsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RunningComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
//                    MainScreen(selectViewModel, runsViewModel)
                    StatisticsScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    selectViewModel: SelectViewModel,
    runsViewModel: RunsViewModel,
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            SelectToolbar(titleDefault = stringResource(id = R.string.app_name),
                titleSelection = context.resources.getQuantityString(
                    R.plurals.selected_items,
                    selectViewModel.sizeSelected,
                    selectViewModel.sizeSelected),
                numberSelection = selectViewModel.sizeSelected,
                actionClear = selectViewModel::clearSelect)
        }
    ) {
        DestinationsNavHost(
            navGraph = NavGraphs.root,
            navController = navController,
            dependenciesContainerBuilder = {
                dependency(selectViewModel)
                dependency(runsViewModel)
            })
    }
}

