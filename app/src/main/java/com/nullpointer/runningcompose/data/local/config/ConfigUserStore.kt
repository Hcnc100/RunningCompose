package com.nullpointer.runningcompose.data.local.config

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.nullpointer.runningcompose.models.config.MapConfig
import com.nullpointer.runningcompose.models.config.SortConfig
import com.nullpointer.runningcompose.models.config.UserConfig
import com.nullpointer.runningcompose.models.types.MapStyle
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.models.types.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ConfigUserStore(
    private val context: Context,
) {
    companion object {
        private const val NAME_PREF_USER = "NAME_PREF_USER"

        private const val KEY_SORT_RUN = "KEY_SORT_RUN"
        private const val KEY_SORT_REVERSE = "KEY_SORT_REVERSE"

        private const val KEY_NAME = "KEY_NAME"
        private const val KEY_WEIGHT = "KEY_WEIGHT"

        private const val KEY_MAP_STYLE = "KEY_MAP_STYLE"
        private const val KEY_WEIGHT_MAP = "KEY_MAP_WEIGHT"
        private const val KEY_METRICS_TYPE = "KEY_METRICS_TYPE"
        private const val KEY_COLOR_MAP = "KEY_COLOR_MAP"

        private const val KEY_FIRST_LOCATION_PERMISSION = "KEY_FIRST_LOCATION_PERMISSION"
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = NAME_PREF_USER)

    private val keySortRun = stringPreferencesKey(KEY_SORT_RUN)
    private val keySortReverse = booleanPreferencesKey(KEY_SORT_REVERSE)

    private val keyName = stringPreferencesKey(KEY_NAME)
    private val keyWeight = floatPreferencesKey(KEY_WEIGHT)

    private val keyMapStyle = stringPreferencesKey(KEY_MAP_STYLE)
    private val keyMapWeight = intPreferencesKey(KEY_WEIGHT_MAP)
    private val keyMetricType = stringPreferencesKey(KEY_METRICS_TYPE)
    private val keyMapColor = intPreferencesKey(KEY_COLOR_MAP)

    private val keyFirstLocationPermission = booleanPreferencesKey(KEY_FIRST_LOCATION_PERMISSION)

    suspend fun changeSortConf(
        sortType: SortType? = null,
        isReverse: Boolean? = null,
    ) = context.dataStore.edit { pref ->
        sortType?.name?.let { pref[keySortRun] = it }
        isReverse?.let { pref[keySortReverse] = it }
    }

    fun getSortConfig() = context.dataStore.data.map { pref ->
        SortConfig(
            pref[keySortRun]?.let { SortType.valueOf(it) } ?: SortType.DATE,
            pref[keySortReverse] ?: false
        )
    }

    suspend fun changeUserConf(
        nameUser: String? = null,
        weight: Float? = null,
    ) = context.dataStore.edit { pref ->
        nameUser?.let { pref[keyName] = it }
        weight?.let { pref[keyWeight] = it }
    }

    fun getUserConfig() = context.dataStore.data.map { pref ->
        val name = pref[keyName]
        val weight = pref[keyWeight]
        if (name != null && weight != null) UserConfig(name, weight) else null
    }

    suspend fun changeMapConfig(
        style: MapStyle? = null,
        weight: Int? = null,
        color: Color? = null,
    ) = context.dataStore.edit { pref ->
        style?.let { pref[keyMapStyle] = it.name }
        weight?.let { pref[keyMapWeight] = it }
        color?.let { pref[keyMapColor] = it.toArgb() }
    }

    suspend fun changeMetrics(
        metrics: MetricType,
    ) = context.dataStore.edit { pref ->
        pref[keyMetricType] = metrics.name
    }

    fun getMetrics() = context.dataStore.data.map { pref ->
        pref[keyMetricType]?.let { MetricType.valueOf(it) } ?: MetricType.Meters
    }

    fun getMapConfig() = context.dataStore.data.map { pref ->
        MapConfig(
            style = pref[keyMapStyle]?.let { MapStyle.valueOf(it) } ?: MapStyle.LITE,
            weight = pref[keyMapWeight] ?: 5,
            colorValue = pref[keyMapColor] ?: Color.Black.toArgb()
        )
    }

    fun isFirstPermission() = context.dataStore.data.map { pref ->
        pref[keyFirstLocationPermission] ?: true
    }

    suspend fun changePermission() = context.dataStore.edit { pref ->
        pref[keyFirstLocationPermission] = false
    }

}