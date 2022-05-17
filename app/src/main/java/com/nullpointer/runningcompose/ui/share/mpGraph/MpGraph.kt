package com.nullpointer.runningcompose.ui.share.mpGraph

import android.content.Context
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.Run


@Composable
fun MpGraphAndroid(
    list: List<Run>,
    modifier: Modifier = Modifier,
) {
    val textColor = MaterialTheme.colors.onBackground.toArgb()
    AndroidView(
        // * launch every update
        update = {
            updateValuesGraph(
                context = it.context,
                barChart = it,
                list = list,
                colorText = textColor
            )
        },
        modifier = modifier,
        // * launch when create
        // ! only draw limits once
        factory = { context ->
            BarChart(context).apply {
                setupGraph(this)
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

fun updateValuesGraph(
    context: Context,
    barChart: BarChart,
    list: List<Run>,
    colorText: Int,
) = with(barChart) {

    // * create all entry
    val listAllMeasure = list.mapIndexed { index, simpleMeasure ->
        BarEntry(index.toFloat(), simpleMeasure.avgSpeed)
    }
    // * create dataset
    val dataSet = BarDataSet(listAllMeasure, "Seguimientos").apply {
        valueTextColor = colorText
        colors = ColorTemplate.MATERIAL_COLORS.toList()
        setDrawValues(false)
    }
    // ! remove old marker
    invalidate()
    highlightValue(null)
    data = BarData(dataSet)
    marker = CustomMarkerView(list,true, context, R.layout.custom_layout_marker_run)

}
