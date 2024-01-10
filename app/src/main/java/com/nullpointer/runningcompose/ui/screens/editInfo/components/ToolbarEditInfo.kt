package com.nullpointer.runningcompose.ui.screens.editInfo.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.ui.share.ToolbarBack
import com.nullpointer.runningcompose.ui.share.ToolbarSimple

@Composable
fun ToolbarEditInfo(
    isAuth: Boolean,
    actionBack: () -> Unit
) {
    when (isAuth) {
        true -> ToolbarBack(
            title = stringResource(R.string.title_edit_data),
            actionBack = actionBack,
        )

        else -> ToolbarSimple(
            title = stringResource(R.string.title_complete_data),
        )
    }
}


@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFF
)
@Composable
private fun ToolbarEditInfoPreviewComplete() {
    ToolbarEditInfo(
        actionBack = {},
        isAuth = false
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFF
)
@Composable
private fun ToolbarEditInfoPreviewEdit() {
    ToolbarEditInfo(
        actionBack = {},
        isAuth = true
    )
}