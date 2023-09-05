package com.example.myapplication

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val USER_PREFERENCES_NAME = "user_preferences"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)

class SettingsRepository(private val context: Context) {

    fun getInt(key: String): Flow<Int> = context.dataStore.data
        .map { preferences ->
            val intPrefKey = intPreferencesKey(key)
            preferences[intPrefKey] ?: 0
        }

    fun getString(key: String): Flow<String> = context.dataStore.data
        .map { preferences ->
            val strPrefKey = stringPreferencesKey(key)
            preferences[strPrefKey] ?: ""
        }

    fun getBoolean(key: String): Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            val boolPrefKey = booleanPreferencesKey(key)
            preferences[boolPrefKey] ?: false
        }

    suspend fun writeIntToPrefs(key: String, value: Int) {
        context.dataStore.edit { settings ->
            val intPrefkey = intPreferencesKey(key)
            settings[intPrefkey] = value
        }
    }

    suspend fun writeStringToPrefs(key: String, value: String) {
        context.dataStore.edit { settings ->
            val strPrefkey = stringPreferencesKey(key)
            settings[strPrefkey] = value
        }
    }

    suspend fun writeBoolToPrefs(key: String, value: Boolean) {
        context.dataStore.edit { settings ->
            val boolPrefkey = booleanPreferencesKey(key)
            settings[boolPrefkey] = value
        }
    }
}
