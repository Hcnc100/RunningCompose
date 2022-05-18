package com.nullpointer.runningcompose.ui.screens.runs.componets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.ui.screens.empty.EmptyScreen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListRuns(
    listRuns: List<Run>?,
    listState: LazyListState,
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
            LazyVerticalGrid(
                cells = GridCells.Adaptive(250.dp),
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