package com.nullpointer.runningcompose.ui.screens.runs.componets.itemRun

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.data.RunData
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.ui.actions.RunActions
import com.nullpointer.runningcompose.ui.preview.config.ThemePreviews
import com.nullpointer.runningcompose.ui.theme.RunningSpacing

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemRun(
    isSelect: Boolean,
    runData: RunData,
    isSelectEnable: Boolean,
    metricType: MetricType,
    modifier: Modifier = Modifier,
    actionRun: (RunActions) -> Unit,
) {
    val longClickLabel = stringResource(R.string.m3_select_run)
    Surface(
        color = if (isSelect) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainerLow,
        contentColor = if (isSelect) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.fillMaxWidth().semantics { selected = isSelect }.combinedClickable(
            onClick = { actionRun(if (isSelectEnable) RunActions.SELECT else RunActions.DETAILS) },
            onLongClickLabel = longClickLabel,
            onLongClick = { if (!isSelectEnable) actionRun(RunActions.SELECT) },
        ),
    ) {
        BoxWithConstraints(Modifier.padding(RunningSpacing.medium)) {
            if (maxWidth < 300.dp || LocalDensity.current.fontScale > 1.3f) {
                Column(verticalArrangement = Arrangement.spacedBy(RunningSpacing.medium)) {
                    ImageRun(runData.pathImgRun, Modifier.fillMaxWidth().height(120.dp))
                    InfoRun(runData, metricType)
                }
            } else {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(RunningSpacing.medium),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ImageRun(runData.pathImgRun, Modifier.size(width = 96.dp, height = 120.dp))
                    InfoRun(runData, metricType, Modifier.weight(1f))
                }
            }
        }
    }
}

@ThemePreviews
@Composable
private fun ItemRunUnselectPreview() {
    ItemRun(false, RunData.runDataExample, false, MetricType.Meters, actionRun = {})
}

@ThemePreviews
@Composable
private fun ItemRunSelectPreview() {
    ItemRun(true, RunData.runDataExample, true, MetricType.Meters, actionRun = {})
}
