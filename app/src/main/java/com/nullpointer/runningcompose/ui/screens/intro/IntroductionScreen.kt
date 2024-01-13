package com.nullpointer.runningcompose.ui.screens.intro


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.nullpointer.runningcompose.ui.actions.IntroActions.*
import com.nullpointer.runningcompose.ui.actions.PermissionActions.*
import com.nullpointer.runningcompose.ui.navigation.MainNavGraph
import com.nullpointer.runningcompose.ui.preview.config.SimplePreview
import com.nullpointer.runningcompose.ui.screens.intro.componets.ButtonsPrevAndNext
import com.nullpointer.runningcompose.ui.screens.intro.componets.IntroductionPager
import com.nullpointer.runningcompose.ui.screens.intro.componets.PageIndicator
import com.nullpointer.runningcompose.ui.screens.intro.state.IntroductionState
import com.nullpointer.runningcompose.ui.screens.intro.state.rememberIntroductionScreenState
import com.nullpointer.runningcompose.ui.screens.intro.viewModel.IntroductionViewModel
import com.ramcosta.composedestinations.annotation.Destination

@ExperimentalPermissionsApi
@OptIn(ExperimentalFoundationApi::class)
@MainNavGraph
@Destination
@Composable
fun IntroductionScreen(
    introductionViewModel: IntroductionViewModel = hiltViewModel(),
    introductionState: IntroductionState = rememberIntroductionScreenState(
        countPager = if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) 3 else 2,
        actionAfterPermissionLocation = introductionViewModel::updateFirstLocationPermission,
        actionAfterPermissionNotification = introductionViewModel::updateFirstNotificationPermission,
    ),
) {

    val isFirstLocationPermission by introductionViewModel.isFirsRequestLocation.collectAsState()
    val isFirstNotificationPermission by introductionViewModel.isFirstRequestNotification.collectAsState()

    Scaffold(
        scaffoldState = introductionState.scaffoldState
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .background(MaterialTheme.colors.primary)
        ) {
            IntroductionPager(
                pagerState = introductionState.pagerState,
                modifier = Modifier.weight(1F),
                isFirstLocationPermission = isFirstLocationPermission,
                isFirstNotificationPermission = isFirstNotificationPermission,
                permissionAction = { action ->
                    when (action) {
                        OPEN_SETTING -> Unit
                        LAUNCH_LOCATION_PERMISSION -> introductionState.launchPermissionLocation(isFirstLocationPermission)
                        LAUNCH_NOTIFICATION_PERMISSION -> introductionState.launchPermissionNotification(isFirstNotificationPermission)
                    }
                }
            )
            Column(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PageIndicator(
                    colorCurrent = Color.Cyan,
                    colorOther = Color.White,
                    numberPages = introductionState.pageCount,
                    currentPage = introductionState.currentPager
                )
                ButtonsPrevAndNext(
                    isFirstPage = introductionState.isFirstPage,
                    isLastPage = introductionState.isLastPage,
                    actionIntro = { action ->
                        when (action) {
                            PREV -> introductionState.prevPage()
                            NEXT -> introductionState.nextPage()
                            START -> introductionViewModel.initApp()
                        }
                    }
                )
            }

        }

    }
}


@OptIn(ExperimentalPermissionsApi::class)
@SimplePreview
@Composable
fun IntroductionScreenPreview() {
    IntroductionScreen()
}