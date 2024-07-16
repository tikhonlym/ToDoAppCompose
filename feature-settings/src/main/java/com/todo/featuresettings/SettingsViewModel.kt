package com.todo.featuresettings

import androidx.lifecycle.ViewModel
import com.todo.core.config.theme.AppThemeMode
import com.todo.core.config.theme.ThemeConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themeConfig: ThemeConfig,
) : ViewModel() {

    fun getThemeMode(): AppThemeMode {
        return themeConfig.getAppThemeMode()
    }

    fun changeThemeMode(mode: AppThemeMode) {
        themeConfig.initMode(mode)
    }
}