package com.todo.app.todoappcompose.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.todo.app.todoappcompose.app.theme.ToDoAppComposeTheme
import com.todo.app.todoappcompose.presentation.edit.EditToDoScreen
import com.todo.app.todoappcompose.presentation.edit.EditToDoScreenDestination
import com.todo.app.todoappcompose.presentation.home.HomeScreen
import com.todo.app.todoappcompose.presentation.home.HomeScreenDestination

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoAppComposeTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = HomeScreenDestination
                ) {
                    composable<HomeScreenDestination> {
                        HomeScreen(navController)
                    }
                    composable<EditToDoScreenDestination> {
                        val inputArguments = it.toRoute<EditToDoScreenDestination>()
                        EditToDoScreen(
                            id = inputArguments.id,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}