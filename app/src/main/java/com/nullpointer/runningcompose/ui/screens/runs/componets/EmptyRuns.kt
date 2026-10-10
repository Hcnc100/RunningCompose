package com.nullpointer.runningcompose.ui.screens.runs.componets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.ui.preview.config.ThemePreviews
import com.nullpointer.runningcompose.ui.theme.RunningSpacing
import com.nullpointer.runningcompose.ui.share.empty.LottieContainerForever

@Composable
fun EmptyRuns(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(RunningSpacing.large),
        verticalArrangement = Arrangement.spacedBy(RunningSpacing.medium, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LottieContainerForever(animation = R.raw.empty1, modifier = Modifier.size(160.dp))
        Text(stringResource(R.string.message_empty_runs), style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center)
        Text(stringResource(R.string.m3_empty_runs_body), style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
    }
}

@ThemePreviews
@Composable
private fun EmptyRunPreview() { EmptyRuns() }
