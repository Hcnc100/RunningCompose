package com.nullpointer.runningcompose.ui.screens.config.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.ui.screens.config.components.share.TitleConfig

@Composable
fun InfoUserConfig(
    orientation: Int,
    actionGoEditInfo: () -> Unit,
) {

    Column {
        TitleConfig(text = stringResource(R.string.title_info_personal))
        when (orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                Column {
                    TextShowOnlyInfo(
                        textLabel = stringResource(R.string.label_name_user),
                        textShow = "Pepe")
                    Spacer(modifier = Modifier.height(10.dp))
                    TextShowOnlyInfo(
                        textLabel = stringResource(R.string.label_weight_user),
                        textShow = "80 Kg")
                }
            }
            else -> {
                Row {
                    TextShowOnlyInfo(
                        textLabel = stringResource(R.string.label_name_user),
                        textShow = "Pepe",
                        modifier = Modifier.weight(.5f))
                    Spacer(modifier = Modifier.height(10.dp))
                    TextShowOnlyInfo(
                        textLabel = stringResource(R.string.label_weight_user),
                        textShow = "80 Kg",
                        modifier = Modifier.weight(.5f))
                }
            }
        }
        Button(onClick = actionGoEditInfo,
            modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Row {
                Icon(painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = stringResource(R.string.description_icon_edit_info))
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = stringResource(R.string.text_edit_info_user))
            }
        }
    }
}

@Composable
private fun TextShowOnlyInfo(
    textShow: String,
    textLabel: String,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(value = textShow,
        readOnly = true,
        enabled = false,
        maxLines = 1,
        label = { Text(text = textLabel) },
        onValueChange = {},
        modifier = modifier
            .padding(vertical = 10.dp, horizontal = 15.dp)
            .fillMaxWidth())
}
