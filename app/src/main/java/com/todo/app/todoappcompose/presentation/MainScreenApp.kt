package com.todo.app.todoappcompose.presentation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.todo.app.todoappcompose.presentation.edit.EditScreen
import com.todo.app.todoappcompose.presentation.edit.EditScreenDestination
import com.todo.app.todoappcompose.presentation.home.HomeScreen
import com.todo.app.todoappcompose.presentation.home.HomeScreenDestination

@Composable
fun MainScreenApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HomeScreenDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        composable<HomeScreenDestination> {
            HomeScreen(
                onNavigateToEditScreen = { id ->
                    navController.navigate(EditScreenDestination(id))
                }
            )
        }
        composable<EditScreenDestination> { backStackEntry ->
            val editScreen: EditScreenDestination = backStackEntry.toRoute()
            EditScreen(
                onNavigateBack = {
                    navController.navigateUp()
                },
                id = editScreen.id,
            )
        }
    }
}