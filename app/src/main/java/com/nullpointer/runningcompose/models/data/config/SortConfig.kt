package com.nullpointer.runningcompose.models.data.config

import com.nullpointer.runningcompose.models.types.SortType
import kotlinx.serialization.Serializable

@Serializable
data class SortConfig(
    val sortType: SortType = SortType.DATE,
    val isReverse: Boolean = false,
)