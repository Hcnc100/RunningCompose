package com.nullpointer.runningcompose.ui.screens.details.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.ui.preview.config.SimplePreview

@Composable
fun ButtonCenterLocation(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier
            .padding(10.dp)
            .size(45.dp),
        onClick = onClick
    ) {
        Icon(painter = painterResource(id = R.drawable.ic_location), contentDescription = null)
    }
}


@SimplePreview
@Composable
fun ButtonCenterLocationPreview() {
    ButtonCenterLocation {}
}