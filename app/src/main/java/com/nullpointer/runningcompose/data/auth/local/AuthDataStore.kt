package com.nullpointer.runningcompose.data.auth.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.nullpointer.runningcompose.models.data.AuthData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AuthDataStore(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        private const val KEY_AUTH_DATA = "KEY_AUTH_DATA"
    }

    private val keyAuthData = stringPreferencesKey(KEY_AUTH_DATA)

    suspend fun saveAuthData(authData: AuthData) {
        val authDataEncode = Json.encodeToString(authData)
        dataStore.edit { pref ->
            pref[keyAuthData] = authDataEncode
        }
    }

    fun getAuthData():Flow<AuthData?> = dataStore.data.map { pref ->
        val authDataEncode = pref[keyAuthData]
        return@map authDataEncode?.let {
            Json.decodeFromString(authDataEncode)
        }
    }

    suspend fun saveUserData(name: String? = null, weight: Float? = null, photo: String? = null) {
        val authData = getAuthData().first() ?: AuthData()
        val newAuthData = authData.copy(
            name = name ?: authData.name,
            weight = weight ?: authData.weight,
            photo = photo ?: authData.photo
        )
        saveAuthData(newAuthData)
    }

}