package com.nullpointer.runningcompose.ui.screens.config.components.share

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.R

@Composable
 fun <T> SelectOptionConfig(
    titleField: String,
    onChange: (T) -> Unit,
    textCurrentSelected: String,
    modifier: Modifier= Modifier,
    listItemsAndNames:Map<T,Int?>,
) {
    val (isDropDown, changeIsDrop) = rememberSaveable { mutableStateOf(false) }
    Row(
        modifier = modifier.clickable { changeIsDrop(true) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
        Text(
            text = titleField,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(.5f)
        )
        Box(modifier = Modifier.weight(.5f)) {
            OutlinedTextField(
                enabled = false,
                value = textCurrentSelected,
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                trailingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_drop),
                        contentDescription = stringResource(R.string.describe_icon_drop_menu),
                    )
                })
            DropdownMenu(expanded = isDropDown, onDismissRequest = { changeIsDrop(false) }) {
                listItemsAndNames.entries.forEach { item->
                    val (value,name)= item
                    DropdownMenuItem(
                        onClick = {
                            onChange(value)
                            changeIsDrop(false)
                        }
                    ) {
                        Text(
                            text = name?.let { stringResource(id = it) } ?: value.toString()
                        )
                    }
                }
            }
        }
    }
}