package com.nullpointer.runningcompose.ui.theme

import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.click
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.nullpointer.runningcompose.R
import com.nullpointer.runningcompose.ui.share.material3.RunningPrimaryButton
import com.nullpointer.runningcompose.ui.share.material3.RunningSelectableCard
import com.nullpointer.runningcompose.ui.share.material3.RunningTextField
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RunningComponentsTest {
    @get:Rule val compose = createComposeRule()

    @Test fun primaryActionInvokesCallbackAndHasMinimumHeight() {
        var clicks = 0
        compose.setContent {
            RunningComposeTheme {
                RunningPrimaryButton("Iniciar", { clicks++ }, Modifier.testTag("action"))
            }
        }
        compose.onNodeWithTag("action").assertHeightIsAtLeast(48.dp).performClick()
        compose.runOnIdle { assertEquals(1, clicks) }
    }

    @Test fun disabledActionDoesNotInvokeCallback() {
        var clicks = 0
        compose.setContent {
            RunningComposeTheme {
                RunningPrimaryButton("Iniciar", { clicks++ }, Modifier.testTag("action"), enabled = false)
            }
        }
        compose.onNodeWithTag("action").assertIsNotEnabled().performTouchInput { click() }
        compose.runOnIdle { assertEquals(0, clicks) }
    }

    @Test fun loadingActionAnnouncesStateAndPreventsRepeatedSubmission() {
        var clicks = 0
        val description = InstrumentationRegistry.getInstrumentation().targetContext.getString(R.string.m3_loading)
        compose.setContent {
            RunningComposeTheme {
                RunningPrimaryButton("Guardar", { clicks++ }, Modifier.testTag("action"), loading = true)
            }
        }
        compose.onNodeWithTag("action")
            .assertIsNotEnabled()
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.StateDescription, description))
            .performTouchInput { click() }
        compose.runOnIdle { assertEquals(0, clicks) }
    }

    @Test fun fieldHoistsEditedValueAndDisplaysValidationMessage() {
        var value by mutableStateOf("Corredor")
        compose.setContent {
            RunningComposeTheme {
                RunningTextField(value, { value = it }, "Nombre", Modifier.testTag("field"), error = "Revisa el nombre")
            }
        }
        compose.onNodeWithTag("field").performTextReplacement("RunnerQA")
        compose.runOnIdle { assertEquals("RunnerQA", value) }
        compose.onNodeWithText("Revisa el nombre").assertIsDisplayed()
    }

    @Test fun selectableCardExposesAndUpdatesSelection() {
        var selected by mutableStateOf(false)
        compose.setContent {
            RunningComposeTheme {
                RunningSelectableCard(selected, { selected = !selected }, Modifier.testTag("card")) {
                    Text("Carrera")
                }
            }
        }
        compose.onNodeWithTag("card").assertHeightIsAtLeast(48.dp)
            .assertIsNotSelected().performClick().assertIsSelected()
        compose.onNodeWithTag("card").performClick().assertIsNotSelected()
    }
}
