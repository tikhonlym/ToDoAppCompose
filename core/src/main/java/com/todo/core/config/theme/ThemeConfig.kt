package com.todo.core.config.theme

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ThemeConfig(
    context: Context,
) {
    private val configScope = CoroutineScope(Dispatchers.IO)

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(CONFIG_PREFS, Context.MODE_PRIVATE)

    private val _appThemeMode = MutableStateFlow(getAppThemeMode())
    val appThemeMode: StateFlow<AppThemeMode> get() = _appThemeMode

    fun initMode(mode: AppThemeMode) {
        when (mode) {
            AppThemeMode.SYSTEM -> initSystemMode()
            AppThemeMode.ALWAYS_DARK -> initAlwaysDarkMode()
            AppThemeMode.ALWAYS_LIGHT -> initAlwaysLightMode()
        }
    }

    fun getAppThemeMode(): AppThemeMode {
        val modeFlag = sharedPreferences.getString(KEY_ALWAYS_MODE, AppThemeMode.SYSTEM.toString())
            ?: AppThemeMode.SYSTEM.toString()

        return when (modeFlag) {
            AppThemeMode.ALWAYS_LIGHT.toString() -> {
                AppThemeMode.ALWAYS_LIGHT
            }

            AppThemeMode.ALWAYS_DARK.toString() -> {
                AppThemeMode.ALWAYS_DARK
            }

            else -> {
                AppThemeMode.SYSTEM
            }
        }
    }

    private fun initAlwaysDarkMode() {
        configScope.launch {
            _appThemeMode.update { AppThemeMode.ALWAYS_DARK }
        }
        val editor = sharedPreferences.edit()
        editor.putString(KEY_ALWAYS_MODE, AppThemeMode.ALWAYS_DARK.toString())
        editor.apply()
    }

    private fun initAlwaysLightMode() {
        configScope.launch {
            _appThemeMode.update { AppThemeMode.ALWAYS_LIGHT }
        }
        val editor = sharedPreferences.edit()
        editor.putString(KEY_ALWAYS_MODE, AppThemeMode.ALWAYS_LIGHT.toString())
        editor.apply()
    }

    private fun initSystemMode() {
        configScope.launch {
            _appThemeMode.update { AppThemeMode.SYSTEM }
        }
        val editor = sharedPreferences.edit()
        editor.putString(KEY_ALWAYS_MODE, AppThemeMode.SYSTEM.toString())
        editor.apply()
    }

    companion object {
        private const val CONFIG_PREFS = "ConfigPrefs"
        private const val KEY_ALWAYS_MODE = "AlwaysMode"
    }
}