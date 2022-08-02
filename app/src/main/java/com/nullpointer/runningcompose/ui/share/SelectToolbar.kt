package com.nullpointer.runningcompose.ui.share


import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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

    val title by remember(numberSelection) {
        derivedStateOf {
            if (numberSelection == 0)
                context.getString(titleDefault) else context.getPlural(titleSelection, numberSelection)
        }
    }


    TopAppBar(
        backgroundColor = if (numberSelection == 0)  MaterialTheme.colors.primary else MaterialTheme.colors.secondary,
        title = { Text(title, color = Color.White) },
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
fun ToolbarBack(title: String, actionBack: () -> Unit) {
    TopAppBar(title = {  Text(title, color = Color.White)},
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
    TopAppBar(title = {  Text(title, color = Color.White)},
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
        title = {  Text(title, color = Color.White)},
        navigationIcon = {
            IconButton(onClick = actionBack) {
                Icon(painterResource(id = R.drawable.ic_arrow_back),
                    stringResource(id = R.string.description_arrow_back))
            }
        },
        contentColor = Color.White,
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