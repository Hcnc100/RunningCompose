package com.nullpointer.runningcompose.ui.screens.details.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.types.MetricType

@Composable
fun DetailsRunLandscape(
    itemsRun: RunData,
    metricType: MetricType,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        MapRunItem(
            itemRunEntity = itemsRun,
            alignmentButton = Alignment.BottomEnd,
            modifier = Modifier
                .fillMaxSize()
        )
        StatisticsRun(
            fontSizeBody = 12.sp,
            fontSizeTitle = 14.sp,
            itemRunEntity = itemsRun,
            metricType = metricType,
            canExpanded = true,
            modifier = Modifier
                .width(250.dp)
                .padding(5.dp)
                .align(Alignment.TopStart)
        )

    }
}

@Preview(
    backgroundColor = 0xFFFFFF,
    showBackground = true
)
@Composable
fun DetailsRunLandscapePreview() {
    DetailsRunLandscape(
        itemsRun = RunData.runDataExample,
        metricType = MetricType.Meters
    )
}