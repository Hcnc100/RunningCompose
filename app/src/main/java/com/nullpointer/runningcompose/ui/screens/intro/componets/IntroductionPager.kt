package com.nullpointer.runningcompose.ui.screens.intro.componets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nullpointer.runningcompose.ui.actions.PermissionActions
import com.nullpointer.runningcompose.ui.preview.config.SimplePreview

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IntroductionPager(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    permissionAction: (PermissionActions) -> Unit,
    isFirstLocationPermission: Boolean,
    isFirstNotificationPermission: Boolean
) {



    HorizontalPager(
        modifier = modifier,
        state = pagerState,
    ) { page ->
        when (page) {
            0 -> DescriptionScreen()
            1 -> LocationScreen(
                permissionAction = permissionAction,
                isFirstLocationPermission = isFirstLocationPermission
            )
            2 -> NotifyScreen(
                permissionAction = permissionAction,
                isFirstNotificationPermission = isFirstNotificationPermission
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@SimplePreview
@Composable
fun IntroductionPagerPreview() {
    IntroductionPager(
        pagerState = rememberPagerState { 3 },
        permissionAction = {},
        isFirstLocationPermission = true,
        isFirstNotificationPermission = false
    )
}