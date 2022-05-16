package com.nullpointer.runningcompose.ui.screens.runs

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.presentation.RunsViewModel
import com.nullpointer.runningcompose.presentation.SelectViewModel
import com.nullpointer.runningcompose.ui.screens.destinations.DetailsRunDestination
import com.nullpointer.runningcompose.ui.screens.empty.EmptyScreen
import com.nullpointer.runningcompose.ui.screens.runs.componets.ItemRun
import com.nullpointer.runningcompose.ui.screens.runs.componets.ItemRunFake
import com.nullpointer.runningcompose.ui.share.ButtonToggleAddRemove
import com.nullpointer.runningcompose.ui.share.FabAnimation
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first

@Destination(start = true)
@Composable
fun RunsScreens(
    runsViewModel: RunsViewModel,
    selectViewModel: SelectViewModel,
    navigator: DestinationsNavigator,
) {
    val messageRuns = runsViewModel.messageRuns
    val scaffoldState = rememberScaffoldState()
    val stateList = rememberLazyListState()
    val listRunsState = runsViewModel.listRuns.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        messageRuns.filter { it != -1 }.collect {
            scaffoldState.snackbarHostState.showSnackbar(
                context.getString(it)
            )
        }
    }
    LaunchedEffect(key1 = Unit) {
        runsViewModel.listRuns.first { it != null }.let {
            if (!it.isNullOrEmpty()) selectViewModel.restoreSelect(it)
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            ButtonToggleAddRemove(isVisible = !stateList.isScrollInProgress,
                isSelectedEnable = selectViewModel.isSelectEnable,
                descriptionButtonAdd = stringResource(R.string.description_button_add_run),
                actionAdd = { runsViewModel.insertNewRun(Run.generateFake()) },
                descriptionButtonRemove = stringResource(R.string.description_deleter_select_runs),
                actionRemove = {
                    val listIds = selectViewModel.getListForDeleter()
                    runsViewModel.deleterListRun(listIds)
                }
            )
        }
    ) {
        ListRuns(listRuns = listRunsState.value,
            actionClick = { navigator.navigate(DetailsRunDestination.invoke(it)) },
            actionSelect = selectViewModel::changeSelect,
            isSelectEnable = selectViewModel.isSelectEnable)
    }

    BackHandler(selectViewModel.isSelectEnable,
        selectViewModel::clearSelect)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListRuns(
    listRuns: List<Run>?,
    actionClick: (Run) -> Unit,
    actionSelect: (Run) -> Unit,
    isSelectEnable: Boolean,
) {
    when {
        listRuns == null -> {
            LazyVerticalGrid(cells = GridCells.Adaptive(250.dp)) {
                items(10) { ItemRunFake() }
            }
        }
        listRuns.isEmpty() -> {
            EmptyScreen(animation = R.raw.empty1,
                textEmpty = stringResource(R.string.message_empty_runs))
        }
        else -> {
            LazyVerticalGrid(cells = GridCells.Adaptive(250.dp)) {
                items(listRuns.size) { index ->
                    ItemRun(
                        itemRun = listRuns[index],
                        actionClick = actionClick,
                        actionSelect = actionSelect,
                        isSelectEnable = isSelectEnable,
                    )
                }
            }
        }
    }

}