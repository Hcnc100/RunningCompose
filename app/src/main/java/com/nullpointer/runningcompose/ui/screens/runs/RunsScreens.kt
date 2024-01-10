package com.nullpointer.runningcompose.ui.screens.runs

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.states.Resource
import com.nullpointer.runningcompose.core.utils.shareViewModel
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.data.config.SortConfig
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.models.types.SortType
import com.nullpointer.runningcompose.models.types.TrackingState
import com.nullpointer.runningcompose.presentation.RunsViewModel
import com.nullpointer.runningcompose.presentation.SelectViewModel
import com.nullpointer.runningcompose.ui.interfaces.ActionRootDestinations
import com.nullpointer.runningcompose.ui.navigation.HomeNavGraph
import com.nullpointer.runningcompose.ui.screens.config.viewModel.ConfigViewModel
import com.nullpointer.runningcompose.ui.screens.destinations.DetailsRunDestination
import com.nullpointer.runningcompose.ui.screens.destinations.TrackingScreenDestination
import com.nullpointer.runningcompose.ui.screens.runs.ActionRun.DELETER
import com.nullpointer.runningcompose.ui.screens.runs.ActionRun.DETAILS
import com.nullpointer.runningcompose.ui.screens.runs.ActionRun.GO_TO_RUN
import com.nullpointer.runningcompose.ui.screens.runs.ActionRun.LAUNCH_PERMISSION
import com.nullpointer.runningcompose.ui.screens.runs.ActionRun.OPEN_SETTING
import com.nullpointer.runningcompose.ui.screens.runs.ActionRun.SELECT
import com.nullpointer.runningcompose.ui.screens.runs.componets.ContainerPermission
import com.nullpointer.runningcompose.ui.screens.runs.componets.ListRuns
import com.nullpointer.runningcompose.ui.screens.runs.componets.filters.DropFilterAndOrder
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
    configViewModel: ConfigViewModel= hiltViewModel(),
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
        lazyGridState = runsState.lazyGridState,
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
    numberRuns: Int,
    metricType: MetricType,
    sortConfig: SortConfig,
    isSelectEnable: Boolean,
    trackingState: TrackingState,
    scaffoldState: ScaffoldState,
    lazyGridState: LazyGridState,
    permissionState: PermissionState,
    listRuns: LazyPagingItems<RunData>,
    changeSort: (SortType?, Boolean?) -> Unit,
    isFirstRequestPermission: Resource<Boolean>,
    actionRunScreen: (ActionRun, RunData?) -> Unit,
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
                        listState = lazyGridState,
                        listRuns = listRuns,
                        isSelectEnable = isSelectEnable,
                        metricType = metricType,
                        actionRun = actionRunScreen,
                        numberRuns = numberRuns,
                        listRunSelected = listRunsSelected,
                        headerOrderAndFilter = {
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

