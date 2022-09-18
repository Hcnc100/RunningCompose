package com.nullpointer.runningcompose.ui.screens.editInfo

import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.nullpointer.runningcompose.ui.states.SimpleScreenState
import com.nullpointer.runningcompose.ui.states.rememberSimpleScreenState
import com.ramcosta.composedestinations.annotation.Destination

@MainNavGraph(start = true)
@Destination
@Composable
fun EditInfoScreen(
    isAuth: Boolean = false,
    configViewModel: ConfigViewModel,
    editInfoViewModel: EditInfoViewModel = hiltViewModel(),
    editInfoState: SimpleScreenState = rememberSimpleScreenState(),
    actionRootDestinations: ActionRootDestinations
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
            FloatingActionButton(
                onClick = {
                    editInfoState.hiddenKeyBoard()
                    editInfoViewModel.validateDataUser()?.let {
                        configViewModel.changeUserConfig(it)
                        if (isAuth) actionRootDestinations.backDestination()
                    }
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_save),
                    contentDescription = stringResource(R.string.description_icon_save_info))
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 20.dp)
        ) {
            EditableTextSavable(valueProperty = editInfoViewModel.nameUser)
            Spacer(modifier = Modifier.height(10.dp))
            EditableTextSavable(valueProperty = editInfoViewModel.weightUser)
        }
    }
}

@Composable
private fun ToolbarEditInfo(isAuth: Boolean, actionBack: () -> Unit) {
    if (isAuth) ToolbarBack(
        title = stringResource(R.string.title_edit_data),
        actionBack = actionBack
    ) else ToolbarSimple(title = stringResource(R.string.title_complete_data))
}


