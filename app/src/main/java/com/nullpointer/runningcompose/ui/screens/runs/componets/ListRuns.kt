package com.nullpointer.runningcompose.ui.screens.runs.componets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.ui.screens.empty.EmptyScreen

@Composable
fun ListRuns(
    listRuns: List<Run>?,
    listState: LazyGridState,
    actionClick: (Run) -> Unit,
    actionSelect: (Run) -> Unit,
    isSelectEnable: Boolean,
) {
    when {
        listRuns == null -> {
            LazyVerticalGrid(columns = GridCells.Adaptive(250.dp)) {
                items(10) { ItemRunFake() }
            }
        }
        listRuns.isEmpty() -> {
            EmptyScreen(animation = R.raw.empty1,
                textEmpty = stringResource(R.string.message_empty_runs))
        }
        else -> {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(250.dp),
                state = listState
            ) {
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