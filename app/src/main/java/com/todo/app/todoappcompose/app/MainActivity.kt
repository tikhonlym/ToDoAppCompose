package com.todo.app.todoappcompose.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.todo.app.todoappcompose.app.theme.AppTheme
import com.todo.app.todoappcompose.presentation.edit.EditScreen
import com.todo.app.todoappcompose.presentation.edit.EditScreenDestination
import com.todo.app.todoappcompose.presentation.home.HomeScreen
import com.todo.app.todoappcompose.presentation.home.HomeScreenDestination
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = HomeScreenDestination
                ) {
                    composable<HomeScreenDestination> {
                        HomeScreen(navController = navController)
                    }
                    composable<EditScreenDestination> {
                        val inputArguments = it.toRoute<EditScreenDestination>()
                        EditScreen(
                            id = inputArguments.id,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}