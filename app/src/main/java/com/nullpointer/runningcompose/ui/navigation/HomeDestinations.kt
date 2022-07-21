package com.nullpointer.runningcompose.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.ui.screens.destinations.ConfigScreenDestination
import com.nullpointer.runningcompose.ui.screens.destinations.Destination
import com.nullpointer.runningcompose.ui.screens.destinations.RunsScreensDestination
import com.nullpointer.runningcompose.ui.screens.destinations.StatisticsScreenDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

enum class HomeDestinations(
    @StringRes
    val titleShow: Int,
    @DrawableRes
    val iconNavigation: Int,
    val destination: DirectionDestinationSpec,
) {
    RunsScreen(
        R.string.title_screen_runs,
        R.drawable.ic_run_nav,
        RunsScreensDestination
    ),
    StatisticsScreen(
        R.string.title_screen_statistics,
        R.drawable.ic_graph,
        StatisticsScreenDestination
    ),
    ConfigScreens(
        R.string.title_screen_config,
        R.drawable.ic_settings,
        ConfigScreenDestination
    );

    companion object {
        fun isHomeRoute(route: String?): Boolean {
            if (route == null) return false
            return values().find { it.destination.route == route } != null
        }
    }
}