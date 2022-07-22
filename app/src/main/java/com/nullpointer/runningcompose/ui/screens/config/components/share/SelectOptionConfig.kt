package com.nullpointer.runningcompose.ui.screens.config.components.share

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.R

@Composable
 fun <T> SelectOptionConfig(
    textField: String,
    selected: String,
    listItems: List<T>,
    listNamed: List<String>? = null,
    onChange: (T) -> Unit,
) {
    val (isDropDown, changeIsDrop) = rememberSaveable { mutableStateOf(false) }
    Row(modifier = Modifier.padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = textField,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(.5f)
                .clickable { changeIsDrop(true) }
        )
        Spacer(modifier = Modifier.width(15.dp))
        Box(modifier = Modifier.weight(.5f)) {
            OutlinedTextField(
                enabled = false,
                value = selected,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.clickable { changeIsDrop(true) },
                singleLine = true,
                trailingIcon = {
                    Icon(painter = painterResource(id = R.drawable.ic_arrow_drop),
                        contentDescription = stringResource(R.string.describe_icon_drop_menu))
                })
            DropdownMenu(expanded = isDropDown, onDismissRequest = { changeIsDrop(false) }) {
                listItems.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        onClick = {
                            onChange(item)
                            changeIsDrop(false)
                        }
                    ) {
                        Text(listNamed?.get(index) ?: item.toString())
                    }
                }
            }
        }
    }
}