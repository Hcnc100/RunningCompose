package com.nullpointer.runningcompose.ui.states

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Stable
class RunsScreenState(
    context: Context,
    scaffoldState: ScaffoldState,
    val lazyListState: LazyListState,
    val locationPermissionState: PermissionState
) : SimpleScreenState(context, scaffoldState) {
    fun showDialogPermission() {
        locationPermissionState.launchPermissionRequest()
    }

    fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:" + context.packageName)
        context.startActivity(intent)
    }


    fun showToast(@StringRes stringRes: Int) {
        Toast.makeText(context, context.getText(stringRes), Toast.LENGTH_SHORT).show()
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun rememberRunsScreenState(
    actionAfterPermission: () -> Unit,
    context: Context = LocalContext.current,
    lazyState: LazyListState = rememberLazyListState(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    locationPermissionState: PermissionState = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION,
        onPermissionResult = { actionAfterPermission() }
    ),
) = remember(scaffoldState, lazyState,locationPermissionState) {
    RunsScreenState(context, scaffoldState, lazyState, locationPermissionState)
}