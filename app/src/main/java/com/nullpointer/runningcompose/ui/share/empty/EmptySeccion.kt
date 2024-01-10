package com.nullpointer.runningcompose.ui.share.empty

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.nullpointer.runningcompose.R

@Composable
fun EmptySection(
    @RawRes
    animation: Int,
    textEmpty: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        LottieContainerForever(
            modifier = Modifier.weight(.8f),
            animation = animation
        )
        Box(
            modifier = Modifier
                .weight(.2f)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                maxLines = 3,
                text = textEmpty,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.W600,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.fillMaxWidth(.8f)
            )
        }
    }
}

@Preview(
    backgroundColor = 0xFFFFFF,
    showBackground = true
)
@Composable
fun EmptySectionPreview() {
    EmptySection(
        animation = R.raw.empty1,
        textEmpty = "Empty Section"
    )
}



