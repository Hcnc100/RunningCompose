package com.nullpointer.runningcompose.ui.share


import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.utils.getPlural


@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun SelectToolbar(
    @StringRes
    titleDefault: Int,
    @PluralsRes
    titleSelection: Int,
    numberSelection: Int,
    actionClear: () -> Unit,
    context: Context = LocalContext.current
) {

    val title =
        if (numberSelection == 0)
            context.getString(titleDefault) else context.getPlural(titleSelection, numberSelection)



    val backgroundToolbar by animateColorAsState(
        if (numberSelection == 0) androidx.compose.material3.MaterialTheme.colorScheme.surfaceContainer
        else androidx.compose.material3.MaterialTheme.colorScheme.primaryContainer,
        label = ""
    )
    val contextColor by animateColorAsState(
        if(numberSelection == 0) androidx.compose.material3.MaterialTheme.colorScheme.onSurface
        else androidx.compose.material3.MaterialTheme.colorScheme.onPrimaryContainer, label = ""
    )

    androidx.compose.material3.TopAppBar(
        colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
            containerColor = backgroundToolbar,
            titleContentColor = contextColor,
            actionIconContentColor = contextColor,
        ),
        title = { androidx.compose.material3.Text(title, maxLines = 1, overflow = TextOverflow.Ellipsis) },
        actions = {
            if (numberSelection != 0) {
                androidx.compose.material3.IconButton(onClick = actionClear) {
                    androidx.compose.material3.Icon(
                        painterResource(id = R.drawable.ic_clear),
                        contentDescription = stringResource(R.string.description_clear_selection)
                    )
                }
            }
        }
    )
}

@Composable
fun ToolbarBack(title: String, actionBack: () -> Unit) {
    TopAppBar(title = {  Text(title)},
        backgroundColor =  MaterialTheme.colors.primary,
        contentColor = Color.White,
        navigationIcon = {
            IconButton(onClick = actionBack) {
                Icon(painterResource(id = R.drawable.ic_arrow_back),
                    stringResource(id = R.string.description_arrow_back))
            }
        })
}

@Composable
fun ToolbarSimple(title: String) {
    TopAppBar(
        contentColor = Color.White,
        title = {  Text(title)},
        backgroundColor =  MaterialTheme.colors.primary)
}

@Composable
fun ToolbarBackWithAction(
    title: String,
    actionBack: () -> Unit,
    actionCancel: (() -> Unit)? = null,
) {
    TopAppBar(
        backgroundColor =  MaterialTheme.colors.primary,
        contentColor = Color.White,
        title = {  Text(title)},
        navigationIcon = {
            IconButton(onClick = actionBack) {
                Icon(painterResource(id = R.drawable.ic_arrow_back),
                    stringResource(id = R.string.description_arrow_back))
            }
        },
        actions = {
            actionCancel?.let {actionCancel->
                IconButton(onClick = actionCancel) {
                    Icon(painterResource(id = R.drawable.ic_clear),
                        contentDescription = stringResource(R.string.description_cancel_run))
                }
            }
        }
    )
}
