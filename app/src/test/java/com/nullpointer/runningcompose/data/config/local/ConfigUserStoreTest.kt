package com.nullpointer.runningcompose.data.config.local

import androidx.datastore.preferences.core.edit
import com.nullpointer.runningcompose.data.mocks.DataStoreMock
import com.nullpointer.runningcompose.models.data.config.MapConfig
import com.nullpointer.runningcompose.models.data.config.SettingsData
import com.nullpointer.runningcompose.models.data.config.SortConfig
import com.nullpointer.runningcompose.models.types.MetricType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ConfigUserStoreTest {

    private val dataStore = DataStoreMock()
    private val configUserStore = ConfigUserStore(dataStore)

    @Before
    fun setUp() {
        runBlocking {
            dataStore.edit { it.clear() }
        }
    }

    @Test
    fun `saveSettingsData correctly saves SettingsData`() = runBlocking {
        // * create an instance of SettingsData
        val settingsData = SettingsData(
            sortConfig = SortConfig(),
            mapConfig = MapConfig(),
            metricConfig = MetricType.Meters,
            isFirstOpen = true
        )

        // * save the SettingsData
        configUserStore.updateSettingsData(settingsData)

        // * retrieve the SettingsData
        val response = configUserStore.getSettingsData().first()

        // * assert that the retrieved SettingsData is the same as the one saved
        assertEquals(settingsData, response)
    }


    @Test
    fun `return default SettingsData when no SettingsData is saved`() = runBlocking {
        // * retrieve the SettingsData
        val response = configUserStore.getSettingsData().first()

        // * assert that the retrieved SettingsData is the same as the default SettingsData
        assertEquals(SettingsData(), response)
    }
}