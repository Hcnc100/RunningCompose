package com.nullpointer.runningcompose.data.config.local

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.nullpointer.runningcompose.models.data.config.MapConfig
import com.nullpointer.runningcompose.models.data.config.SortConfig
import com.nullpointer.runningcompose.models.types.MapStyle
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.models.types.SortType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ConfigUserStore(
    private val dataStore: DataStore<Preferences>,
) {
    companion object {
        private const val KEY_MAP_CONFIG = "KEY_MAP_CONFIG"
        private const val KEY_SORT_CONFIG = "KEY_SORT_CONFIG"
        private const val KEY_METRICS_CONFIG = "KEY_METRICS_CONFIG"
        private const val KEY_FIRST_LOCATION_PERMISSION = "KEY_FIRST_LOCATION_PERMISSION"
    }


    private val keyMapConfig = stringPreferencesKey(KEY_MAP_CONFIG)
    private val keySortConfig = stringPreferencesKey(KEY_SORT_CONFIG)
    private val keyMetricsConfig = stringPreferencesKey(KEY_METRICS_CONFIG)
    private val keyFirstLocationPermission = booleanPreferencesKey(KEY_FIRST_LOCATION_PERMISSION)

    suspend fun changeSortConfig(
        sortType: SortType? = null,
        isReverse: Boolean? = null,
    ) {
        val sortConfig = getSortConfig().first()
        changeSortConfig(
            SortConfig(
                sortType ?: sortConfig.sortType,
                isReverse ?: sortConfig.isReverse
            )
        )
    }

    private suspend fun changeSortConfig(sortConfig: SortConfig) {
        dataStore.edit { pref ->
            pref[keySortConfig] = Json.encodeToString(sortConfig)
        }
    }

    fun getSortConfig() = dataStore.data.map { pref ->
        val sortConfigEncode = pref[keySortConfig].orEmpty()
        if (sortConfigEncode.isEmpty()) SortConfig() else Json.decodeFromString(sortConfigEncode)
    }


    suspend fun changeMapConfig(
        style: MapStyle? = null,
        weight: Int? = null,
        color: Color? = null,
    ) {
        val mapConfig = getMapConfig().first()
        changeMapConfig(
            MapConfig(
                style = style ?: mapConfig.style,
                weight = weight ?: mapConfig.weight,
                colorValue = color?.toArgb() ?: mapConfig.colorValue
            )
        )
    }

    private suspend fun changeMapConfig(mapConfig: MapConfig) {
        dataStore.edit { pref ->
            pref[keyMapConfig] = Json.encodeToString(mapConfig)
        }
    }

    suspend fun changeMetrics(
        metrics: MetricType,
    ) = dataStore.edit { pref ->
        pref[keyMetricsConfig] = metrics.name
    }

    fun getMetrics() = dataStore.data.map { pref ->
        pref[keyMetricsConfig]?.let { MetricType.valueOf(it) } ?: MetricType.Meters
    }

    fun getMapConfig() = dataStore.data.map { pref ->
        val mapConfigEncode = pref[keyMapConfig].orEmpty()
        if (mapConfigEncode.isEmpty()) MapConfig() else Json.decodeFromString(mapConfigEncode)
    }

    fun isFirstPermission() = dataStore.data.map { pref ->
        pref[keyFirstLocationPermission] ?: true
    }

    suspend fun changePermission() = dataStore.edit { pref ->
        pref[keyFirstLocationPermission] = false
    }

}