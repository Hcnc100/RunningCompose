package com.nullpointer.runningcompose.ui.screens.config.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.core.states.Resource
import com.nullpointer.runningcompose.models.data.AuthData
import com.nullpointer.runningcompose.ui.screens.config.components.share.TitleConfig

@Composable
fun InfoUserConfig(
    orientation: Int,
    authData: Resource<AuthData?>,
    actionGoEditInfo: () -> Unit,
) {
    val name = if (authData is Resource.Success) authData.data?.name.orEmpty() else ""

    val weight = if (authData is Resource.Success) authData.data?.weight.toString() else ""

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TitleConfig(text = stringResource(R.string.title_info_personal))
        Box(
            contentAlignment = Alignment.Center
        ) {
            when (orientation) {
                Configuration.ORIENTATION_PORTRAIT -> InfoUserConfigPortrait(
                    name = name,
                    weight = weight,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp)
                )

                Configuration.ORIENTATION_LANDSCAPE -> InfoUserConfigLandscape(
                    name = name,
                    weight = weight,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp)
                )

                else -> InfoUserConfigLandscape(
                    name = name,
                    weight = weight
                )
            }
            if (authData is Resource.Loading) {
                CircularProgressIndicator()
            }

        }
        ButtonEditInfo(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            actionGoEditInfo = actionGoEditInfo
        )
    }
}
@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFF
)
@Composable
private fun InfoUserConfigPortraitPreview() {
    InfoUserConfigPortrait(
        weight = "80",
        name = "Juanito"
    )
}

@Composable
private fun InfoUserConfigPortrait(
    name: String,
    weight: String,
    modifier: Modifier=Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
    ) {
        TextShowOnlyInfo(
            textLabel = stringResource(R.string.label_name_user),
            textShow = name
        )
        TextShowOnlyInfo(
            textLabel = stringResource(R.string.label_weight_user),
            textShow = weight
        )
    }
}


@Composable
fun InfoUserConfigLandscape(
    name: String,
    weight: String,
    modifier: Modifier=Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
    ) {
        TextShowOnlyInfo(
            textLabel = stringResource(R.string.label_name_user),
            textShow = name,
            modifier = Modifier.weight(.5f)
        )
        TextShowOnlyInfo(
            textLabel = stringResource(R.string.label_weight_user),
            textShow = weight,
            modifier = Modifier.weight(.5f)
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFF
)
@Composable
private fun InfoUserConfigLandscapePreview() {
    InfoUserConfigLandscape(
        weight = "80",
        name = "Juanito"
    )
}




@Composable
private fun TextShowOnlyInfo(
    textShow: String,
    textLabel: String,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = textShow,
        readOnly = true,
        enabled = false,
        maxLines = 1,
        label = { Text(text = textLabel) },
        onValueChange = {},
        modifier = modifier.fillMaxWidth()
    )
}
