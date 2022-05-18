package com.nullpointer.runningcompose.ui.screens.editInfo

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.ui.screens.editInfo.viewModels.EditInfoViewModel
import com.nullpointer.runningcompose.ui.share.ToolbarBack
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun EditInfoScreen(
    editInfoViewModel: EditInfoViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    Scaffold(
        topBar = {
            if (editInfoViewModel.isDataComplete)
                ToolbarBack(
                    title = "Edita tus datos",
                    actionBack = navigator::popBackStack)
            else ToolbarBack(title = "Completa tus datos")
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                editInfoViewModel.updateDataUser()
            }) {
                Icon(painterResource(id = R.drawable.ic_save), "")
            }
        }
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 20.dp)) {
            EditInfoName(editInfoViewModel = editInfoViewModel)
            EditInfoWeight(editInfoViewModel = editInfoViewModel)
        }
    }
}

@Composable
private fun EditInfoName(
    editInfoViewModel: EditInfoViewModel,
) {
    EditTextInfo(value = editInfoViewModel.nameUser,
        label = "Nombre",
        errorRes = editInfoViewModel.errorNamed,
        lengthValue = editInfoViewModel.nameLength,
        onChange = editInfoViewModel::changeNameUser)
}

@Composable
private fun EditInfoWeight(
    editInfoViewModel: EditInfoViewModel,
) {
    EditTextInfo(value = editInfoViewModel.weightUser.toString(),
        label = "Peso",
        errorRes = editInfoViewModel.errorWeight,
        lengthValue = "",
        onChange = editInfoViewModel::changeWeight)
}

@Composable
private fun EditTextInfo(
    value: String,
    label: String,
    @StringRes
    errorRes: Int,
    lengthValue: String,
    onChange: (String) -> Unit,
) {
    Column(modifier = Modifier.padding(vertical = 20.dp, horizontal = 10.dp)) {
        OutlinedTextField(
            value = value,
            onValueChange = onChange,
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth()
        )
        Text(text = if (errorRes == -1) lengthValue else stringResource(id = errorRes),
            style = MaterialTheme.typography.caption,
            color = if (errorRes == -1) Color.Unspecified else MaterialTheme.colors.error,
            modifier = Modifier.align(Alignment.End))
    }
}

