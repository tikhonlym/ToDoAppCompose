package com.todo.app.todoappcompose

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.rememberNavController
import com.todo.core.config.theme.AppThemeMode
import com.todo.core.config.theme.ThemeConfig
import com.todo.core.divkit.ActionController
import com.todo.core.divkit.DivScreenBuilder
import com.todo.core.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var themeConfig: ThemeConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

            val themeState = themeConfig.appThemeMode.collectAsState()
            val navController = rememberNavController()

            AppTheme(
                darkTheme = when (themeState.value) {
                    AppThemeMode.ALWAYS_DARK -> true
                    AppThemeMode.ALWAYS_LIGHT -> false
                    else -> isSystemInDarkTheme()
                }
            ) {
                MainNavigation(
                    context = this,
                    divScreenProvider = { jsonName ->
                        DivScreenBuilder(
                            activity = this,
                            lifecycleOwner = this,
                            actionController = ActionController(navController)
                        ).provideScreenByJsonName(jsonName)
                    },
                    navController = navController
                )
            }
        }
    }
}
