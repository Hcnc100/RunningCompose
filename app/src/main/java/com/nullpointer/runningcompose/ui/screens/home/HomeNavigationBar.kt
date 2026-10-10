package com.nullpointer.runningcompose.ui.screens.home

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import com.nullpointer.runningcompose.ui.navigation.HomeDestinations
import com.nullpointer.runningcompose.R

internal val HomeDestinations.navigationLabel: Int
    get() = when (this) {
        HomeDestinations.RunsScreen -> R.string.m3_nav_runs
        HomeDestinations.StatisticsScreen -> R.string.m3_nav_statistics
        HomeDestinations.ConfigScreens -> R.string.m3_nav_settings
    }

@Composable
internal fun HomeNavigationBar(
    selectedRoute: String?,
    onDestinationClick: (HomeDestinations) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(modifier = modifier, containerColor = MaterialTheme.colorScheme.surfaceContainer) {
        HomeDestinations.entries.forEach { destination ->
            NavigationBarItem(
                selected = selectedRoute == destination.destination.route,
                onClick = { onDestinationClick(destination) },
                icon = { Icon(ImageVector.vectorResource(destination.iconNavigation), contentDescription = null) },
                label = {
                    Text(stringResource(destination.navigationLabel), maxLines = 1, overflow = TextOverflow.Ellipsis)
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onSurface,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                ),
            )
        }
    }
}
