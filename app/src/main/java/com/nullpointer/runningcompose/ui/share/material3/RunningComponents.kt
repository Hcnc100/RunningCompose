package com.nullpointer.runningcompose.ui.share.material3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.ui.theme.RunningSpacing

/** Stateless building blocks for the phased migration. Business state stays with callers. */
@Composable
fun RunningCard(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            contentColor = MaterialTheme.colorScheme.onSurface,
        ),
    ) {
        Column(Modifier.padding(RunningSpacing.medium), content = content)
    }
}

@Composable
fun RunningSelectableCard(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.semantics { this.selected = selected },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = if (selected) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.surfaceContainerLow,
            contentColor = if (selected) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.onSurface,
        ),
    ) {
        Column(Modifier.padding(RunningSpacing.medium), content = content)
    }
}

@Composable
fun RunningPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
) {
    val loadingDescription = stringResource(R.string.m3_loading)
    // Busy is not the same as unavailable: retain readable action colors while
    // disabling input to prevent repeat submissions.
    val colors = if (loading && enabled) ButtonDefaults.buttonColors(
        disabledContainerColor = MaterialTheme.colorScheme.primary,
        disabledContentColor = MaterialTheme.colorScheme.onPrimary,
    ) else ButtonDefaults.buttonColors()
    Button(
        onClick = onClick,
        enabled = enabled && !loading,
        modifier = modifier.heightIn(min = 48.dp).semantics {
            if (loading) stateDescription = loadingDescription
        },
        shape = MaterialTheme.shapes.medium,
        colors = colors,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(RunningSpacing.small),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp), strokeWidth = 2.dp,
                    color = androidx.compose.material3.LocalContentColor.current,
                )
            }
            Text(text)
        }
    }
}

@Composable
fun RunningTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    error: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    OutlinedTextField(
        value = value, onValueChange = onValueChange,
        label = { Text(label) }, modifier = modifier,
        enabled = enabled, readOnly = readOnly, isError = error != null,
        supportingText = error?.let { message -> { Text(message) } },
        keyboardOptions = keyboardOptions,
        singleLine = true, shape = MaterialTheme.shapes.medium,
    )
}

@Composable
fun RunningErrorMessage(message: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.semantics { liveRegion = LiveRegionMode.Polite },
        color = MaterialTheme.colorScheme.errorContainer,
        contentColor = MaterialTheme.colorScheme.onErrorContainer,
        shape = MaterialTheme.shapes.medium,
    ) {
        Text(message, Modifier.padding(RunningSpacing.medium))
    }
}
