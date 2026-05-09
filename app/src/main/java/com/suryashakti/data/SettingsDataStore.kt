package com.suryashakti.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object SettingsKeys {
    val UTILITY_RATE = doublePreferencesKey("utility_rate")
    val EXPORT_TARIFF = doublePreferencesKey("export_tariff")
}

class SettingsRepository(private val context: Context) {
    val utilityRate: Flow<Double> = context.dataStore.data
        .map { it[SettingsKeys.UTILITY_RATE] ?: 8.0 }

    val exportTariff: Flow<Double> = context.dataStore.data
        .map { it[SettingsKeys.EXPORT_TARIFF] ?: 4.0 }

    suspend fun saveUtilityRate(rate: Double) {
        context.dataStore.edit { it[SettingsKeys.UTILITY_RATE] = rate }
    }

    suspend fun saveExportTariff(tariff: Double) {
        context.dataStore.edit { it[SettingsKeys.EXPORT_TARIFF] = tariff }
    }
}
