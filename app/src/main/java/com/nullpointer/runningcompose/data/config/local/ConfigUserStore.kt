package com.nullpointer.runningcompose.data.config.local

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.nullpointer.runningcompose.models.data.config.MapConfig
import com.nullpointer.runningcompose.models.data.config.SettingsData
import com.nullpointer.runningcompose.models.data.config.SortConfig
import com.nullpointer.runningcompose.models.types.MapStyle
import com.nullpointer.runningcompose.models.types.MetricType
import com.nullpointer.runningcompose.models.types.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder

class ConfigUserStore(
    private val dataStore: DataStore<Preferences>,
) {
    companion object {
        private const val KEY_USER_SETTINGS="KEY_USER_SETTINGS"
    }


    private val keyUserSettings = stringPreferencesKey(KEY_USER_SETTINGS)


    fun getSettingsData():Flow<SettingsData> = dataStore.data.map {pref->
        val settingsEncode= pref[keyUserSettings]

        return@map settingsEncode?.let {
            Json.decodeFromString(it)
        } ?: SettingsData()
    }

    suspend fun updateSettingsData(settingsData: SettingsData){
        dataStore.edit{pref->
            val settingsEncode= Json.encodeToString(settingsData)
            pref[keyUserSettings] = settingsEncode
        }
    }



}