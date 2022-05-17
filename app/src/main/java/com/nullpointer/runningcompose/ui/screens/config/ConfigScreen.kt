package com.nullpointer.runningcompose.ui.screens.config

import androidx.annotation.RawRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.config.MapConfig
import com.nullpointer.runningcompose.models.types.MapStyle
import com.nullpointer.runningcompose.presentation.ConfigViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun ConfigScreen(
    configViewModel: ConfigViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val listMaps = remember { MapStyle.values().toList() }
    val listWeight = remember { listOf(1, 2, 3, 4, 5, 6, 7) }
    val configMap = configViewModel.mapConfig.collectAsState()

    Scaffold {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            InfoUser()
            MapSettings()
            configMap.value?.let { mapConfig ->
                SelectOptionConfig(
                    textField = "Estilo del mapa",
                    selected = stringResource(id = mapConfig.style.string),
                    listItems = listMaps,
                    listNamed = listMaps.map { stringResource(id = it.string) },
                    onChange = { configViewModel.changeMapConfig(style = it) })
                Spacer(modifier = Modifier.height(10.dp))
                SelectOptionConfig(
                    textField = "Grosor de la linea",
                    selected = mapConfig.weight.toString(),
                    listItems = listWeight,
                    onChange = { configViewModel.changeMapConfig(weight = it) })
            }
        }
    }
}

@Composable
fun MapSettings() {
    Column {
        TitleConfig(text = "Configuracion del mapa")
        Box(modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2f)
            .padding(vertical = 10.dp, horizontal = 20.dp)
            .background(
                Color.Gray))
    }
}

@Composable
fun <T> SelectOptionConfig(
    textField: String,
    selected: String,
    listItems: List<T>,
    listNamed: List<String>?=null,
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

@Composable
fun TitleConfig(
    text: String,
) {
    Text(text = text,
        fontWeight = FontWeight.W500,
        style = MaterialTheme.typography.caption,
        modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp))
}

@Composable
fun InfoUser() {
    Column {
        TitleConfig(text = "Informacion Personal")
        OutlinedTextField(value = "Pepe",
            readOnly = true,
            enabled = false,
            maxLines = 1,
            label = { Text(text = "Tu nombre") },
            onValueChange = {},
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 15.dp)
                .fillMaxWidth())

        OutlinedTextField(
            value = "Pepe",
            enabled = false,
            readOnly = true,
            maxLines = 1,
            label = { Text(text = "Tu Peso") },
            onValueChange = {},
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 15.dp)
                .fillMaxWidth())
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { /*TODO*/ }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Row {
                Icon(painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = "Icono de editar la informacion del usuario")
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Editar informacion")
            }
        }
    }


}
