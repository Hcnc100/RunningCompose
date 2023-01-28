package com.nullpointer.runningcompose.ui.screens.runs

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.utils.shareViewModel
import com.nullpointer.runningcompose.presentation.ConfigViewModel
import com.nullpointer.runningcompose.presentation.RunsViewModel
import com.nullpointer.runningcompose.presentation.SelectViewModel
import com.nullpointer.runningcompose.ui.interfaces.ActionRootDestinations
import com.nullpointer.runningcompose.ui.navigation.HomeNavGraph
import com.nullpointer.runningcompose.ui.screens.destinations.DetailsRunDestination
import com.nullpointer.runningcompose.ui.screens.destinations.TrackingScreenDestination
import com.nullpointer.runningcompose.ui.screens.runs.ActionRun.DETAILS
import com.nullpointer.runningcompose.ui.screens.runs.ActionRun.SELECT
import com.nullpointer.runningcompose.ui.screens.runs.componets.ListRuns
import com.nullpointer.runningcompose.ui.share.ButtonToggleAddRemove
import com.nullpointer.runningcompose.ui.states.RunsScreenState
import com.nullpointer.runningcompose.ui.states.rememberRunsScreenState
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalPermissionsApi::class)
@HomeNavGraph(start = true)
@Destination
@Composable
fun RunsScreens(
    selectViewModel: SelectViewModel,
    configViewModel: ConfigViewModel,
    actionRootDestinations: ActionRootDestinations,
    runsViewModel: RunsViewModel = shareViewModel(),
    runsState: RunsScreenState = rememberRunsScreenState()
) {

    val metricType by configViewModel.metrics.collectAsState()
    val sortConfig by configViewModel.sortConfig.collectAsState()
    val listRuns = runsViewModel.listRunsOrdered.collectAsLazyPagingItems()
    val stateTracking by runsViewModel.stateTracking.collectAsState()
    val isFirstDialogRequest by configViewModel.isFirstLocationPermission.collectAsState()

    val (showDialogPermission, changeVisibilityDialog) = rememberSaveable { mutableStateOf(false) }

    BackHandler(
        selectViewModel.isSelectEnable,
        selectViewModel::clearSelect
    )

    LaunchedEffect(key1 = Unit) {
        runsViewModel.messageRuns.collect(runsState::showSnackMessage)
    }
//    LaunchedEffect(key1 = Unit) {
//        runsViewModel.listRunsOrdered.first { it is Resource.Success }.let {
//            val firstListRuns = it as Resource.Success
//            if (firstListRuns.data.isNotEmpty()) selectViewModel.restoreSelect(firstListRuns.data)
//        }
//    }

    Scaffold(
        scaffoldState = runsState.scaffoldState,
        floatingActionButton = {
            ButtonToggleAddRemove(
                isVisible = !runsState.isScrollInProgress,
                isSelectedEnable = selectViewModel.isSelectEnable,
                descriptionButtonAdd = stringResource(R.string.description_button_add_run),
                trackingState = stateTracking,
                actionAdd = {
                    when (runsState.permissionState) {
                        PermissionStatus.Granted -> {
                            actionRootDestinations.changeRoot(TrackingScreenDestination)
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
            modifier = Modifier.padding(it),
            listState = runsState.lazyListState,
            listRuns = listRuns,
            isSelectEnable = selectViewModel.isSelectEnable,
            metricType = metricType,
            sortConfig = sortConfig,
            changeSort = configViewModel::changeSortConfig,
            actionRun = { action, itemRun ->
                when (action) {
                    DETAILS -> {
                        actionRootDestinations.changeRoot(
                            DetailsRunDestination(itemRun, metricType)
                        )
                    }
                    SELECT -> selectViewModel.changeSelect(itemRun)
                }
            }
        )
    }

    if (showDialogPermission)
        DialogExplainPermission(
            isFirstRequestPermission = isFirstDialogRequest,
            changeFirstRequest = configViewModel::changeFirstRequestPermission,
            actionHidden = { changeVisibilityDialog(false) },
            actionAccept = runsState::showDialogPermission,
        )
}
