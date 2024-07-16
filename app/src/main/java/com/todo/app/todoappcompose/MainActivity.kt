package com.todo.app.todoappcompose

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.todo.core.callback.NetworkStatusCallback
import com.todo.core.config.theme.AppThemeMode
import com.todo.core.config.theme.ThemeConfig
import com.todo.core.navigation.EditScreenDestination
import com.todo.core.navigation.HomeScreenDestination
import com.todo.core.navigation.SettingsScreenDestination
import com.todo.core.theme.AppTheme
import com.todo.featureedit.EditScreen
import com.todo.featurehome.HomeScreen
import com.todo.featurehome.HomeViewModel
import com.todo.featuresettings.SettingsScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var themeConfig: ThemeConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val themeState = themeConfig.appThemeMode.collectAsState()

            AppTheme(
                darkTheme = when (themeState.value) {
                    AppThemeMode.ALWAYS_DARK -> {
                        true
                    }

                    AppThemeMode.ALWAYS_LIGHT -> {
                        false
                    }

                    else -> {
                        isSystemInDarkTheme()
                    }
                }
            ) {
                MainScreenApp(this)
            }
        }
    }
}

@Composable
fun MainScreenApp(
    context: Context,
) {
    val navController = rememberNavController()
    val networkStatusCallback = remember { NetworkStatusCallback(context) }
    val homeViewModel: HomeViewModel = hiltViewModel()
    if (networkStatusCallback.isNetworkAvailable.value) {
        homeViewModel.refreshList()
    }
    NavHost(
        navController = navController,
        startDestination = HomeScreenDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        composable<HomeScreenDestination> {
            HomeScreen(
                viewModel = homeViewModel,
                networkStatusCallback = networkStatusCallback,
                onNavigateToEditScreen = {
                    navController.navigate(EditScreenDestination(it))
                },
                onNavigateToSettings = {
                    navController.navigate(SettingsScreenDestination)
                }

            )
        }
        composable<EditScreenDestination> { backStackEntry ->
            val editScreen: EditScreenDestination = backStackEntry.toRoute()
            EditScreen(
                id = editScreen.id,
                onNavigateUp = {
                    navController.navigateUp()
                }
            )
        }
        composable<SettingsScreenDestination> {
            SettingsScreen(
                onNavigateUp = {
                    navController.navigateUp()
                }
            )
        }
    }
}

