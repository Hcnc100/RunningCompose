package com.nullpointer.runningcompose.ui.screens.runs.componets.filters

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.Modifier
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.types.SortType

@Composable
fun DropDownSorterOptions(sortType: SortType, changeSort: (SortType) -> Unit) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Box {
        OutlinedButton(onClick = { expanded = !expanded }) {
            Text(stringResource(sortType.idName))
            Icon(painterResource(R.drawable.ic_arrow_drop), contentDescription = stringResource(R.string.description_drop_sort_menu))
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            SortType.entries.forEach { option ->
                DropdownMenuItem(
                    text = { Text(stringResource(option.idName), color = if (option == sortType)
                        MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface) },
                    modifier = Modifier.semantics { selected = option == sortType },
                    onClick = { expanded = false; changeSort(option) },
                )
            }
        }
    }
}
