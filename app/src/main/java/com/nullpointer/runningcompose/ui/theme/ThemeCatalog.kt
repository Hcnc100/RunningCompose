package com.nullpointer.runningcompose.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nullpointer.runningcompose.ui.share.material3.RunningCard
import com.nullpointer.runningcompose.ui.share.material3.RunningSelectableCard
import com.nullpointer.runningcompose.ui.share.material3.RunningPrimaryButton
import com.nullpointer.runningcompose.ui.share.material3.RunningTextField
import com.nullpointer.runningcompose.ui.share.material3.RunningErrorMessage

/** Component baseline for visual review; not a destination or production screen. */
@Composable
internal fun RunningThemeCatalog() {
    var name by rememberSaveable { mutableStateOf("Corredor") }
    var selected by rememberSaveable { mutableStateOf(true) }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()).padding(RunningSpacing.medium),
            verticalArrangement = Arrangement.spacedBy(RunningSpacing.medium),
        ) {
            Text("RunningCompose", style = MaterialTheme.typography.headlineSmall)
            Text("Material 3 · Componentes", style = MaterialTheme.typography.bodyLarge)
            RunningCard(modifier = Modifier.fillMaxWidth()) {
                    Text("5.20 km", style = MaterialTheme.typography.headlineLarge)
                    Text("Distancia · Ejemplo visual", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            RunningSelectableCard(
                selected = selected, onClick = { selected = !selected },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(if (selected) "Carrera seleccionada" else "Seleccionar carrera")
            }
            RunningTextField(
                value = name, onValueChange = { name = it },
                label = "Nombre", modifier = Modifier.fillMaxWidth(),
            )
            RunningPrimaryButton("Iniciar carrera", onClick = {}, modifier = Modifier.fillMaxWidth())
            Text("Todavía no hay carreras", style = MaterialTheme.typography.titleMedium)
            Text("Tu historial aparecerá aquí.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            RunningErrorMessage("No se pudo cargar. Inténtalo de nuevo.")
            RunningPrimaryButton(
                "Guardando carrera", onClick = {}, loading = true,
                modifier = Modifier.fillMaxWidth(),
            )
            RunningPrimaryButton(
                "Acción no disponible", onClick = {}, enabled = false,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview(name = "Foundation light", showBackground = true)
@Composable
private fun RunningThemeLightPreview() {
    RunningComposeTheme(darkTheme = false) { RunningThemeCatalog() }
}

@Preview(name = "Foundation dark", showBackground = true)
@Composable
private fun RunningThemeDarkPreview() {
    RunningComposeTheme(darkTheme = true) { RunningThemeCatalog() }
}

@Preview(name = "Foundation large font", fontScale = 2f, showBackground = true)
@Composable
private fun RunningThemeLargeFontPreview() {
    RunningComposeTheme(darkTheme = false) { RunningThemeCatalog() }
}
