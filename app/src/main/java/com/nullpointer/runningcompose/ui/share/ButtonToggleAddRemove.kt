package com.nullpointer.runningcompose.ui.share

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.models.types.TrackingState


@Composable
fun ButtonToggleAddRemove(
    isSelectedEnable: Boolean,
    descriptionButtonAdd: String,
    actionAdd: () -> Unit,
    descriptionButtonRemove: String,
    actionRemove: () -> Unit,
    trackingState: TrackingState,
) {


    val scale by animateFloatAsState(
        if (isSelectedEnable) 0f else 1f,
        label = "FAB_MEASURE_ANIMATION"
    )

    AnimatedVisibility(
        visible = !isSelectedEnable,
        enter = fadeIn(animationSpec = tween(durationMillis = 500)) + scaleIn(
            animationSpec = tween(
                durationMillis = 500
            )
        ),
        exit = fadeOut(animationSpec = tween(durationMillis = 500)) + scaleOut(
            animationSpec = tween(
                durationMillis = 500
            )
        )
    ) {
        FabAnimation(
            modifier = Modifier.scale(scale),
            icon = if (trackingState == TrackingState.WAITING) Icons.Default.Add else Icons.Default.PlayArrow,
            description = descriptionButtonAdd,
            action = actionAdd,
        )
    }

    val scaleDelete by animateFloatAsState(if (isSelectedEnable) 1f else 0f, label = "")

    AnimatedVisibility(
        visible = isSelectedEnable,
        enter = fadeIn(animationSpec = tween(durationMillis = 500)) + scaleIn(
            animationSpec = tween(
                durationMillis = 500
            )
        ),
        exit = fadeOut(animationSpec = tween(durationMillis = 500)) + scaleOut(
            animationSpec = tween(
                durationMillis = 500
            )
        )
    ) {
        FabAnimation(
            modifier = Modifier.scale(scaleDelete),
            icon = Icons.Default.Delete,
            description = descriptionButtonRemove,
            action = actionRemove,
        )
    }





}

@Composable
fun FabAnimation(
    icon: ImageVector,
    description: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.secondary,
    isProgress: Boolean = false,
    action: () -> Unit,

) {
        FloatingActionButton(onClick = { action() },
            backgroundColor = backgroundColor,
            modifier = modifier) {
            Box(contentAlignment = Alignment.Center) {
                if (isProgress) CircularProgressIndicator(modifier = Modifier.size(50.dp))
                Icon(imageVector = icon,
                    contentDescription = description
                )
            }
        }

}
