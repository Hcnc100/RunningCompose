package com.nullpointer.runningcompose.ui.screens.statistics

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.models.StatisticsRun
import com.nullpointer.runningcompose.presentation.RunsViewModel
import com.nullpointer.runningcompose.ui.screens.empty.EmptyScreen
import com.nullpointer.runningcompose.ui.screens.statistics.componets.GraphRuns
import com.nullpointer.runningcompose.ui.screens.statistics.componets.StatisticsRuns
import com.nullpointer.runningcompose.ui.share.mpGraph.MpGraphAndroid
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination
fun StatisticsScreen(
    runsViewModel: RunsViewModel = hiltViewModel(),
) {
    val listRuns by runsViewModel.listRuns.collectAsState()
    val stateStatistics by runsViewModel.statisticsRuns.collectAsState()
    val isStatisticsLoad by runsViewModel.isStatisticsLoad.collectAsState(initial = true)
    val orientation = LocalConfiguration.current.orientation

    when {
        isStatisticsLoad -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        listRuns!!.isEmpty() -> {
            EmptyScreen(animation = R.raw.empty1,
                textEmpty = stringResource(R.string.message_empty_statisctis))
        }
        else -> {
            StatisticsAndGraph(
                orientation = orientation,
                listRuns = listRuns!!,
                statistics = stateStatistics!!)
        }
    }
}

@Composable
private fun StatisticsAndGraph(
    orientation: Int,
    listRuns: List<Run>,
    statistics: StatisticsRun,
) {
    Scaffold {
        when (orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                Column {
                    StatisticsRuns(statisticsRun = statistics,
                        modifier = Modifier
                            .weight(2F)
                            .fillMaxWidth())
                    GraphRuns(list = listRuns,
                        modifier = Modifier
                            .weight(6F)
                            .fillMaxWidth())
                }
            }
            else -> {
                Row {
                    StatisticsRuns(
                        statisticsRun = statistics,
                        modifier = Modifier
                            .weight(.4F)
                            .fillMaxHeight())
                    GraphRuns(
                        list = listRuns,
                        modifier = Modifier
                            .weight(.5f)
                            .fillMaxHeight()
                    )
                }
            }
        }
    }
}




