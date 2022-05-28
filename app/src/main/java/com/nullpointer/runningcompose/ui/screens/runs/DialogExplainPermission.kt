package com.nullpointer.runningcompose.ui.screens.runs

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.Html
import android.widget.TextView
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.ui.screens.empty.LottieContainer


@Composable
fun DialogExplainPermission(
    actionHidden: () -> Unit,
    actionAccept: () -> Unit,
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    AlertDialog(onDismissRequest = actionHidden,
        confirmButton = {
            Button(onClick = {
                actionAccept()
                actionHidden()
            }) {
                Text(stringResource(id = R.string.action_accept))
            }
        },
        dismissButton = {
            OutlinedButton(onClick = actionHidden) {
                Text(stringResource(id = R.string.action_cancel))
            }
        },
        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(stringResource(R.string.need_permissions_tracking),
                    style = MaterialTheme.typography.h6)

                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    IconButton(onClick = {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        intent.data = Uri.parse("package:" + context.packageName)
                        context.startActivity(intent)

                    }) {
                        Icon(painter = painterResource(id = R.drawable.ic_settings),
                            contentDescription = stringResource(R.string.description_icon_open_settings))
                    }
                    Box {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(painter = painterResource(id = R.drawable.ic_help),
                                contentDescription = stringResource(R.string.description_permissions_help))
                        }
                        ToolTipInfo(
                            isExpanded = expanded,
                            hiddenTooltip = { expanded = false }
                        )
                    }
                }
                LottieContainer(modifier = Modifier.size(250.dp), animation = R.raw.location)
            }
        }
    )
}

@Composable
fun ToolTipInfo(
    isExpanded: Boolean,
    hiddenTooltip: () -> Unit,
) {
    val context = LocalContext.current
    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = hiddenTooltip
    ) {
        DropdownMenuItem(onClick = hiddenTooltip) {
            val colorText = if (isSystemInDarkTheme()) Color.WHITE else Color.BLACK
            val textTooltip = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(context.getString(R.string.message_permission_tooltip),
                    Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(context.getString(R.string.message_permission_tooltip))
            }
            AndroidView(factory = { context ->
                TextView(context).apply {
                    text = textTooltip
                    setTextColor(colorText)
                }
            })
        }
    }
}