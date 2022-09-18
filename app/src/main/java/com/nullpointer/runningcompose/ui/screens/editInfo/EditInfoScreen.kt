package com.nullpointer.runningcompose.ui.screens.editInfo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.presentation.ConfigViewModel
import com.nullpointer.runningcompose.ui.interfaces.ActionRootDestinations
import com.nullpointer.runningcompose.ui.navigation.MainNavGraph
import com.nullpointer.runningcompose.ui.screens.editInfo.viewModels.EditInfoViewModel
import com.nullpointer.runningcompose.ui.share.EditableTextSavable
import com.nullpointer.runningcompose.ui.share.ToolbarBack
import com.nullpointer.runningcompose.ui.share.ToolbarSimple
import com.nullpointer.runningcompose.ui.states.EditInfoScreen
import com.nullpointer.runningcompose.ui.states.rememberEditInfoScreen
import com.ramcosta.composedestinations.annotation.Destination

@MainNavGraph(start = true)
@Destination
@Composable
fun EditInfoScreen(
    isAuth: Boolean = false,
    configViewModel: ConfigViewModel,
    actionRootDestinations: ActionRootDestinations,
    editInfoViewModel: EditInfoViewModel = hiltViewModel(),
    editInfoState: EditInfoScreen = rememberEditInfoScreen()
) {

    LaunchedEffect(key1 = Unit) {
        editInfoViewModel.messageEditInfo.collect(editInfoState::showSnackMessage)
    }

    Scaffold(
        scaffoldState = editInfoState.scaffoldState,
        topBar = {
            ToolbarEditInfo(
                isAuth = isAuth,
                actionBack = actionRootDestinations::backDestination
            )
        },
        floatingActionButton = {
            ButtonSaveInfo(actionClick = {
                editInfoState.hiddenKeyBoard()
                editInfoViewModel.validateDataUser()?.let {
                    configViewModel.changeUserConfig(it)
                    if (isAuth) actionRootDestinations.backDestination()
                }
            })
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .padding(paddingValue)
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 20.dp)
        ) {
            EditableTextSavable(
                valueProperty = editInfoViewModel.nameUser,
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    autoCorrect = false,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { editInfoState.moveNextFocus() }
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            EditableTextSavable(
                valueProperty = editInfoViewModel.weightUser,
                changeValue = editInfoViewModel::changeWeightUser,
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    autoCorrect = false,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        editInfoState.hiddenKeyBoard()
                        editInfoViewModel.validateDataUser()?.let {
                            configViewModel.changeUserConfig(it)
                            if (isAuth) actionRootDestinations.backDestination()
                        }
                    }
                )
            )
        }
    }
}


@Composable
private fun ButtonSaveInfo(
    actionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = actionClick
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_save),
            contentDescription = stringResource(R.string.description_icon_save_info)
        )
    }
}

@Composable
private fun ToolbarEditInfo(isAuth: Boolean, actionBack: () -> Unit) {
    if (isAuth) ToolbarBack(
        title = stringResource(R.string.title_edit_data),
        actionBack = actionBack
    ) else ToolbarSimple(title = stringResource(R.string.title_complete_data))
}


