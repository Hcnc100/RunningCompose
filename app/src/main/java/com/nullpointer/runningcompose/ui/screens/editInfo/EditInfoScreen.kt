package com.nullpointer.runningcompose.ui.screens.editInfo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.delegates.PropertySavableString
import com.nullpointer.runningcompose.ui.screens.main.viewModel.AuthViewModel
import com.nullpointer.runningcompose.ui.actions.EditInfoAction
import com.nullpointer.runningcompose.ui.actions.EditInfoAction.*
import com.nullpointer.runningcompose.ui.interfaces.ActionRootDestinations
import com.nullpointer.runningcompose.ui.navigation.MainNavGraph
import com.nullpointer.runningcompose.ui.screens.editInfo.viewModels.EditInfoViewModel
import com.nullpointer.runningcompose.ui.share.BlockProgress
import com.nullpointer.runningcompose.ui.share.EditableTextSavable
import com.nullpointer.runningcompose.ui.share.ToolbarBack
import com.nullpointer.runningcompose.ui.share.ToolbarSimple
import com.nullpointer.runningcompose.ui.states.EditInfoScreenState
import com.nullpointer.runningcompose.ui.states.rememberEditInfoScreenState
import com.ramcosta.composedestinations.annotation.Destination

@MainNavGraph(start = true)
@Destination
@Composable
fun EditInfoScreen(
    isAuth: Boolean = false,
    authViewModel: AuthViewModel,
    actionRootDestinations: ActionRootDestinations,
    editInfoViewModel: EditInfoViewModel = hiltViewModel(),
    editInfoScreenState: EditInfoScreenState = rememberEditInfoScreenState(),
) {

    LaunchedEffect(key1 = Unit) {
        editInfoViewModel.messageEditInfo.collect(editInfoScreenState::showSnackMessage)
    }

    LaunchedEffect(key1 = Unit) {
        if (isAuth) editInfoViewModel.restoreSaveData()
    }

    EditInfoScreen(
        isAuth = isAuth,
        isSavingData = authViewModel.isUpdatedData,
        nameUserProperty = editInfoViewModel.nameUser,
        scaffoldState = editInfoScreenState.scaffoldState,
        weightUserProperty = editInfoViewModel.weightUser,
        actionEditInfo = { action ->
            when (action) {
                MOVE_NEXT_FOCUS -> editInfoScreenState.moveNextFocus()
                ACTION_BACK -> actionRootDestinations.backDestination()
                CHANGE_DATA -> {
                    editInfoScreenState.hiddenKeyBoard()
                    editInfoViewModel.validateDataUser()?.let { authData ->
                        authViewModel.saveAuthData(authData).invokeOnCompletion {
                            if(it==null && isAuth){
                                editInfoViewModel.addMessage(R.string.message_data_updated)
                            }
                        }
                    }
                }
            }
        }
    )

}


@Composable
fun EditInfoScreen(
    isAuth: Boolean,
    isSavingData: Boolean,
    scaffoldState: ScaffoldState,
    nameUserProperty: PropertySavableString,
    weightUserProperty: PropertySavableString,
    actionEditInfo: (EditInfoAction) -> Unit,
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ToolbarEditInfo(
                isAuth = isAuth,
                actionBack = { actionEditInfo(ACTION_BACK) })
        },
        floatingActionButton = {
            ButtonSaveInfo(
                isEnable = !isSavingData,
                actionClick = {
                    actionEditInfo(CHANGE_DATA)
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(10.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            EditableTextSavable(
                isEnabled = !isSavingData,
                valueProperty = nameUserProperty,
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { actionEditInfo(MOVE_NEXT_FOCUS) })
            )

            EditableTextSavable(
                isEnabled = !isSavingData,
                singleLine = true,
                valueProperty = weightUserProperty,
                keyboardOptions = KeyboardOptions.Default.copy(
                    autoCorrect = false, imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { actionEditInfo(CHANGE_DATA) })
            )
        }
        if (isSavingData) BlockProgress()
    }
}


@Composable
private fun ButtonSaveInfo(
    isEnable: Boolean,
    actionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        enabled = isEnable,
        modifier = modifier,
        onClick = actionClick,
        content = {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_save),
                    contentDescription = stringResource(R.string.description_icon_save_info)
                )
                Text(text = "Guardar datos")
            }
        },
    )
}

@Composable
private fun ToolbarEditInfo(isAuth: Boolean, actionBack: () -> Unit) {
    if (isAuth) ToolbarBack(
        title = stringResource(R.string.title_edit_data), actionBack = actionBack
    ) else ToolbarSimple(title = stringResource(R.string.title_complete_data))
}


