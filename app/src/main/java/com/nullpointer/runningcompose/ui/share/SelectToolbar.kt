package com.nullpointer.runningcompose.ui.share


import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.utils.getPlural


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

    val title by derivedStateOf {
        if (numberSelection == 0)
            context.getString(titleDefault) else
                context.getPlural(titleSelection, numberSelection)
    }


    TopAppBar(
        backgroundColor = if (numberSelection == 0) MaterialTheme.colors.primarySurface else MaterialTheme.colors.primary,
        title = {
            Text(title)
        },
        contentColor = Color.White,
        actions = {
            if (numberSelection != 0) {
                IconButton(onClick = actionClear) {
                    Icon(painterResource(id = R.drawable.ic_clear),
                        contentDescription = stringResource(R.string.description_clear_selection))
                }
            }
        }
    )
}

@Composable
fun ToolbarBack(title: String, actionBack: (() -> Unit)? = null) {
    TopAppBar(title = { Text(title) },
        navigationIcon = {
            actionBack?.let { action ->
                IconButton(onClick = { action() }) {
                    Icon(painterResource(id = R.drawable.ic_arrow_back),
                        stringResource(id = R.string.description_arrow_back))
                }
            }
        })
}

@Composable
fun ToolbarBackWithAction(
    title: String,
    actionBack: () -> Unit,
    actionCancel: (() -> Unit)? = null,
) {
    TopAppBar(title = { Text(title) },
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