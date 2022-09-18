package com.nullpointer.runningcompose.core.delegates

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle

class PropertySavableString(
    tagSavable: String,
    @StringRes val hint: Int,
    @StringRes val label: Int,
    private val maxLength: Int,
    savedState: SavedStateHandle,
    private val valueDefault: String = "",
    @StringRes private val emptyError: Int = RESOURCE_DEFAULT,
    @StringRes private val lengthError: Int = RESOURCE_DEFAULT,
) {
    companion object {
        private const val RESOURCE_DEFAULT = -1
    }

    private val idSaved = "SAVED_PROPERTY_$tagSavable"

    var currentValue by SavableComposeState(savedState, "$idSaved-CURRENT-VALUE", valueDefault)
        private set

    var errorValue by SavableComposeState(savedState, "$idSaved-ERROR-VALUE", RESOURCE_DEFAULT)
        private set

    val hasChanged: Boolean get() = this.currentValue != valueDefault

    val countLength get() = "${currentValue.length}/${maxLength}"

    val hasError get() = errorValue != RESOURCE_DEFAULT

    val isEmpty get() = currentValue.isEmpty()


    fun changeValue(newValue: String) {
        currentValue = newValue
        errorValue = when {
            newValue.isEmpty() && emptyError != RESOURCE_DEFAULT -> emptyError
            newValue.length > maxLength && lengthError != RESOURCE_DEFAULT -> lengthError
            else -> RESOURCE_DEFAULT
        }
    }

    fun setAnotherError(@StringRes newError: Int) {
        errorValue = newError
    }

    fun clearValue() {
        currentValue = ""
        errorValue = RESOURCE_DEFAULT
    }

    fun reValueField() = changeValue(currentValue)

}
