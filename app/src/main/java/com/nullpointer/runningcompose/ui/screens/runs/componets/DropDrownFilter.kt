package com.nullpointer.runningcompose.ui.screens.runs.componets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.data.config.SortConfig
import com.nullpointer.runningcompose.models.types.SortType


@Composable
fun DropFilterAndOrder(
    sortConfig: SortConfig,
    changeSort: (SortType?, Boolean?) -> Unit,
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        DropDownSorterOptions(sortConfig = sortConfig, changeSort = { changeSort(it, null) })
        DropDownOrder(isReverse = sortConfig.isReverse, changeSort = { changeSort(null, it) })
    }
}


@Preview(showBackground = true)
@Composable
private fun DropFilterAndOrderPreview() {
    DropFilterAndOrder(
        sortConfig = SortConfig(),
        changeSort = {_,_->}
    )
}



@Composable
private fun DropDownOrder(
    isReverse: Boolean,
    changeSort: (Boolean)->Unit,
) {
    val textOrderRes = when (isReverse) {
        true -> R.string.text_asc_order
        false -> R.string.text_desc_order
    }
    val iconOrderRes = when (isReverse) {
        true -> R.drawable.ic_arrow_upward
        false -> R.drawable.ic_arrow_downward
    }

    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { changeSort(!isReverse) },
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(stringResource(id = textOrderRes))
        Icon(
            imageVector = ImageVector.vectorResource(id =iconOrderRes),
            contentDescription = stringResource(R.string.description_order_sort_asc_or_desc),
            modifier = Modifier.size(15.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DropDownOrderPreview() {
    DropDownOrder(isReverse = false, changeSort ={} )
}




@Composable
private fun DropDownSorterOptions(
    sortConfig: SortConfig,
    changeSort: (SortType) -> Unit,
) {

    var isDropSelect by rememberSaveable {
        mutableStateOf(false)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            stringResource(R.string.text_order_by),
            style = MaterialTheme.typography.caption
        )
        Box(
            modifier = Modifier.clickable {
                isDropSelect= !isDropSelect
            }
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(id = sortConfig.sortType.idName),
                    style = MaterialTheme.typography.caption,
                    fontWeight = FontWeight.W500,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_drop),
                    contentDescription = stringResource(R.string.description_drop_sort_menu)
                )
            }
            DropdownMenu(
                expanded = isDropSelect,
                onDismissRequest = { isDropSelect = false },
            ) {
                SortType.values().forEach { sortType ->
                    DropdownMenuItem(
                        onClick = {
                            isDropSelect = false
                            changeSort(sortType)
                        },
                    ) {
                        Text(text = stringResource(id = sortType.idName))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MenuDropDownPreview() {
    DropDownSorterOptions(sortConfig = SortConfig(), changeSort = {})
}
