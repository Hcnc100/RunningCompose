package com.nullpointer.runningcompose.ui.screens.runs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.presentation.RunsViewModel
import com.nullpointer.runningcompose.presentation.SelectViewModel
import com.nullpointer.runningcompose.ui.screens.empty.EmptyScreen
import com.nullpointer.runningcompose.ui.screens.runs.componets.ItemRun
import com.nullpointer.runningcompose.ui.screens.runs.componets.ItemRunFake
import com.nullpointer.runningcompose.ui.share.ButtonToggleAddRemove
import com.nullpointer.runningcompose.ui.share.FabAnimation

@Composable
fun RunsScreens(
    runsViewModel: RunsViewModel = hiltViewModel(),
    selectViewModel: SelectViewModel = hiltViewModel(),
) {
    val messageRuns = runsViewModel.messageRuns
    val scaffoldState = rememberScaffoldState()
    val stateList = rememberLazyListState()
    val listRunsState = runsViewModel.listRuns.collectAsState()

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            ButtonToggleAddRemove(isVisible = !stateList.isScrollInProgress,
                isSelectedEnable = selectViewModel.isSelectEnable,
                descriptionButtonAdd = stringResource(R.string.description_button_add_run),
                actionAdd = { /*TODO*/ },
                descriptionButtonRemove = stringResource(R.string.description_deleter_select_runs),
                actionRemove = {}
            )
        }
    ) {
        ListRuns(listRuns = listRunsState.value, actionClick = {}, actionLongClick = {})
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListRuns(
    listRuns: List<Run>?,
    actionClick: (Run) -> Unit,
    actionLongClick: (Run) -> Unit,
) {
    when {
        listRuns == null -> {
            LazyVerticalGrid(cells = GridCells.Adaptive(250.dp)) {
                items(10) { ItemRunFake() }
            }
        }
//        listRuns.isEmpty() -> {
//            EmptyScreen(animation = R.raw.empty1,
//                textEmpty = stringResource(R.string.message_empty_runs))
//        }
        else -> {
            LazyVerticalGrid(cells = GridCells.Adaptive(250.dp)) {
                items(10) { index ->
                    ItemRun(
                        itemRun = Run(),
                        actionClick = actionClick)
                }
            }
        }
    }

}