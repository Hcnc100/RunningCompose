package com.nullpointer.runningcompose.ui.screens.intro.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ContainerItemIntro(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {

    Column(
        modifier = modifier
            .background(MaterialTheme.colors.primary)
            .padding(20.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content
    )

}
