package com.nullpointer.runningcompose.ui.activitys

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.nullpointer.runningcompose.core.states.LoginStatus
import com.nullpointer.runningcompose.presentation.ConfigViewModel
import com.nullpointer.runningcompose.ui.screens.NavGraphs
import com.nullpointer.runningcompose.ui.screens.destinations.EditInfoScreenDestination
import com.nullpointer.runningcompose.ui.screens.destinations.RunsScreensDestination
import com.nullpointer.runningcompose.ui.states.rememberRootAppState
import com.nullpointer.runningcompose.ui.theme.RunningComposeTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val configViewModel: ConfigViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var loading = true
        val splash = installSplashScreen()
        splash.setKeepOnScreenCondition { loading }
        setContent {
            RunningComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colors.background
                ) {

                    val stateAuth by configViewModel.stateAuth.collectAsState()
                    val rootAppState = rememberRootAppState()

                    when (stateAuth) {
                        LoginStatus.Authenticating -> null
                        LoginStatus.Authenticated -> RunsScreensDestination
                        LoginStatus.Unauthenticated -> EditInfoScreenDestination
                    }?.let { startDestination ->
                        loading = false
                        DestinationsNavHost(
                            navGraph = NavGraphs.root,
                            startRoute = startDestination,
                            navController = rootAppState.navController,
                            engine = rootAppState.navHostEngine,
                            dependenciesContainerBuilder = {
                                dependency(configViewModel)
                                dependency(rootAppState.rootActions)
                            }
                        )
                    }
                }
            }
        }
    }
}
