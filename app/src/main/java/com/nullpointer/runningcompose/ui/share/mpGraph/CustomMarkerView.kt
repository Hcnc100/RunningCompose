package com.nullpointer.runningcompose.ui.share.mpGraph

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.utils.*
import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.models.types.MetricType


@SuppressLint("ViewConstructor")
class CustomMarkerView(
    private val listRuns: List<Run>,
    private val metricType: MetricType,
    context: Context,
    layoutId: Int,
) :
    MarkerView(context, layoutId) {

    private val textDate: TextView = findViewById(R.id.textDateMarker)
    private val textAvgSpeed: TextView = findViewById(R.id.textSpeedMarker)
    private val textDistance: TextView = findViewById(R.id.textDistanceMarker)
    private val textDuration: TextView = findViewById(R.id.textDurationMarker)
    private val textCalories: TextView = findViewById(R.id.textCaloriesMarker)

    override fun getOffset(): MPPointF {
        return MPPointF(-width / 2f, -height.toFloat())
    }

    @SuppressLint("SetTextI18n")
    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        super.refreshContent(e, highlight)
        if (e == null) {
            return
        }
        val currentIndex = e.x.toInt()
        with(listRuns[currentIndex]) {
            textDate.text = timestamp.toDateFormat()
            textAvgSpeed.text = avgSpeedInMeters.toAVGSpeed(metricType)
            textCalories.text = caloriesBurned.toCaloriesBurned(metricType)
            textDistance.text = distanceInMeters.toMeters(metricType)
            textDuration.text = timeRunInMillis.toFullFormatTime(true)
        }

    }
}