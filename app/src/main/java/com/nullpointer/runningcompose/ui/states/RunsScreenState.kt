package com.nullpointer.runningcompose.ui.states

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
    val lazyGridState: LazyGridState,
    val notifyPermissionState: PermissionState,
    val locationPermissionState: PermissionState,
) : SimpleScreenState(context, scaffoldState) {
    fun showDialogLocationPermission() {
        locationPermissionState.launchPermissionRequest()
    }

    fun showDialogNotifyPermission() {
        notifyPermissionState.launchPermissionRequest()
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
    actionAfterLocationPermission: () -> Unit,
    actionAfterNotifyPermission: () -> Unit,
    context: Context = LocalContext.current,
    lazyState: LazyGridState = rememberLazyGridState(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    locationPermissionState: PermissionState = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION,
        onPermissionResult = { actionAfterLocationPermission() }
    ),
    notifyPermissionState: PermissionState = rememberPermissionState(
        Manifest.permission.POST_NOTIFICATIONS,
        onPermissionResult = { actionAfterNotifyPermission() }
    ),
) = remember(
    lazyState,
    scaffoldState,
    locationPermissionState
) {
    RunsScreenState(
        context = context,
        lazyGridState = lazyState,
        scaffoldState = scaffoldState,
        notifyPermissionState = notifyPermissionState,
        locationPermissionState = locationPermissionState,
    )
}