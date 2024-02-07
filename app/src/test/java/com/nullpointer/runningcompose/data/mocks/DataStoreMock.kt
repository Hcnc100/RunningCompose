package com.nullpointer.runningcompose.data.mocks

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DataStoreMock : DataStore<Preferences> {
    private val _data = MutableStateFlow(preferencesOf())
    override val data = _data.asStateFlow()
    override suspend fun updateData(transform: suspend (t: Preferences) -> Preferences): Preferences =
        transform(_data.value).also {
            _data.value = it
        }
}
