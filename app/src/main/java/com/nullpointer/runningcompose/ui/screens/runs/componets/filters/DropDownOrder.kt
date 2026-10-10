package com.nullpointer.runningcompose.ui.screens.runs.componets.filters

import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.nullpointer.runningcompose.R

@Composable
fun DropDownOrder(isReverse: Boolean, changeSort: (Boolean) -> Unit) {
    OutlinedButton(onClick = { changeSort(!isReverse) }) {
        Icon(painterResource(if (isReverse) R.drawable.ic_arrow_upward else R.drawable.ic_arrow_downward),
            contentDescription = null)
        Text(stringResource(if (isReverse) R.string.text_asc_order else R.string.text_desc_order))
    }
}
