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
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.presentation.RunsViewModel
import com.nullpointer.runningcompose.presentation.SelectViewModel
import com.nullpointer.runningcompose.ui.actions.PermissionActions
import com.nullpointer.runningcompose.ui.actions.RunActions
import com.nullpointer.runningcompose.ui.actions.RunActions.DETAILS
import com.nullpointer.runningcompose.ui.actions.RunActions.SELECT
import com.nullpointer.runningcompose.ui.interfaces.ActionRootDestinations
import com.nullpointer.runningcompose.ui.navigation.HomeNavGraph
import com.nullpointer.runningcompose.ui.screens.config.viewModel.ConfigViewModel
import com.nullpointer.runningcompose.ui.screens.destinations.DetailsRunDestination
import com.nullpointer.runningcompose.ui.screens.destinations.TrackingScreenDestination
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
    actionRootDestinations: ActionRootDestinations,
    runsViewModel: RunsViewModel = hiltViewModel(),
    configViewModel: ConfigViewModel = hiltViewModel(),
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
    val listRunsSelected = selectViewModel.listRunsSelected
    val isSelectEnable = listRunsSelected.isNotEmpty()

    BackHandler(
        enabled = listRunsSelected.isNotEmpty(),
        onBack = selectViewModel::clearSelect
    )

    LaunchedEffect(key1 = Unit) {
        runsViewModel.messageRuns.collect(runsState::showSnackMessage)
    }



    RunsScreens(
        listRuns = listRuns,
        numberRuns = numberRuns,
        metricType = metricType,
        isSelectEnable = isSelectEnable,
        listRunsSelected = listRunsSelected,
        scaffoldState = runsState.scaffoldState,
        lazyGridState = runsState.lazyGridState,
        permissionState = runsState.locationPermissionState,
        isFirstRequestPermission = Resource.Success(isFirstDialogRequest),
        actionRunScreen = { action, itemRun ->
            when (action) {
                DETAILS -> actionRootDestinations.changeRoot(
                    DetailsRunDestination(itemRun, metricType)
                )

                SELECT -> selectViewModel.changeSelect(itemRun)
            }
        },
        permissionAction = { permissionAction ->
            when (permissionAction) {
                PermissionActions.OPEN_SETTING -> runsState.openSettings()
                PermissionActions.LAUNCH_PERMISSION -> runsState.showDialogPermission()
            }
        },
        headerSorterAndFilter = {
            DropFilterAndOrder(
                sortConfig = sortConfig,
                changeSort = configViewModel::changeSortConfig
            )
        },
        buttonPauseResume = {
            ButtonToggleAddRemove(
                trackingState = stateTracking,
                isSelectedEnable = isSelectEnable,
                descriptionButtonAdd = stringResource(R.string.description_button_add_run),
                descriptionButtonRemove = stringResource(R.string.description_deleter_select_runs),
                actionAdd = { actionRootDestinations.changeRoot(TrackingScreenDestination) },
                actionRemove = {
                    val listIds = selectViewModel.getListForDeleter()
                    runsViewModel.deleterListRun(listIds)
                }
            )
        },
    )
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RunsScreens(
    numberRuns: Int,
    metricType: MetricType,
    isSelectEnable: Boolean,
    scaffoldState: ScaffoldState,
    lazyGridState: LazyGridState,
    permissionState: PermissionState,
    listRuns: LazyPagingItems<RunData>,
    buttonPauseResume: @Composable () -> Unit,
    isFirstRequestPermission: Resource<Boolean>,
    headerSorterAndFilter: @Composable () -> Unit,
    actionRunScreen: (RunActions, RunData) -> Unit,
    permissionAction: (PermissionActions) -> Unit,
    listRunsSelected: Map<Long, RunData>
) {


    when (isFirstRequestPermission) {
        Resource.Failure -> Unit
        Resource.Loading -> BlockProgress()
        is Resource.Success -> {
            if (permissionState.status.isGranted) {
                Scaffold(
                    scaffoldState = scaffoldState,
                    floatingActionButton = buttonPauseResume
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
                        headerOrderAndFilter = headerSorterAndFilter
                    )
                }
            } else {
                ContainerPermission(
                    permissionAction = permissionAction,
                    isFirstRequestPermission = isFirstRequestPermission.data
                )
            }
        }
    }
}

