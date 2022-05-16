package com.nullpointer.runningcompose.ui.share

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ButtonToggleAddRemove(
    isVisible: Boolean,
    isSelectedEnable: Boolean,
    descriptionButtonAdd: String,
    actionAdd: () -> Unit,
    descriptionButtonRemove: String,
    actionRemove: () -> Unit,
) {

    AnimatedVisibility(
        visible = isVisible,
        enter = scaleIn() + fadeIn(),
        exit = scaleOut() + fadeOut()
    ) {
        FabAnimation(
            isVisible = !isSelectedEnable,
            icon = Icons.Default.Add,
            description = descriptionButtonAdd,
            action = actionAdd,
        )

        FabAnimation(
            isVisible = isSelectedEnable,
            icon = Icons.Default.Delete,
            description = descriptionButtonRemove,
            action = actionRemove,
        )
    }

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FabAnimation(
    isVisible: Boolean,
    icon: ImageVector,
    description: String,
    modifier: Modifier= Modifier,
    backgroundColor: Color = MaterialTheme.colors.secondary,
    isProgress: Boolean = false,
    action: () -> Unit,
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = scaleIn() + fadeIn(),
        exit = scaleOut() + fadeOut()
    ) {
        FloatingActionButton(onClick = { action() }, backgroundColor = backgroundColor, modifier = modifier) {
            Box(contentAlignment = Alignment.Center) {
                if (isProgress) CircularProgressIndicator(modifier = Modifier.size(50.dp))
                Icon(imageVector = icon,
                    contentDescription = description
                )
            }

        }
    }
}
