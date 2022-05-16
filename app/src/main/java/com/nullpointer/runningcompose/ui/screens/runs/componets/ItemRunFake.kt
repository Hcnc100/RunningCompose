package com.nullpointer.runningcompose.ui.screens.runs.componets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun ItemRunFake() {
    Card(
        modifier = Modifier.padding(3.dp),
        shape = RoundedCornerShape(10.dp),
    ) {
        Row(modifier = Modifier
            .padding(10.dp)
            .height(150.dp)) {
            Card(modifier = Modifier
                .weight(.5f)
                .fillMaxHeight()
                .shimmer(), backgroundColor = Color.Gray) {}
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier
                .padding(10.dp)
                .weight(.5f)
                .fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween) {
                repeat(4) {
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .shimmer(), backgroundColor = Color.Gray) {}
                }
            }
        }
    }
}