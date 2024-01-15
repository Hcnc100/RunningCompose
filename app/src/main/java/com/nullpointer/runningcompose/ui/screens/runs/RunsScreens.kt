package com.nullpointer.runningcompose.ui.screens.runs

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
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
import com.google.accompanist.permissions.PermissionStatus
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.states.Resource
import com.nullpointer.runningcompose.models.data.PermissionsData
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.data.config.SortConfig
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.models.types.SortType
import com.nullpointer.runningcompose.models.types.TrackingState
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
import com.nullpointer.runningcompose.ui.screens.runs.componets.ListRuns
import com.nullpointer.runningcompose.ui.screens.runs.componets.filters.DropFilterAndOrder
import com.nullpointer.runningcompose.ui.screens.tracking.componets.ContainerPermissionLocation
import com.nullpointer.runningcompose.ui.screens.tracking.componets.ContainerPermissionNotify
import com.nullpointer.runningcompose.ui.share.BlockProgress
import com.nullpointer.runningcompose.ui.share.ButtonToggleAddRemove
import com.nullpointer.runningcompose.ui.states.RunsScreenState
import com.nullpointer.runningcompose.ui.states.rememberRunsScreenState
import com.ramcosta.composedestinations.annotation.Destination
import timber.log.Timber

@OptIn(ExperimentalPermissionsApi::class)
@HomeNavGraph(start = true)
@Destination
@Composable
fun RunsScreens(
    selectViewModel: SelectViewModel,
    actionRootDestinations: ActionRootDestinations,
    runsViewModel: RunsViewModel = hiltViewModel(),
    runsState: RunsScreenState = rememberRunsScreenState(
        actionAfterLocationPermission = runsViewModel::changeFirstRequestLocationPermission,
        actionAfterNotifyPermission = runsViewModel::changeFirstRequestNotifyPermission
    )
) {

    val metricType by runsViewModel.metricType.collectAsState()
    val numberRuns by runsViewModel.numberRuns.collectAsState()
    val sortConfig by runsViewModel.sortConfig.collectAsState()
    val stateTracking by runsViewModel.stateTracking.collectAsState()
    val listRuns = runsViewModel.listRunsOrdered.collectAsLazyPagingItems()
    val listRunsSelected = selectViewModel.listRunsSelected
    val isSelectEnable = listRunsSelected.isNotEmpty()
    val permissionsDataState by runsViewModel.permissionDataState.collectAsState()


    LaunchedEffect(key1 = Unit){
        runsViewModel.metricType.collect{
            Timber.d("Metric type in screen $it")
        }
    }


    BackHandler(
        enabled = listRunsSelected.isNotEmpty(),
        onBack = selectViewModel::clearSelect
    )

    LaunchedEffect(key1 = Unit) {
        runsViewModel.messageRuns.collect(runsState::showSnackMessage)
    }

    ContainerTrackingPermissions(
        permissionsDataState = permissionsDataState,
        permissionState = runsState.locationPermissionState,
        notifyPermissionState = runsState.notifyPermissionState,
        permissionAction = { action ->
            when (action) {
                PermissionActions.LAUNCH_LOCATION_PERMISSION -> runsState.showDialogLocationPermission()
                PermissionActions.LAUNCH_NOTIFICATION_PERMISSION -> runsState.showDialogNotifyPermission()
                PermissionActions.OPEN_SETTING -> runsState.openSettings()
            }
        },
    ) {
        RunsScreens(
            listRuns = listRuns,
            numberRuns = numberRuns,
            metricType = metricType,
            sortConfig = sortConfig,
            stateTracking = stateTracking,
            isSelectEnable = isSelectEnable,
            listRunsSelected = listRunsSelected,
            scaffoldState = runsState.scaffoldState,
            lazyGridState = runsState.lazyGridState,
            changeSort = runsViewModel::changeSortConfig,
            actionAddRun = {
                actionRootDestinations.changeRoot(TrackingScreenDestination)
            },
            actionRemoveRun = {
                val listIds = selectViewModel.getListForDeleter()
                runsViewModel.deleterListRun(listIds)
            },
            actionRunScreen = { action, itemRun ->
                when (action) {
                    DETAILS -> actionRootDestinations.changeRoot(
                        DetailsRunDestination(
                            itemRun,
                            metricType
                        )
                    )

                    SELECT -> selectViewModel.changeSelect(itemRun)
                }
            }
        )
    }


}
@Composable
fun RunsScreens(
    numberRuns: Int,
    metricType: MetricType,
    isSelectEnable: Boolean,
    scaffoldState: ScaffoldState,
    lazyGridState: LazyGridState,
    listRuns: LazyPagingItems<RunData>,
    actionRunScreen: (RunActions, RunData) -> Unit,
    listRunsSelected: Map<Long, RunData>,
    stateTracking: TrackingState,
    actionAddRun: () -> Unit,
    actionRemoveRun: () -> Unit,
    sortConfig: SortConfig,
    changeSort: (SortType?, Boolean?) -> Unit,
) {

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            ButtonToggleAddRemove(
                actionAdd = actionAddRun,
                trackingState = stateTracking,
                actionRemove = actionRemoveRun,
                isSelectedEnable = isSelectEnable,
                descriptionButtonAdd = stringResource(R.string.description_button_add_run),
                descriptionButtonRemove = stringResource(R.string.description_deleter_select_runs)
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

}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ContainerTrackingPermissions(
    permissionState: PermissionState,
    notifyPermissionState: PermissionState,
    permissionAction: (PermissionActions) -> Unit,
    permissionsDataState: Resource<PermissionsData>,
    content: @Composable () -> Unit
) {
    when (permissionsDataState) {
        Resource.Loading -> BlockProgress()
        Resource.Failure -> Text(text = "Error")
        is Resource.Success -> when (permissionState.status) {
            is PermissionStatus.Denied -> ContainerPermissionLocation(
                isFirstRequestPermission = permissionsDataState.data.isFirstRequestLocation,
                permissionAction = permissionAction
            )

            PermissionStatus.Granted -> when (notifyPermissionState.status) {
                is PermissionStatus.Denied -> ContainerPermissionNotify(
                    isFirstRequestPermission = permissionsDataState.data.isFirstRequestNotification,
                    permissionAction = permissionAction
                )

                PermissionStatus.Granted -> content()
            }
        }
    }


}






