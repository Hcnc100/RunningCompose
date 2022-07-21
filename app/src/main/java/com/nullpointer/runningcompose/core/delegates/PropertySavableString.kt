package com.nullpointer.runningcompose.core.delegates

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle

open class PropertySavableString(
    state: SavedStateHandle,
    @StringRes
    val label: Int,
    @StringRes
    val hint: Int,
    private val maxLength: Int,
    @StringRes
    private val emptyError: Int = RESOURCE_DEFAULT,
    @StringRes
    private val lengthError: Int = RESOURCE_DEFAULT,
) {
    companion object {
        private const val INIT_ID = 1
        private const val FIN_ID = 1234567890
        const val RESOURCE_DEFAULT = -1
    }

    private val idSaved = "SAVED_PROPERTY_${(INIT_ID..FIN_ID).random()}"

    var value by SavableComposeState(state, "$idSaved-value", "")
        private set

    var errorValue by SavableComposeState(state, "$idSaved-error", RESOURCE_DEFAULT)

    private var textInit by SavableProperty(state, "$idSaved-init", "")


    val hasChanged: Boolean
        get() = this.value != textInit

    val countLength get() = "${value.length}/${maxLength}"

    val hasError get() = errorValue != RESOURCE_DEFAULT

    fun initValue(value: String) {
        this.value = value
        textInit = value
    }

    open fun changeValue(stringValue: String) {
        this.value = stringValue
        this.errorValue = when {
            stringValue.isEmpty() && emptyError != RESOURCE_DEFAULT -> emptyError
            stringValue.length > maxLength && lengthError != RESOURCE_DEFAULT -> lengthError
            else -> RESOURCE_DEFAULT
        }
    }

    fun clearValue() {
        value = ""
        errorValue = RESOURCE_DEFAULT
    }

}