package com.example.myapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

fun logInfo(info: Any) = Log.i("Settings", info.toString())

class SettingsViewModel(private val settingsRepository: SettingsRepository) : ViewModel() {

    private val isInitKey = "is_init"

    init {
        viewModelScope.launch {
            checkDbInit()
        }
    }

    private suspend fun checkDbInit() {
        val isInit = settingsRepository.getBoolean(isInitKey).first()
        logInfo("db init status: $isInit")
        if (!isInit) {
            logInfo("now init db")
            settingsRepository.writeBoolToPrefs(isInitKey, true)
            saveSharedPrefs()
        }
    }

    private suspend fun saveSharedPrefs() {
        listOf(leakCanaryEnabled, chuckerEnabled, imitateApplication, endPoint)
            .asFlow()
            .onEach { value ->
                logInfo("writing ${value.key} of type ${value.className} to db")
                when (value.className) {
                    "Strings" -> settingsRepository.writeStringToPrefs(
                        value.key,
                        value.current as String
                    )

                    "Booleans" -> settingsRepository.writeBoolToPrefs(
                        value.key,
                        value.current as Boolean
                    )

                    "Ints" -> settingsRepository.writeIntToPrefs(
                        value.key,
                        value.current as Int
                    )
                }

            }
            .collect()
    }
}

class SettingsViewModelFactory(
    private val settingsRepository: SettingsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingsViewModel(settingsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
