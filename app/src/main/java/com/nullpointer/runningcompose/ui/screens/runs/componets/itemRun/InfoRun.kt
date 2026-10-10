package com.nullpointer.runningcompose.ui.screens.runs.componets.itemRun

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.utils.toAVGSpeed
import com.nullpointer.runningcompose.core.utils.toCaloriesBurned
import com.nullpointer.runningcompose.core.utils.toDateFormat
import com.nullpointer.runningcompose.core.utils.toDateOnlyTime
import com.nullpointer.runningcompose.core.utils.toFullFormatTime
import com.nullpointer.runningcompose.core.utils.toMeters
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.ui.theme.RunningSpacing

@Composable
fun InfoRun(
    itemRun: RunData,
    metricType: MetricType,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
) {
    // Derive from current arguments: cached unkeyed values became stale after unit/data changes.
    Column(modifier, verticalArrangement = Arrangement.spacedBy(RunningSpacing.extraSmall)) {
        Text(itemRun.createAt.toDateFormat() + " · " + itemRun.createAt.toDateOnlyTime(context),
            style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(itemRun.distanceInMeters.toMeters(metricType), style = MaterialTheme.typography.headlineSmall)
        Text(stringResource(R.string.item_title_duration) + " " + itemRun.timeRunInMillis.toFullFormatTime(false),
            style = MaterialTheme.typography.bodyMedium)
        Text(stringResource(R.string.item_title_speed) + " " + itemRun.avgSpeedInMeters.toAVGSpeed(metricType),
            style = MaterialTheme.typography.bodyMedium)
        Text(stringResource(R.string.item_title_calories) + " " + itemRun.caloriesBurned.toCaloriesBurned(metricType),
            style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
