package com.todo.app.todoappcompose

import android.content.Context
import android.view.View
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.todo.core.callback.NetworkStatusCallback
import com.todo.core.config.theme.AppThemeMode
import com.todo.core.destinations.AboutScreenDestination
import com.todo.core.destinations.EditScreenDestination
import com.todo.core.destinations.HomeScreenDestination
import com.todo.core.destinations.SettingsScreenDestination
import com.todo.featureabout.AboutScreen
import com.todo.featureedit.EditScreen
import com.todo.featurehome.HomeScreen
import com.todo.featurehome.HomeViewModel
import com.todo.featuresettings.SettingsScreen

@Composable
fun MainNavigation(
    context: Context,
    divScreenProvider: (jsonName: String) -> View,
    navController: NavHostController
) {
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
        composable<HomeScreenDestination>(
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(400)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(400)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(400)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(400)
                )
            }
        ) {
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
        composable<EditScreenDestination>(
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(400)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(400)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(400)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(400)
                )
            }
        ) { backStackEntry ->
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
                },
                onNavigateToAbout = {
                    navController.navigate(AboutScreenDestination)
                }
            )
        }

        composable<AboutScreenDestination> {
            AboutScreen(
                aboutScreen = divScreenProvider.invoke("about_screen.json")
            )
        }
    }
}
