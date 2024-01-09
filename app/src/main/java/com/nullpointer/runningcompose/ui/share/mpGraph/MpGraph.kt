package com.nullpointer.runningcompose.ui.share.mpGraph

import android.content.Context
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.entities.RunEntity
import com.nullpointer.runningcompose.models.types.MetricType


@Composable
fun MpGraphAndroid(
    list: List<RunData>,
    modifier: Modifier = Modifier,
    metricType: MetricType,
    context: Context= LocalContext.current
) {
    val textColor = MaterialTheme.colors.onBackground.toArgb()

    val runsStatisticData= remember(list) {
        val listAllMeasure = list.mapIndexed { index, simpleMeasure ->
            BarEntry(index.toFloat(), simpleMeasure.avgSpeedInMeters)
        }
        // * create dataset
        val barData=BarDataSet(listAllMeasure,"RUNS").apply {
            valueTextColor = textColor
            colors = ColorTemplate.MATERIAL_COLORS.toList()
            setDrawValues(false)
        }
        BarData(barData)
    }

    AndroidView(
        // * launch every update
        update = {
            with(it){
                data = runsStatisticData
                marker = CustomMarkerView(list,metricType, context, R.layout.custom_layout_marker_run)
                invalidate()
                highlightValue(null)
            }

        },
        modifier = modifier,
        factory = { context ->
            BarChart(context).apply {
                setupGraph(this)
                data = runsStatisticData
            }
        },
    )
}





fun setupGraph(graph: BarChart) = with(graph) {
    // * config graph and hidden labels
    description.isEnabled = false
    legend.isEnabled = false

    // * config axis
    xAxis.setDrawGridLines(false)
    xAxis.setDrawLabels(false)
    axisRight.setDrawLabels(false)
    axisRight.setDrawGridLines(false)
    axisLeft.setDrawLabels(false)
    axisLeft.setDrawGridLines(false)

    // * config container
    setDrawBorders(true)
}

