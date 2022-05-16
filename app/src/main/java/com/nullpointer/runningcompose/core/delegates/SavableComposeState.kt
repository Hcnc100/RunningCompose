package com.nullpointer.runningcompose.core.delegates

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import kotlin.reflect.KProperty

// * delegate to save state in view model with SavedStateHandle
class SavableComposeState<T>(
    private val savedStateHandle: SavedStateHandle,
    private val key: String,
    defaultValue: T,
) {
    private var _state by mutableStateOf(
        savedStateHandle.get<T>(key) ?: defaultValue)

    operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>,
    ): T {
        return _state
    }

    operator fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: T,
    ) {
        _state = value
        savedStateHandle[key] = value
    }
}
