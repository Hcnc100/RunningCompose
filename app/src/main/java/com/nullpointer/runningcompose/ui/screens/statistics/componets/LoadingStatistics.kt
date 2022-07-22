package com.nullpointer.runningcompose.ui.screens.statistics.componets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun LoadingStatistics(
    modifier :Modifier = Modifier
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        repeat(2) {
            Row(modifier = Modifier.fillMaxWidth()) {
                repeat(2) {
                    Box(
                        modifier = Modifier
                            .weight(.5f)
                            .height(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Card(
                            modifier = Modifier
                                .height(50.dp)
                                .width(150.dp)
                                .shimmer()
                        ) {}
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .shimmer()
            ) {}
        }
    }
}