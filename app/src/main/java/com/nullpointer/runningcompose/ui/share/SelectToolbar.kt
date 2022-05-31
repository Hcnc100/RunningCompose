package com.nullpointer.runningcompose.ui.share


import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.nullpointer.runningcompose.R


@Composable
fun SelectToolbar(
    titleDefault: String,
    titleSelection: String,
    numberSelection: Int,
    actionClear: () -> Unit,
) {
    TopAppBar(
        backgroundColor = if (numberSelection == 0) MaterialTheme.colors.primarySurface else MaterialTheme.colors.primary,
        title = {
            Text(if (numberSelection == 0) titleDefault else titleSelection)
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