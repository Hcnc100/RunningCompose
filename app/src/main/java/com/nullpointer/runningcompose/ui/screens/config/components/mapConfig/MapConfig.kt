package com.nullpointer.runningcompose.ui.screens.config.components.mapConfig

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.models.data.config.MapConfig
import com.nullpointer.runningcompose.models.types.MapStyle
import com.nullpointer.runningcompose.ui.screens.config.components.dialogColor.DialogColorPicker
import com.nullpointer.runningcompose.ui.screens.config.components.share.TitleConfig


@Composable
fun MapSettings(
    orientation: Int,
    mapConfig: MapConfig,
    changeWeight: (Int) -> Unit,
    changeColorMap: (Color) -> Unit,
    changeStyleMap: (MapStyle) -> Unit,
) {
    val (isDialogShow, changeVisibleDialog) = rememberSaveable {
        mutableStateOf(false)
    }

    Column {
        TitleConfig(text = stringResource(R.string.title_config_map))
        when (orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                MapSettingsConfigPortrait(
                    mapConfig = mapConfig,
                    changeWeight = changeWeight,
                    changeStyleMap = changeStyleMap,
                    showDialogColor = { changeVisibleDialog(true) },
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            }

            else -> {
                MapSettingsConfigLandscape(
                    mapConfig = mapConfig,
                    changeWeight = changeWeight,
                    changeStyleMap = changeStyleMap,
                    showDialogColor = { changeVisibleDialog(true) },
                    modifier = Modifier.padding(horizontal = 10.dp)

                )
            }
        }
    }
    if (isDialogShow) {
        DialogColorPicker(
            changeColor = changeColorMap,
            hiddenDialog = { changeVisibleDialog(false) },
        )
    }
}






