package com.nullpointer.runningcompose.ui.screens.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.types.MetricType

@Composable
fun DetailsRunPortrait(
    itemsRun: RunData,
    metricType: MetricType,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
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
            canExpanded = false,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )
    }
}