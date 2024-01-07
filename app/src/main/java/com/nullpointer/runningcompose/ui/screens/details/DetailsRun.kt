package com.nullpointer.runningcompose.ui.screens.details

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.entities.RunEntity
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.ui.navigation.MainNavGraph
import com.nullpointer.runningcompose.ui.screens.details.components.StatisticsRun
import com.nullpointer.runningcompose.ui.screens.runs.componets.MapRunItem
import com.nullpointer.runningcompose.ui.share.ToolbarBack
import com.nullpointer.runningcompose.ui.states.OrientationScreenState
import com.nullpointer.runningcompose.ui.states.rememberOrientationScreenState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@MainNavGraph
@Destination
@Composable
fun DetailsRun(
    itemsRun: RunData,
    metricType: MetricType,
    navigator: DestinationsNavigator,
    detailsState: OrientationScreenState = rememberOrientationScreenState()
) {

    Scaffold(
        topBar = {
            ToolbarBack(
                title = stringResource(R.string.title_details),
                actionBack = navigator::popBackStack
            )
        }
    ) { padding ->
        when (detailsState.orientation) {
            ORIENTATION_LANDSCAPE -> {
                Box {
                    MapRunItem(
                        itemRunEntity = itemsRun,
                        alignmentButton = Alignment.BottomEnd,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    )
                    StatisticsRun(
                        fontSizeBody = 12.sp,
                        fontSizeTitle = 14.sp,
                        itemRunEntity = itemsRun,
                        metricType = metricType,
                        isStatisticsExpanded = true,
                        modifier = Modifier
                            .width(250.dp)
                            .padding(5.dp)
                            .align(Alignment.TopStart)
                    )

                }
            }
            ORIENTATION_PORTRAIT -> {
                Column(modifier = Modifier.padding(padding)) {
                    MapRunItem(
                        itemRunEntity = itemsRun,
                        alignmentButton = Alignment.BottomEnd,
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .weight(6.5f)
                    )
                    StatisticsRun(
                        fontSizeTitle = 24.sp,
                        fontSizeBody = 16.sp,
                        itemRunEntity = itemsRun,
                        metricType = metricType,
                        isStatisticsExpanded = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    )
                }
            }
        }
    }
}

