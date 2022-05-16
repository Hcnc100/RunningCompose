package com.nullpointer.runningcompose.ui.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.Run
import com.nullpointer.runningcompose.ui.screens.runs.componets.InfoRun
import com.nullpointer.runningcompose.ui.share.ToolbarBack
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun DetailsRun(
    navigator: DestinationsNavigator,
    itemsRun: Run,
) {
    Scaffold(
        topBar = {
            ToolbarBack(title = stringResource(R.string.title_details),
                actionBack = navigator::popBackStack)
        }
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Card(modifier = Modifier.padding(10.dp)) {
                InfoRun(
                    itemRun = itemsRun,
                    dataComplete = true,
                    modifier = Modifier.padding(10.dp),
                    isMiniTitle = false)
            }
        }
    }
}