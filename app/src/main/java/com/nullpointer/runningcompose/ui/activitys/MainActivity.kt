package com.nullpointer.runningcompose.ui.activitys

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.nullpointer.runningcompose.ui.screens.main.MainScreen
import com.nullpointer.runningcompose.ui.theme.RunningComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

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
                    MainScreen(actionSuccess = { loading = false })
                }
            }
        }
    }
}
