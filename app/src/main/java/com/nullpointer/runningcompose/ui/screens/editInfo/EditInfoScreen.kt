package com.nullpointer.runningcompose.ui.screens.editInfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nullpointer.runningcompose.core.delegates.PropertySavableString
import com.nullpointer.runningcompose.ui.actions.EditInfoAction
import com.nullpointer.runningcompose.ui.actions.EditInfoAction.ACTION_BACK
import com.nullpointer.runningcompose.ui.actions.EditInfoAction.CHANGE_DATA
import com.nullpointer.runningcompose.ui.actions.EditInfoAction.MOVE_NEXT_FOCUS
import com.nullpointer.runningcompose.ui.interfaces.ActionRootDestinations
import com.nullpointer.runningcompose.ui.navigation.MainNavGraph
import com.nullpointer.runningcompose.ui.screens.editInfo.components.ButtonSaveUserInfo
import com.nullpointer.runningcompose.ui.screens.editInfo.components.ToolbarEditInfo
import com.nullpointer.runningcompose.ui.screens.editInfo.viewModels.EditInfoViewModel
import com.nullpointer.runningcompose.ui.share.BlockProgress
import com.nullpointer.runningcompose.ui.share.EditableTextSavable
import com.nullpointer.runningcompose.ui.states.EditInfoScreenState
import com.nullpointer.runningcompose.ui.states.rememberEditInfoScreenState
import com.ramcosta.composedestinations.annotation.Destination

@MainNavGraph(start = true)
@Destination
@Composable
fun EditInfoScreen(
    isAuth: Boolean = false,
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
        isSavingData = editInfoViewModel.isSavingData,
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
                        editInfoViewModel.saveAuthData(authData)
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
            ButtonSaveUserInfo(
                isEnable = !isSavingData,
                actionClick = { actionEditInfo(CHANGE_DATA) }
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

