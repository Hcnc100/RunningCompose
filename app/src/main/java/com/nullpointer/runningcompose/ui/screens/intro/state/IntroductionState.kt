package com.nullpointer.runningcompose.ui.screens.intro.state

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import com.nullpointer.runningcompose.ui.states.SimpleScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
@OptIn(ExperimentalFoundationApi::class, ExperimentalPermissionsApi::class)
class IntroductionState(
    context: Context,
    val pagerState: PagerState,
    scaffoldState: ScaffoldState,
    private val coroutineScope: CoroutineScope,
    val permissionsLocationState: PermissionState,
    val permissionsNotificationState: PermissionState
) : SimpleScreenState(context, scaffoldState) {

    val currentPager get() = pagerState.currentPage

    val isLastPage get() = pagerState.currentPage == pagerState.pageCount - 1

    val isFirstPage get() = pagerState.currentPage == 0

    val pageCount get() = pagerState.pageCount

    fun nextPage() = coroutineScope.launch {
        pagerState.animateScrollToPage(pagerState.currentPage + 1)
    }

    fun prevPage() = coroutineScope.launch {
        pagerState.animateScrollToPage(pagerState.currentPage - 1)
    }

    fun launchPermissionLocation(isFirst:Boolean) {
        when(isFirst){
            true -> permissionsLocationState.launchPermissionRequest()
            false -> openSettings()
        }

    }

    fun launchPermissionNotification(isFirst:Boolean) {
        when(isFirst){
            true -> permissionsNotificationState.launchPermissionRequest()
            false -> openSettings()
        }
    }

    private fun openSettings() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", context.packageName, null)
        )
        context.startActivity(intent)
    }
}


@OptIn(ExperimentalFoundationApi::class, ExperimentalPermissionsApi::class)
@Composable
fun rememberIntroductionScreenState(
    countPager: Int,
    context: Context = LocalContext.current,
    actionAfterPermissionLocation: () -> Unit,
    actionAfterPermissionNotification: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    pagerState: PagerState = rememberPagerState { countPager },
    permissionsLocationState: PermissionState = rememberPermissionState(
        permission = android.Manifest.permission.ACCESS_FINE_LOCATION,
        onPermissionResult = {actionAfterPermissionLocation()}
    ),
    permissionsNotificationState: PermissionState = rememberPermissionState(
        permission = android.Manifest.permission.POST_NOTIFICATIONS,
        onPermissionResult = {actionAfterPermissionNotification()}
    ),
) = remember(
    scaffoldState,
    pagerState,
    permissionsLocationState,
    permissionsNotificationState
) {
    IntroductionState(
        context = context,
        pagerState = pagerState,
        scaffoldState = scaffoldState,
        coroutineScope = coroutineScope,
        permissionsLocationState = permissionsLocationState,
        permissionsNotificationState = permissionsNotificationState
    )
}