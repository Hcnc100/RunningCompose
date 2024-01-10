package com.nullpointer.runningcompose.ui.screens.runs.componets.filters

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.R

@Composable
fun DropDownOrder(
    isReverse: Boolean,
    changeSort: (Boolean) -> Unit,
) {
    val textOrderRes = when (isReverse) {
        true -> R.string.text_asc_order
        false -> R.string.text_desc_order
    }
    val iconOrderRes = when (isReverse) {
        true -> R.drawable.ic_arrow_upward
        false -> R.drawable.ic_arrow_downward
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { changeSort(!isReverse) },
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(stringResource(id = textOrderRes))
        Icon(
            imageVector = ImageVector.vectorResource(id = iconOrderRes),
            contentDescription = stringResource(R.string.description_order_sort_asc_or_desc),
            modifier = Modifier.size(15.dp)
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFF,
)
@Composable
private fun DropDownOrderPreview() {
    DropDownOrder(
        isReverse = false,
        changeSort = {}
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFF,
)
@Composable
private fun DropDownOrderReversePreview() {
    DropDownOrder(
        isReverse = true,
        changeSort = {}
    )
}