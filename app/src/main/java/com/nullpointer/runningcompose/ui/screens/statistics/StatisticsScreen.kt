package com.nullpointer.runningcompose.ui.screens.statistics

import android.content.res.Configuration
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nullpointer.runningcompose.core.utils.toFullFormatTime
import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.models.StatisticsRun
import com.nullpointer.runningcompose.presentation.RunsViewModel
import com.nullpointer.runningcompose.ui.share.mpGraph.MpGraphAndroid

@Composable
fun StatisticsScreen(
    runsViewModel: RunsViewModel = hiltViewModel(),
) {
    val stateRuns = runsViewModel.listRuns.collectAsState()
    val stateStatistics = runsViewModel.statisticsRuns.collectAsState()
    val orientation = LocalConfiguration.current.orientation
    Scaffold {
        when (orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                Column(modifier = Modifier.padding(vertical = 20.dp, horizontal = 10.dp)) {
                    StatisticsRuns(statisticsRun = stateStatistics.value,
                        modifier = Modifier
                            .weight(2F)
                            .fillMaxWidth())
                    Text("Grafico de estadisticas", modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                        textAlign = TextAlign.Center)
                    GraphRuns(list = stateRuns.value, modifier = Modifier
                        .weight(6F)
                        .fillMaxWidth())
                }
            }
            else -> {
                Row(modifier = Modifier.padding(vertical = 20.dp, horizontal = 10.dp)) {
                    StatisticsRuns(statisticsRun = stateStatistics.value,
                        modifier = Modifier
                            .weight(.4F)
                            .fillMaxHeight())
                    Column(modifier = Modifier
                        .weight(.5f)
                        .fillMaxHeight()) {
                        Text("Grafico de estadisticas",
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center)
                        GraphRuns(
                            list = stateRuns.value,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                }
            }
        }

    }
}

@Composable
fun StatisticsRuns(
    statisticsRun: StatisticsRun?,
    modifier: Modifier = Modifier,
) {
    if (statisticsRun == null) {
        Box(modifier = modifier, contentAlignment = Alignment.Center){
            CircularProgressIndicator()
        }
    } else {
        Column(modifier = modifier, verticalArrangement = Arrangement.SpaceEvenly) {
            Row(modifier = Modifier.fillMaxWidth()) {
                TextInfo(titleInfo = "Tiempo total",
                    valueInfo = statisticsRun.timeRun.toFullFormatTime(true),
                    modifier = Modifier.weight(.5f))
                TextInfo(titleInfo = "Distancia total",
                    valueInfo = statisticsRun.distance.toString(),
                    modifier = Modifier.weight(.5f))
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                TextInfo(titleInfo = "Calorias Quemadas totales",
                    valueInfo = statisticsRun.caloriesBurned.toString(),
                    modifier = Modifier.weight(.5f))
                TextInfo(titleInfo = "Velocidad media m/s",
                    valueInfo = statisticsRun.AVGSpeed.toString(),
                    modifier = Modifier.weight(.5f))
            }
        }

    }

}

@Composable
fun GraphRuns(
    list: List<Run>?,
    modifier: Modifier = Modifier,
) {

    when {
        list == null -> {

        }
        list.isEmpty() -> {

        }
        else -> {
            MpGraphAndroid(list = list, modifier = modifier)
        }
    }
}

@Composable
fun TextInfo(titleInfo: String, valueInfo: String, modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Text(text = valueInfo,
            style = MaterialTheme.typography.h5,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = titleInfo,
            style = MaterialTheme.typography.caption,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis, textAlign = TextAlign.Center)
    }

}