package com.nullpointer.runningcompose.ui.screens.runs

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.states.Resource
import com.nullpointer.runningcompose.core.utils.shareViewModel
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.entities.RunEntity
import com.nullpointer.runningcompose.models.data.config.SortConfig
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.models.types.SortType
import com.nullpointer.runningcompose.models.types.TrackingState
import com.nullpointer.runningcompose.presentation.ConfigViewModel
import com.nullpointer.runningcompose.presentation.RunsViewModel
import com.nullpointer.runningcompose.presentation.SelectViewModel
import com.nullpointer.runningcompose.ui.interfaces.ActionRootDestinations
import com.nullpointer.runningcompose.ui.navigation.HomeNavGraph
import com.nullpointer.runningcompose.ui.screens.destinations.DetailsRunDestination
import com.nullpointer.runningcompose.ui.screens.destinations.TrackingScreenDestination
import com.nullpointer.runningcompose.ui.screens.empty.LottieContainer
import com.nullpointer.runningcompose.ui.screens.runs.ActionRun.*
import com.nullpointer.runningcompose.ui.screens.runs.componets.ContainerPermission
import com.nullpointer.runningcompose.ui.screens.runs.componets.DropFilterAndOrder
import com.nullpointer.runningcompose.ui.screens.runs.componets.ListRuns
import com.nullpointer.runningcompose.ui.share.BlockProgress
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
    runsState: RunsScreenState = rememberRunsScreenState(
        actionAfterPermission = configViewModel::changeFirstRequestPermission
    )
) {

    val metricType by configViewModel.metrics.collectAsState()
    val numberRuns by runsViewModel.numberRuns.collectAsState()
    val sortConfig by configViewModel.sortConfig.collectAsState()
    val stateTracking by runsViewModel.stateTracking.collectAsState()
    val listRuns = runsViewModel.listRunsOrdered.collectAsLazyPagingItems()
    val isFirstDialogRequest by configViewModel.isFirstLocationPermission.collectAsState()
    val listRunsSelected= selectViewModel.listRunsSelected

    BackHandler(
        enabled = listRunsSelected.isNotEmpty(),
        onBack = selectViewModel::clearSelect
    )

    LaunchedEffect(key1 = Unit) {
        runsViewModel.messageRuns.collect(runsState::showSnackMessage)
    }



    RunsScreens(
        listRunsSelected = listRunsSelected,
        scaffoldState = runsState.scaffoldState,
        listRuns = listRuns,
        metricType = metricType,
        sortConfig = sortConfig,
        numberRuns = numberRuns,
        trackingState = stateTracking,
        lazyListState = runsState.lazyListState,
        changeSort = configViewModel::changeSortConfig,
        isSelectEnable = listRunsSelected.isNotEmpty(),
        permissionState = runsState.locationPermissionState,
        isFirstRequestPermission = Resource.Success(isFirstDialogRequest),
        actionRunScreen = { action, itemRun ->
            when (action) {
                DETAILS -> {
                    itemRun?.let {
                        actionRootDestinations.changeRoot(
                            DetailsRunDestination(itemRun, metricType)
                        )
                    }
                }
                SELECT -> itemRun?.let { selectViewModel.changeSelect(it) }
                OPEN_SETTING -> runsState.openSettings()
                LAUNCH_PERMISSION -> runsState.showDialogPermission()
                GO_TO_RUN -> actionRootDestinations.changeRoot(TrackingScreenDestination)
                DELETER -> {
                    val listIds = selectViewModel.getListForDeleter()
                    runsViewModel.deleterListRun(listIds)
                }
            }
        }
    )
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RunsScreens(
    scaffoldState: ScaffoldState,
    listRuns: LazyPagingItems<RunData>,
    metricType: MetricType,
    sortConfig: SortConfig,
    changeSort: (SortType?, Boolean?) -> Unit,
    isFirstRequestPermission: Resource<Boolean>,
    isSelectEnable: Boolean,
    trackingState: TrackingState,
    lazyListState: LazyListState,
    permissionState: PermissionState,
    actionRunScreen: (ActionRun, RunData?) -> Unit,
    numberRuns: Int,
    listRunsSelected: SnapshotStateMap<Long, RunData>,
) {


    when (isFirstRequestPermission) {
        Resource.Failure -> Unit
        Resource.Loading -> BlockProgress()
        is Resource.Success -> {
            if (permissionState.status.isGranted) {
                Scaffold(
                    scaffoldState = scaffoldState,
                    floatingActionButton = {
                        ButtonToggleAddRemove(
                            isSelectedEnable = isSelectEnable,
                            descriptionButtonAdd = stringResource(R.string.description_button_add_run),
                            trackingState = trackingState,
                            actionAdd = { actionRunScreen(GO_TO_RUN, null) },
                            descriptionButtonRemove = stringResource(R.string.description_deleter_select_runs),
                            actionRemove = { actionRunScreen(DELETER, null) }
                        )
                    }
                ) {
                    ListRuns(
                        modifier = Modifier.padding(it),
                        listState = lazyListState,
                        listRuns = listRuns,
                        isSelectEnable = isSelectEnable,
                        metricType = metricType,
                        actionRun = actionRunScreen,
                        numberRuns = numberRuns,
                        listRunSelected = listRunsSelected,
                        headerOrderAndFilter ={
                            DropFilterAndOrder(
                                sortConfig = sortConfig,
                                changeSort = changeSort
                            )
                        }
                    )
                }
            } else {
                ContainerPermission(
                    isFirstRequestPermission = isFirstRequestPermission.data,
                    actionRunScreen = actionRunScreen,
                )
            }
        }
    }
}

