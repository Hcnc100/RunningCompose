package com.nullpointer.runningcompose.ui.screens.runs

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.presentation.ConfigViewModel
import com.nullpointer.runningcompose.presentation.RunsViewModel
import com.nullpointer.runningcompose.presentation.SelectViewModel
import com.nullpointer.runningcompose.ui.navigation.HomeNavGraph
import com.nullpointer.runningcompose.ui.screens.destinations.DetailsRunDestination
import com.nullpointer.runningcompose.ui.screens.destinations.TrackingScreenDestination
import com.nullpointer.runningcompose.ui.screens.runs.componets.ListRuns
import com.nullpointer.runningcompose.ui.share.ButtonToggleAddRemove
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalPermissionsApi::class)
@HomeNavGraph(start = true)
@Destination
@Composable
fun RunsScreens(
    runsViewModel: RunsViewModel,
    selectViewModel: SelectViewModel,
    configViewModel: ConfigViewModel,
    navigator: DestinationsNavigator,
) {
    val locationPermissionState = rememberPermissionState(
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
    val messageRuns = runsViewModel.messageRuns
    val scaffoldState = rememberScaffoldState()
    val stateList = rememberLazyGridState()
    val listRuns by runsViewModel.listRunsOrdered.collectAsState()
    val sortConfig by configViewModel.sortConfig.collectAsState()
    val context = LocalContext.current
    val (showDialogPermission, changeVisibilityDialog) = rememberSaveable { mutableStateOf(false) }
    val isFirstDialogRequest by configViewModel.isFirstLocationPermission.collectAsState()
    val metricType by configViewModel.metrics.collectAsState()

    LaunchedEffect(key1 = Unit) {
        messageRuns.filter { it != -1 }.collect {
            scaffoldState.snackbarHostState.showSnackbar(
                context.getString(it)
            )
        }
    }
    LaunchedEffect(key1 = Unit) {
        runsViewModel.listRunsOrdered.first { it != null }.let {
            if (!it.isNullOrEmpty()) selectViewModel.restoreSelect(it)
        }
    }
//    LaunchedEffect(key1 = locationPermissionState.status) {
//        val isFirstRequest = configViewModel.isFirstLocationPermission.first()
//        isFirstDialogRequest = isFirstRequest
//    }

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            ButtonToggleAddRemove(
                isVisible = !stateList.isScrollInProgress,
                isSelectedEnable = selectViewModel.isSelectEnable,
                descriptionButtonAdd = stringResource(R.string.description_button_add_run),
                actionAdd = {
                    when (locationPermissionState.status) {
                        PermissionStatus.Granted -> {
//                            navigator.navigate(TrackingScreenDestination)
                        }
                        is PermissionStatus.Denied -> {
                            changeVisibilityDialog(true)
                        }
                    }
                },
                descriptionButtonRemove = stringResource(R.string.description_deleter_select_runs),
                actionRemove = {
                    val listIds = selectViewModel.getListForDeleter()
                    runsViewModel.deleterListRun(listIds)
                }
            )
        }
    ) {
        ListRuns(
            listState = stateList,
            listRuns = listRuns,
            actionClick = { navigator.navigate(DetailsRunDestination.invoke(it, metricType)) },
            actionSelect = selectViewModel::changeSelect,
            isSelectEnable = selectViewModel.isSelectEnable,
            metricType = metricType,
            sortConfig = sortConfig,
            changeSort = configViewModel::changeSortConfig,
        )
    }

    if (showDialogPermission)
        DialogExplainPermission(
            isFirstRequestPermission = isFirstDialogRequest,
            changeFirstRequest = configViewModel::changeFirstRequestPermission,
            actionHidden = { changeVisibilityDialog(false) },
            actionAccept = locationPermissionState::launchPermissionRequest,
        )


    BackHandler(selectViewModel.isSelectEnable,
        selectViewModel::clearSelect)
}
