package com.nullpointer.runningcompose.ui.screens.details

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.ui.navigation.MainNavGraph
import com.nullpointer.runningcompose.ui.screens.details.components.DetailsRunLandscape
import com.nullpointer.runningcompose.ui.screens.details.components.DetailsRunPortrait
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
                DetailsRunLandscape(
                    metricType = metricType,
                    itemsRun = itemsRun,
                    modifier = Modifier.padding(padding)
                )
            }

            ORIENTATION_PORTRAIT -> {
                DetailsRunPortrait(
                    itemsRun = itemsRun,
                    metricType = metricType,
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}

