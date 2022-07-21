package com.nullpointer.runningcompose.ui.states

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
class RunsScreenState  constructor(
    context: Context,
    focusManager: FocusManager,
    scaffoldState: ScaffoldState,
    val lazyGridState: LazyGridState,
    private val locationPermissionState: PermissionState
) : SimpleScreenState(scaffoldState, context, focusManager){

    val isScrollInProgress get() = lazyGridState.isScrollInProgress
    val permissionState get() = locationPermissionState.status


    fun showDialogPermission(){
        locationPermissionState.launchPermissionRequest()
    }

    fun showToast(@StringRes stringRes:Int){
        Toast.makeText(context,context.getText(stringRes),Toast.LENGTH_SHORT).show()
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun rememberRunsScreenState(
    context: Context = LocalContext.current,
    focusManager: FocusManager = LocalFocusManager.current,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    lazyState: LazyGridState = rememberLazyGridState(),
    locationPermissionState: PermissionState = rememberPermissionState(
        Manifest.permission.ACCESS_FINE_LOCATION
    )
) = remember(scaffoldState, lazyState,locationPermissionState) {
    RunsScreenState(context, focusManager, scaffoldState, lazyState,locationPermissionState)
}