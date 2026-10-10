package com.nullpointer.runningcompose.ui.screens.runs.componets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.ui.actions.RunActions
import com.nullpointer.runningcompose.ui.screens.runs.componets.itemRun.ItemRun
import com.nullpointer.runningcompose.ui.share.material3.RunningErrorMessage
import com.nullpointer.runningcompose.ui.theme.RunningSpacing

@Composable
fun ListRuns(
    numberRuns: Int,
    metricType: MetricType,
    isSelectEnable: Boolean,
    listState: LazyGridState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    listRuns: LazyPagingItems<RunData>,
    actionRun: (RunActions, RunData) -> Unit,
    listRunSelected: Map<Long, RunData>,
    headerOrderAndFilter: @Composable () -> Unit,
) {
    val refresh = listRuns.loadState.refresh
    val contentModifier = modifier.fillMaxSize().padding(contentPadding)
    when {
        refresh is LoadState.Error && listRuns.itemCount == 0 -> {
            Column(contentModifier.padding(RunningSpacing.medium),
                verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                RunsLoadError { listRuns.retry() }
            }
        }
        numberRuns < 0 || (refresh is LoadState.Loading && listRuns.itemCount == 0) -> {
            Box(contentModifier, contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        }
        numberRuns == 0 && listRuns.itemCount == 0 -> EmptyRuns(contentModifier)
        else -> Column(contentModifier) {
            Column(Modifier.padding(RunningSpacing.medium)) {
                Text(stringResource(R.string.m3_runs_heading), style = MaterialTheme.typography.titleLarge)
                Text(pluralStringResource(R.plurals.m3_runs_count, numberRuns, numberRuns),
                    style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            if (!isSelectEnable) headerOrderAndFilter()
            LazyVerticalGrid(
                modifier = Modifier.weight(1f),
                state = listState,
                columns = GridCells.Adaptive(minSize = 320.dp),
                contentPadding = PaddingValues(RunningSpacing.medium),
                horizontalArrangement = Arrangement.spacedBy(RunningSpacing.medium),
                verticalArrangement = Arrangement.spacedBy(RunningSpacing.medium),
            ) {
                if (refresh is LoadState.Error) {
                    item(span = { GridItemSpan(maxLineSpan) }) { RunsLoadError { listRuns.retry() } }
                }
                items(listRuns.itemCount, key = listRuns.itemKey { it.id },
                    contentType = listRuns.itemContentType { "run" }) { index ->
                    val run = listRuns[index]
                    if (run != null) {
                        ItemRun(
                            isSelect = listRunSelected.containsKey(run.id), runData = run,
                            actionRun = { actionRun(it, run) }, isSelectEnable = isSelectEnable,
                            metricType = metricType, modifier = Modifier.animateItem(),
                        )
                    } else {
                        // Paging placeholders may be null; never force-unwrap them.
                        Box(Modifier.fillMaxWidth().height(120.dp))
                    }
                }
                when (listRuns.loadState.append) {
                    is LoadState.Loading -> item(span = { GridItemSpan(maxLineSpan) }) {
                        Box(Modifier.fillMaxWidth().padding(RunningSpacing.medium), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                    is LoadState.Error -> item(span = { GridItemSpan(maxLineSpan) }) { RunsLoadError { listRuns.retry() } }
                    else -> Unit
                }
            }
        }
    }
}

@Composable
private fun RunsLoadError(onRetry: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        RunningErrorMessage(stringResource(R.string.error_load_runs))
        TextButton(onClick = onRetry) { Text(stringResource(R.string.m3_retry)) }
    }
}
