package com.nullpointer.runningcompose.core.delegates

import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle

class PropertySavableWeight(
    state: SavedStateHandle,
    @StringRes
    label: Int,
    @StringRes
    hint: Int,
    maxLength: Int,
    @StringRes
    emptyError: Int = RESOURCE_DEFAULT,
    @StringRes
    lengthError: Int = RESOURCE_DEFAULT,
    @StringRes
    private val invalidWeightError: Int = RESOURCE_DEFAULT,
    private val minWeight: Float,
    private val maxWeight: Float,
) : PropertySavableString(state, label, hint, maxLength, emptyError, lengthError) {

    override fun changeValue(stringValue: String) {
        super.changeValue(stringValue)
        val valueWeight = stringValue.toFloatOrNull() ?: 0F
        if(!hasError){
            this.errorValue = if (valueWeight !in minWeight..maxWeight) {
                invalidWeightError
            } else {
                RESOURCE_DEFAULT
            }
        }
    }
}