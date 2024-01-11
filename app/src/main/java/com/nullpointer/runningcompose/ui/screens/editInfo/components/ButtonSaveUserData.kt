package com.nullpointer.runningcompose.ui.screens.editInfo.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.ui.preview.config.SimplePreview

@Composable
fun ButtonSaveUserInfo(
    isEnable: Boolean,
    actionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        enabled = isEnable,
        modifier = modifier,
        onClick = actionClick,
        content = {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_save),
                    contentDescription = stringResource(R.string.description_icon_save_info)
                )
                Text(text = stringResource(R.string.save_data_user))
            }
        },
    )
}

@SimplePreview
@Composable
private fun ButtonSaveUserInfoEnablePreview() {
    ButtonSaveUserInfo(
        isEnable = true,
        actionClick = {}
    )
}

@SimplePreview
@Composable
private fun ButtonSaveUserInfoDisablePreview() {
    ButtonSaveUserInfo(
        isEnable = false,
        actionClick = {}
    )
}
